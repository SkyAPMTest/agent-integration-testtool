package org.skywalking.apm.test.agent.tool.caseassert.assertor;

import java.util.List;
import org.skywalking.apm.test.agent.tool.caseassert.entity.RegistryOperationName;
import org.skywalking.apm.test.agent.tool.caseassert.exception.AssertFailedException;

public class OperationNameAssert {
    public static void assertEquals(List<RegistryOperationName> expected, List<RegistryOperationName> actual) {
        if (expected == null) {
            return;
        }

        for (RegistryOperationName operationName : expected) {
            RegistryOperationName actualOperationName = findActualRegistryOperationName(actual, operationName.applicationCode());
            if (actualOperationName == null) {
                throw new AssertFailedException("assert application[" + operationName.applicationCode() + "] operationName: \n expected:" + operationName.operationName() + "\n actual: not found");
            }

            assertOperationEquals(operationName.operationName(), actualOperationName.operationName());
        }
    }

    private static void assertOperationEquals(List<String> expectedOperationName, List<String> actualOperationName) {
        for (String operationName : expectedOperationName) {
            if (!actualOperationName.contains(operationName)) {
                throw new AssertFailedException("assert operationName: \n expected:" + operationName + "\n actual: not found");
            }
        }
    }

    private static RegistryOperationName findActualRegistryOperationName(
        List<RegistryOperationName> actual, String applicationCode) {
        if (actual == null) {
            return null;
        }

        for (RegistryOperationName operationName : actual) {
            if (operationName.applicationCode().equals(applicationCode)) {
                return operationName;
            }
        }
        return null;
    }
}
