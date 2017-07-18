package org.skywalking.apm.test.agent.tool.caseassert.assertor.element;

public abstract class ElementAssertor {

    public ElementAssertor(String exceptedValue) {
        if (exceptedValue != null) {
            this.exceptedValue = exceptedValue.trim();
        }
    }

    protected String exceptedValue;

    public abstract void assertValue(String actualValue);
}
