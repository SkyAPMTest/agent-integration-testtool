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

import java.util.List;

public class TestCase {
    private String caseName;
    private String testFramework;
    private SupportedStatus status;
    private List<Component> cooperativeFrameworks;

    public TestCase(String testFramework, String testVersion, List<Component> cooperativeFrameworks) {
        this(testFramework, testVersion, cooperativeFrameworks, SupportedStatus.SUPPORTED);
    }

    public TestCase(String testFramework, String testVersion, List<Component> cooperativeFrameworks,
        SupportedStatus status) {
        this.caseName = String.join("-", testFramework, testVersion);
        this.cooperativeFrameworks = cooperativeFrameworks;
        this.testFramework = testFramework;
        this.status = status;
    }

    public String getCaseName() {
        return caseName;
    }

    public List<Component> getCooperativeFrameworks() {
        return cooperativeFrameworks;
    }

    public SupportedStatus getStatus() {
        return status;
    }

    public void setStatus(SupportedStatus status) {
        this.status = status;
    }

    public String getTestFramework() {
        return testFramework;
    }

    public void setTestFramework(String testFramework) {
        this.testFramework = testFramework;
    }

    public boolean supported() {
        return this.status == SupportedStatus.SUPPORTED;
    }

}
