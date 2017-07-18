package org.skywalking.apm.test.agent.tool.caseassert.assertor;

import java.util.List;
import org.skywalking.apm.test.agent.tool.caseassert.entity.RegistryInstance;
import org.skywalking.apm.test.agent.tool.caseassert.exception.AssertFailedException;

/**
 * Created by xin on 2017/7/15.
 */
public class InstanceAssert {
    public static void assertEquals(List<RegistryInstance> expected, List<RegistryInstance> actual) {

        if (expected == null) {
            return;
        }

        for (RegistryInstance instance : expected) {
            RegistryInstance actualInstance = getMatchApplication(actual, instance);
            if (actualInstance == null) {
                throw new AssertFailedException("assert application[" + actualInstance.applicationCode() + "] instance: \n expected:" + instance.expressValue() + "\n actual: not found");
            }

            ExpressParser.parse(actualInstance.expressValue()).assertValue(actualInstance.expressValue());
        }
    }

    private static RegistryInstance getMatchApplication(List<RegistryInstance> actual,
        RegistryInstance application) {
        for (RegistryInstance registryApplication : actual) {
            if (registryApplication.applicationCode().equals(application.applicationCode())) {
                return registryApplication;
            }
        }
        return null;
    }
}
