package org.skywalking.apm.test.agent.tool.validator.assertor;

import java.util.List;
import org.skywalking.apm.test.agent.tool.validator.entity.RegistryOperationName;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.ActualRegistryOperationEmptyException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.RegistryOperationNameNotFoundException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.RegistryOperationNamesNotFoundException;

public class OperationNameAssert {
    public static void assertEquals(List<RegistryOperationName> expected, List<RegistryOperationName> actual) {
        if (expected == null) {
            return;
        }

        for (RegistryOperationName operationName : expected) {
            RegistryOperationName actualOperationName = findActualRegistryOperationName(actual, operationName);
            assertOperationEquals(operationName.operationName(), actualOperationName.operationName());
        }
    }

    private static void assertOperationEquals(List<String> expectedOperationName, List<String> actualOperationName) {
        for (String operationName : expectedOperationName) {
            if (!actualOperationName.contains(operationName)) {
                throw new RegistryOperationNameNotFoundException(operationName);
            }
        }
    }

    private static RegistryOperationName findActualRegistryOperationName(
        List<RegistryOperationName> actual, RegistryOperationName registryOperationName) {
        if (actual == null) {
            throw new ActualRegistryOperationEmptyException(registryOperationName);
        }

        for (RegistryOperationName operationName : actual) {
            if (operationName.applicationCode().equals(registryOperationName.applicationCode())) {
                return operationName;
            }
        }

        throw new RegistryOperationNamesNotFoundException(registryOperationName);
    }
}
