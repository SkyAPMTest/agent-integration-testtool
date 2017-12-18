package org.skywalking.apm.test.agent.tool;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.skywalking.apm.test.agent.tool.report.entity.Report;
import org.skywalking.apm.test.agent.tool.report.entity.TestCase;
import org.skywalking.apm.test.agent.tool.report.entity.TestCaseDesc;
import org.skywalking.apm.test.agent.tool.validator.assertor.DataAssert;
import org.skywalking.apm.test.agent.tool.validator.entity.Data;
import org.skywalking.apm.test.agent.tool.validator.exception.AssertFailedException;

public class Main {
    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Begin to validate data.");

        Report report = new Report(ConfigHelper.testDate(), ConfigHelper.agentBranch(), ConfigHelper.agentCommit(), ConfigHelper.casesBranch(), ConfigHelper.caseCommitId());
        String[] testCases = ConfigHelper.testCases().split(",");

        String testCasePath = ConfigHelper.testCaseBaseDir();
        // 校验所有插件
        for (String aCase : testCases) {
            if (aCase == null || aCase.length() == 0) {
                continue;
            }
            File casePath = new File(testCasePath, aCase);
            TestCaseDesc caseDesc = TestCaseDesc.Parser.parse(new File(casePath, "testcase.desc"));
            TestCase testCase = new TestCase(caseDesc.getTestFramework(), caseDesc.getTestVersion());
            try {
                logger.info("start to assert data of test case[{}]", testCase.getCaseName());
                File actualData = new File(casePath, "actualData.yaml");
                File expectedData = new File(casePath, "expectedData.yaml");
                if (actualData.exists() && expectedData.exists()) {
                    try {
                        DataAssert.assertEquals(Data.Loader.loadData(expectedData), Data.Loader.loadData(actualData));
                        testCase.testedSuccessfully();
                    } catch (AssertFailedException e) {
                        logger.error("assert failed.\n{}", e.getMessage());
                    }
                } else {
                    logger.error("assert failed. because actual data {} and expected data {}", actualData.exists() ? "founded" : "not founded", actualData.exists() ? "founded" : "not founded");
                }
            } catch (Exception e) {
                logger.error("assert test case {} failed.", testCase.getCaseName(), e);
            }

            report.addTestCase(caseDesc.getProjectName(), caseDesc.getTestFramework(), testCase);
        }

        //生成报告
        try {
            report.generateReport(ConfigHelper.reportFilePath());
        } catch (Exception e) {
            logger.error("Failed to generate report file", e);
        }
    }

}
