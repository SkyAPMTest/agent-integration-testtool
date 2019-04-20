package org.skywalking.apm.test.agent.tool.validator.assertor.element;

import org.skywalking.apm.test.agent.tool.validator.assertor.exception.ValueAssertFailedException;

/**
 * Created by xin on 2017/7/16.
 */
public class GreatThanAssertor extends ElementAssertor {

    public GreatThanAssertor(String exceptedValue) {
        super(exceptedValue);
    }

    @Override
    public void assertValue(String desc, String actualValue) {
        if (Long.parseLong(actualValue) <= Long.parseLong(exceptedValue)) {
            throw new ValueAssertFailedException(desc," gt " + exceptedValue, actualValue);
        }
    }
}
