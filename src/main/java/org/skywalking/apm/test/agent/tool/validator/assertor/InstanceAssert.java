package org.skywalking.apm.test.agent.tool.validator.assertor;

import java.util.List;
import org.skywalking.apm.test.agent.tool.validator.entity.RegistryInstance;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.RegistryInstanceNotFoundException;

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
            ExpressParser.parse(actualInstance.expressValue()).assertValue(String.format("The registry instance of %s",
                instance.applicationCode()), actualInstance.expressValue());
        }
    }

    private static RegistryInstance getMatchApplication(List<RegistryInstance> actual,
        RegistryInstance application) {
        for (RegistryInstance registryApplication : actual) {
            if (registryApplication.applicationCode().equals(application.applicationCode())) {
                return registryApplication;
            }
        }
        throw new RegistryInstanceNotFoundException(application.applicationCode());
    }
}
