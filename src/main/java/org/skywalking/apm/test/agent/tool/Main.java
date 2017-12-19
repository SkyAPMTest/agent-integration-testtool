package org.skywalking.apm.test.agent.tool;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.skywalking.apm.test.agent.tool.report.ConfigHelper;
import org.skywalking.apm.test.agent.tool.report.ReportProcessor;
import org.skywalking.apm.test.agent.tool.report.entity.Report;

public class Main {
    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Begin to validate data.");

        String[] testCases = ConfigHelper.testCases().split(",");
        //生成报告
        try {
            Report report = new ReportProcessor().service(Arrays.asList(testCases));
            report.generateReport(ConfigHelper.reportFilePath());
        } catch (Exception e) {
            logger.error("Failed to generate report file", e);
        }
    }

}
