/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    private final String testDate;
    private final String testBranch;
    private final String commitId;
    private List<CaseScenario> caseScenarios;
    private DisplayStatus status;

    public Report(String date, String branch, String commit) {
        this.testDate = formatTestDate(date);
        this.testBranch = branch;
        this.commitId = commit;
        this.caseScenarios = new ArrayList<>();
    }

    public String getTestDate() {
        return testDate;
    }

    public String getTestBranch() {
        return testBranch;
    }

    public String getCommitId() {
        return commitId;
    }

    public List<CaseScenario> getCaseScenarios() {
        return caseScenarios;
    }

    public DisplayStatus getStatus() {
        return status;
    }

    public void addTestCase(TestCase testCase) {
        CaseScenario scenario = findCaseScenario(testCase.getTestFramework());

        if (scenario == null) {
            scenario = CaseScenario.buildCaseScenario(testCase);
            this.caseScenarios.add(scenario);
        }

        scenario.addTestCase(testCase);
    }

    private CaseScenario findCaseScenario(String testFramework) {
        for (CaseScenario ascenario : caseScenarios) {
            if (ascenario.getTestFramework().equalsIgnoreCase(testFramework)) {
                return ascenario;
            }
        }

        return null;
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

    public void setStatus(DisplayStatus status) {
        this.status = status;
    }

    public void generateReport(String path) throws IOException, TemplateException {
        //
        generateDisplayStatus();
        //

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
        Template template = cfg.getTemplate("main-report.md.ftl");
        StringWriter stringWriter = new StringWriter();
        Map<String, Report> resultMap = new HashMap<>();
        resultMap.put("testReport", this);
        template.process(resultMap, stringWriter);
        FileWriter fileOutputStream = new FileWriter(reportFile, false);
        fileOutputStream.write(stringWriter.getBuffer().toString());
        stringWriter.close();
        fileOutputStream.close();
    }

    public void generateDisplayStatus() {
        int total = 0;
        int success = 0;

        for (CaseScenario scenario : caseScenarios) {
            Context context = scenario.generateStatus();
            total += context.getTotal();
            success += context.getSuccess();
        }

        this.status = new DisplayStatus(success, total);
    }
}
