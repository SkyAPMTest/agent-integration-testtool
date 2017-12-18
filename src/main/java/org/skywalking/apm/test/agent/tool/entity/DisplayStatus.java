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

public class DisplayStatus {
    public static final DisplayStatus ERROR = new DisplayStatus(0, 1);

    private String desc;
    private String color;

    public DisplayStatus(int success, int total) {
        this.desc = success + "/" + total;
        double rate = success / (total * 1.0);
        if (rate > 0.9) {
            this.color = "brightgreen";
        } else if (rate > 0.8) {
            this.color = "green";
        } else if (rate > 0.7) {
            this.color = "yellowgreen";
        } else if (rate > 0.6) {
            this.color = "yellow";
        } else {
            this.color = "red";
        }
    }

    public String getDesc() {
        return desc;
    }

    public String getColor() {
        return color;
    }
}
