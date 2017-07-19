package org.skywalking.apm.test.agent.tool.entity;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Report {
    private static Logger logger = LogManager.getLogger(Report.class);
    private String fileSuffix;
    private String testTime;
    private Status status;
    private List<TestCase> testCases;
    private String agentBranch;
    private String agentCommit;
    private String portalURL = "";

    public Report(String date, String branch, String commit) {
        fileSuffix = date;
        testTime = formatTestDate(date);
        this.agentBranch = branch;
        this.agentCommit = commit;
        this.testCases = new ArrayList<>();
        status = Status.PASSING;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }

    public String getAgentBranch() {
        return agentBranch;
    }

    public void setAgentBranch(String agentBranch) {
        this.agentBranch = agentBranch;
    }

    public String getAgentCommit() {
        return agentCommit;
    }

    public void setAgentCommit(String agentCommit) {
        this.agentCommit = agentCommit;
    }

    public void generate(String path) throws IOException, TemplateException {
        File reportFile = new File(path, "README.md");
        if (!reportFile.getParentFile().exists()) {
            reportFile.getParentFile().mkdirs();
        }

        if (!reportFile.exists()) {
            reportFile.createNewFile();
        }
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setClassForTemplateLoading(this.getClass(), "/template");
        cfg.setDefaultEncoding("UTF-8");
        Template template = cfg.getTemplate("test-report.md.ftl");
        StringWriter stringWriter = new StringWriter();
        Map<String, Report> resultMap = new HashMap<>();
        resultMap.put("testReport", this);
        template.process(resultMap, stringWriter);
        FileWriter fileOutputStream = new FileWriter(reportFile, false);
        fileOutputStream.write(stringWriter.getBuffer().toString());
        stringWriter.close();
        fileOutputStream.close();
    }

    public void addTestCase(TestCase aCase) {
        this.testCases.add(aCase);
    }

    public String getPortalURL() {
        return portalURL;
    }

    public void setPortalURL(String portalURL) {
        this.portalURL = portalURL;
    }

    private String formatTestDate(String date) {
        try {
            Date testDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm").parse(date);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(testDate);
        } catch (ParseException e) {
            logger.error("Failed to format date[{}].", date, e);
        }
        return date;
    }
}
