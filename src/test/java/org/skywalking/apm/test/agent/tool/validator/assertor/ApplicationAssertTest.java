/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.skywalking.apm.test.agent.tool.validator.assertor;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.skywalking.apm.test.agent.tool.validator.entity.RegistryApplication;
import org.skywalking.apm.test.agent.tool.validator.exception.RegistryApplicationNotFoundException;

public class ApplicationAssertTest {

    @Test(expected = RegistryApplicationNotFoundException.class)
    public void assertApplicationNotFound() {

        List<RegistryApplication> expected = new ArrayList<>();
        List<RegistryApplication> actual = new ArrayList<>();
        ApplicationAssert.assertEquals(expected, actual);
    }

}