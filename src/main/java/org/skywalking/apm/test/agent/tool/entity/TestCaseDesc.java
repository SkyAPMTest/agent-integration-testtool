package org.skywalking.apm.test.agent.tool.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestCaseDesc {
    private static Logger logger = LogManager.getLogger(TestCaseDesc.class);
    private String caseName;
    private List<TestCase.Component> components;
    private String requestURL;
    private boolean isValidate;

    private TestCaseDesc() {
        components = new ArrayList<>();
        isValidate = false;
    }

    public static class Parser {
        public static TestCaseDesc parse(File caseDescFile) {
            TestCaseDesc testCase = new TestCaseDesc();
            Properties properties = new Properties();
            if (caseDescFile.exists()) {
                try {
                    properties.load(new FileInputStream(caseDescFile));
                } catch (IOException e) {
                    logger.error("Failed to parse case desc file[{}]", caseDescFile.getAbsoluteFile());
                    return testCase;
                }
            }

            testCase.caseName = properties.getProperty("case.name", null);
            testCase.requestURL = properties.getProperty("case.request_url", null);
            testCase.components = formatComponents(properties.getProperty("case.components", null));
            testCase.isValidate = validateCaseDesc(testCase);
            logger.info("load case desc: caseName[{}], caseRequestURL[{}], components[{}], isValidate[{}]",
                testCase.caseName,
                testCase.requestURL,
                testCase.components,
                testCase.isValidate);
            return testCase;
        }

        private static boolean validateCaseDesc(TestCaseDesc aCase) {
            return aCase.requestURL != null && aCase.caseName != null;
        }

        private static List<TestCase.Component> formatComponents(String componentStr) {
            List<TestCase.Component> components = new ArrayList<>();
            if (componentStr == null) {
                return components;
            }

            String[] componentSegments = componentStr.split(",");
            for (String segment : componentSegments) {
                components.add(new TestCase.Component(segment));
            }
            return components;
        }

    }

    public boolean isValidate() {
        return isValidate;
    }

    public String getCaseName() {
        return caseName;
    }

    public List<TestCase.Component> getComponents() {
        return components;
    }

    public String getRequestURL() {
        return requestURL;
    }
}
