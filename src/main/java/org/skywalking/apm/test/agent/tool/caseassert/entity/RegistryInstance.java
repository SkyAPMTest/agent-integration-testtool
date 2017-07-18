package org.skywalking.apm.test.agent.tool.caseassert.entity;

public interface RegistryInstance {

    String applicationCode();

    String expressValue();

    class Impl implements RegistryInstance {

        private final String code;
        private final String express;

        Impl(String code, String express) {
            this.code = code;
            this.express = express;
        }

        @Override public String applicationCode() {
            return code;
        }

        @Override public String expressValue() {
            return express;
        }
    }
}
