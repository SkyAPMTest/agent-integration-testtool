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

package org.skywalking.apm.test.agent.tool.report.processor;

import java.io.File;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.skywalking.apm.test.agent.tool.report.ConfigHelper;
import org.skywalking.apm.test.agent.tool.report.entity.TestCase;
import org.skywalking.apm.test.agent.tool.report.entity.TestCaseDesc;
import org.skywalking.apm.test.agent.tool.validator.assertor.DataAssert;
import org.skywalking.apm.test.agent.tool.validator.entity.Data;
import org.skywalking.apm.test.agent.tool.validator.exception.AssertFailedException;

public class TestCaseProcessor extends WorkerThreadSlave<List<String>, CaseProcessResult> {
    private static Logger logger = LogManager.getLogger(TestCaseProcessor.class);
    private String testCasePath;

    public TestCaseProcessor(BlockingQueue<Runnable> queue) {
        super(queue);
    }

    @Override
    public CaseProcessResult doProcess(List<String> caseNames) {
        CaseProcessResult caseResult = new CaseProcessResult();
        for (String aCase : caseNames) {
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

            caseResult.addTestCases(new CaseInfo(caseDesc.getTestFramework(), caseDesc.getProjectName()), testCase);
        }
        return caseResult;
    }

    @Override
    public void init() {
        testCasePath = ConfigHelper.testCaseBaseDir();
    }
}
