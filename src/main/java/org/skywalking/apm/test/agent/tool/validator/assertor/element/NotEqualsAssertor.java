package org.skywalking.apm.test.agent.tool.validator.assertor.element;

import org.skywalking.apm.test.agent.tool.validator.exception.AssertFailedException;

/**
 * Created by xin on 2017/7/15.
 */
public class NotEqualsAssertor extends ElementAssertor {

    public NotEqualsAssertor(String exceptedValue) {
        super(exceptedValue);
    }

    @Override
    public void assertValue(String desc, String actualValue) {
        if (exceptedValue.equals(actualValue.trim())) {
            throw new AssertFailedException(desc, " not eq " + exceptedValue, actualValue);
        }
    }
}
