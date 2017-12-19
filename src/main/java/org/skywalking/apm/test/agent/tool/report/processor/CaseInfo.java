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

public class CaseInfo {
    private String testFramework;
    private String projectName;

    public CaseInfo(String testFramework, String projectName) {
        this.testFramework = testFramework;
        this.projectName = projectName;
    }

    public String getTestFramework() {
        return testFramework;
    }

    public String getProjectName() {
        return projectName;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CaseInfo info = (CaseInfo)o;

        if (testFramework != null ? !testFramework.equals(info.testFramework) : info.testFramework != null) {
            return false;
        }
        return projectName != null ? projectName.equals(info.projectName) : info.projectName == null;
    }

    @Override public int hashCode() {
        int result = testFramework != null ? testFramework.hashCode() : 0;
        result = 31 * result + (projectName != null ? projectName.hashCode() : 0);
        return result;
    }
}
