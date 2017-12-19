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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.skywalking.apm.test.agent.tool.report.entity.TestCase;

/**
 * @author xin
 */
public class CaseProcessResult {
    private Map<CaseInfo, List<TestCase>> testCases;

    public CaseProcessResult() {
        testCases = new HashMap<>();
    }

    public void addTestCases(CaseInfo caseInfo, TestCase testCase) {
        List<TestCase> cases = testCases.get(caseInfo);
        if (cases == null) {
            cases = new ArrayList<>();
            this.testCases.put(caseInfo, cases);
        }

        cases.add(testCase);
    }

    public Map<CaseInfo, List<TestCase>> getTestCases() {
        return testCases;
    }
}
