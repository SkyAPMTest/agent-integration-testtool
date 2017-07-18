package org.skywalking.apm.test.agent.tool.caseassert.assertor.element;

import org.skywalking.apm.test.agent.tool.caseassert.exception.AssertFailedException;

/**
 * Created by xin on 2017/7/15.
 */
public class NotEqualsAssertor extends ElementAssertor {

    public NotEqualsAssertor(String exceptedValue) {
        super(exceptedValue);
    }

    @Override
    public void assertValue(String actualValue) {
        if (exceptedValue.equals(actualValue.trim())) {
            throw new AssertFailedException("!=" + exceptedValue, actualValue);
        }
    }
}
