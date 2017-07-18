package org.skywalking.apm.test.agent.tool.caseassert.assertor;

import java.util.List;
import org.skywalking.apm.test.agent.tool.caseassert.entity.RegistryApplication;
import org.skywalking.apm.test.agent.tool.caseassert.exception.AssertFailedException;

public class ApplicationAssert {
    public static void assertEquals(List<RegistryApplication> expected,
        List<RegistryApplication> actual) {

        if (expected == null) {
            return;
        }

        for (RegistryApplication application : expected) {
            RegistryApplication actualApplication = getMatchApplication(actual, application);
            if (actualApplication == null) {
                throw new AssertFailedException(application.applicationCode(), "null");
            }

            ExpressParser.parse(application.expressValue()).assertValue(actualApplication.expressValue());
        }
    }

    private static RegistryApplication getMatchApplication(List<RegistryApplication> actual,
        RegistryApplication application) {
        for (RegistryApplication registryApplication : actual) {
            if (registryApplication.applicationCode().equals(application.applicationCode())) {
                return registryApplication;
            }
        }
        return null;
    }
}
