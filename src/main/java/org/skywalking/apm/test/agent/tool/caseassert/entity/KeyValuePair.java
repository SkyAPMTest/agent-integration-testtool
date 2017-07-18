package org.skywalking.apm.test.agent.tool.caseassert.entity;

/**
 * Created by xin on 2017/7/15.
 */
public interface KeyValuePair {
    String key();

    String value();

    class Impl implements KeyValuePair {
        private String key;
        private String value;

        public Impl(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String key() {
            return key;
        }

        @Override
        public String value() {
            return value;
        }
    }
}
