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

public class CategoryForProject {
    private DisplayStatus status;
    private String name;
    private List<TestCase> testCases;

    private CategoryForProject(String name) {
        this.name = name;
        testCases = new ArrayList<>();
    }

    public static final CategoryForProject buildCategoryForProject(TestCase testCase) {
        return new CategoryForProject(testCase.getProjectName());
    }

    public void addTestCases(TestCase testCase) {
        this.testCases.add(testCase);
    }

    public String getName() {
        return name;
    }

    public Context generateStatus() {
        int count = 0;
        for (TestCase aCase : testCases) {
            if (aCase.supported()) {
                count++;
            }
        }

        status = new DisplayStatus(count, testCases.size());
        return new Context(testCases.size(), count);
    }

    public DisplayStatus getStatus() {
        return status;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }
}
