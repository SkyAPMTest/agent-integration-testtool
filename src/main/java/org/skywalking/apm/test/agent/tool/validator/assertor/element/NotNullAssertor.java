package org.skywalking.apm.test.agent.tool.validator.assertor.element;

import org.skywalking.apm.test.agent.tool.validator.exception.AssertFailedException;

/**
 * Created by xin on 2017/7/15.
 */
public class NotNullAssertor extends ElementAssertor {
    public NotNullAssertor() {
        super(null);
    }

    @Override
    public void assertValue(String desc, String actualValue) {
        if (actualValue == null) {
            throw new AssertFailedException(desc, "not null", actualValue);
        }
    }
}
