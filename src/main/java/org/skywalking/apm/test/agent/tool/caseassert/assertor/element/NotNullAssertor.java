package org.skywalking.apm.test.agent.tool.caseassert.assertor.element;

import org.skywalking.apm.test.agent.tool.caseassert.exception.AssertFailedException;

/**
 * Created by xin on 2017/7/15.
 */
public class NotNullAssertor extends ElementAssertor {
    public NotNullAssertor() {
        super(null);
    }

    @Override
    public void assertValue(String actualValue) {
        if (actualValue == null) {
            throw new AssertFailedException("not null", actualValue);
        }
    }
}
