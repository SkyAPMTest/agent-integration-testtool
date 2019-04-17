package org.skywalking.apm.test.agent.tool.validator.assertor.exception;

/**
 * Created by xin on 2017/7/15.
 */
public abstract class AssertFailedException extends RuntimeException {
    protected AssertFailedException(String message) {
        super(message);
    }

    protected AssertFailedException(){
    }

    public abstract String getCauseMessage();
}
