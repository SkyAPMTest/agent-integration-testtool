package org.skywalking.apm.test.agent.tool.caseassert.assertor.element;

import org.skywalking.apm.test.agent.tool.caseassert.exception.AssertFailedException;

/**
 * Created by xin on 2017/7/15.
 */
public class EqualsAssertor extends ElementAssertor {

    public EqualsAssertor(String exceptedValue) {
        super(exceptedValue);
    }

    @Override
    public void assertValue(String actualValue) {
        if (!exceptedValue.equals(actualValue)) {
            throw new AssertFailedException("==" + exceptedValue, actualValue);
        }
    }
}
