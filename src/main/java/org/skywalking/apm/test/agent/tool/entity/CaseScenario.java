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

import java.util.ArrayList;
import java.util.List;

public class CaseScenario {
    private String testFramework;
    private List<TestCase> cases;
    private List<Component> cooperativeFrameworks;
    private SupportedStatus status;

    private CaseScenario(String testFramework, List<Component> cooperativeFrameworks) {
        this.testFramework = testFramework;
        this.cooperativeFrameworks = cooperativeFrameworks;
        this.cases = new ArrayList<>();
    }

    public static CaseScenario buildCaseScenario(TestCase testCase) {
        return new CaseScenario(testCase.getTestFramework(), testCase.getCooperativeFrameworks());
    }

    public void addTestCase(TestCase testCase) {
        this.cases.add(testCase);
    }

    public String getTestFramework() {
        return testFramework;
    }

    public List<TestCase> getCases() {
        return cases;
    }

    public List<Component> getCooperativeFrameworks() {
        return cooperativeFrameworks;
    }

    public SupportedStatus getStatus() {
        return status;
    }

    SupportedStatus verifyAndSetStatus() {
        SupportedStatus status = SupportedStatus.SUPPORTED;
        for (TestCase aCase : cases) {
            if (!aCase.supported()) {
                status = SupportedStatus.FAILED;
                break;
            }
        }

        this.status = status;
        return this.status;
    }

}
