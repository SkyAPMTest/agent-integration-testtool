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

package org.skywalking.apm.test.agent.tool.report;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.skywalking.apm.test.agent.tool.report.entity.Report;
import org.skywalking.apm.test.agent.tool.report.entity.TestCase;
import org.skywalking.apm.test.agent.tool.report.processor.AbstractMaster;
import org.skywalking.apm.test.agent.tool.report.processor.CaseInfo;
import org.skywalking.apm.test.agent.tool.report.processor.CaseProcessResult;
import org.skywalking.apm.test.agent.tool.report.processor.SlaveSpec;
import org.skywalking.apm.test.agent.tool.report.processor.TaskDivideStrategy;
import org.skywalking.apm.test.agent.tool.report.processor.TestCaseProcessor;

/**
 * @author xin
 */
public class ReportProcessor extends AbstractMaster<List<String>, CaseProcessResult, Report, String> {

    private static Logger logger = LogManager.getLogger(ReportProcessor.class);

    @Override protected Report combineResult(Iterator<Future<CaseProcessResult>> results) {
        Report report = new Report(ConfigHelper.testDate(), ConfigHelper.agentBranch(), ConfigHelper.agentCommit(), ConfigHelper.casesBranch(), ConfigHelper.caseCommitId());

        while (results.hasNext()) {
            try {
                CaseProcessResult processResult = results.next().get();
                Map<CaseInfo, List<TestCase>> testCases = processResult.getTestCases();
                for (Map.Entry<CaseInfo, List<TestCase>> entry : testCases.entrySet()) {

                    CaseInfo caseInfo = entry.getKey();
                    for (TestCase aCase : entry.getValue()) {
                        report.addTestCase(caseInfo.getProjectName(), caseInfo.getTestFramework(), aCase);
                    }
                }
            } catch (Exception e) {
                logger.error("Failed to combine result.", e);
            }
        }
        return report;
    }

    @Override protected TaskDivideStrategy<List<String>> newTaskDivideStrategy(List<String> params) {
        return new TaskDivideStrategy<List<String>>() {
            private int lastDispatchIndex = 0;

            @Override public List<String> nextChunk() {
                if (lastDispatchIndex > params.size()) {
                    return null;
                }

                int endIndex = lastDispatchIndex + 5;
                if (endIndex > params.size()) {
                    endIndex = params.size();
                }

                List<String> nextChunk = params.subList(lastDispatchIndex, endIndex);
                lastDispatchIndex = endIndex;
                return nextChunk;
            }
        };
    }

    @Override protected Set<? extends SlaveSpec<List<String>, CaseProcessResult>> createSlaves() {
        Set<TestCaseProcessor> slaves = new HashSet<>();
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            slaves.add(new TestCaseProcessor(new ArrayBlockingQueue<Runnable>(2)));
        }
        return slaves;
    }
}
