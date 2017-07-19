package org.skywalking.apm.test.agent.tool.caseassert.exception;

/**
 * Created by xin on 2017/7/15.
 */
public class AssertFailedException extends RuntimeException {
    public AssertFailedException(String expected, String actual) {
        super("Expected: " + expected + ", Actual: " + actual);
    }

    public AssertFailedException(String message) {
        super(message);
    }

    public AssertFailedException(String desc, String expected, String actual) {
        super(desc + ": \n expected: " + expected + "\n actual: " + actual);
    }
}
