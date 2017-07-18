package org.skywalking.apm.test.agent.tool.caseassert.assertor.element;

import org.skywalking.apm.test.agent.tool.caseassert.exception.AssertFailedException;

/**
 * Created by xin on 2017/7/18.
 */
public class NullAssertor extends ElementAssertor {
    public NullAssertor() {
        super(null);
    }

    @Override
    public void assertValue(String actualValue) {
        if (actualValue != null && actualValue.length() > 0) {
            throw new AssertFailedException("null", actualValue);
        }
    }
}
