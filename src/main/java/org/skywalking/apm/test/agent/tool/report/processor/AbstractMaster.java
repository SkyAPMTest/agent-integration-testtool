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

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * @author xin
 */
public abstract class AbstractMaster<T, V, R, I> {
    protected volatile Set<? extends SlaveSpec<T, V>> slaves;

    private volatile SubTaskDispatchStrategy<T, V> dispatchStrategy;

    public AbstractMaster() {
        slaves = createSlaves();
        dispatchStrategy = newSubTaskDispatchStrategy();
    }

    public R service(List<I> params) throws Exception {
        final TaskDivideStrategy<T> taskDivideStrategy = newTaskDivideStrategy(params);

        Iterator<Future<V>> subResults = dispatchStrategy.dispatch(slaves, taskDivideStrategy);

        for (SlaveSpec<T, V> slave : slaves) {
            slave.shutdown();
        }

        return combineResult(subResults);
    }

    protected SubTaskDispatchStrategy<T, V> newSubTaskDispatchStrategy() {
        return new RoundRobinSubTaskDispatchStrategy<>();
    }

    /**
     * @param results
     * @return
     */
    protected abstract R combineResult(Iterator<Future<V>> results);

    /**
     * @param params
     * @return
     */
    protected abstract TaskDivideStrategy<T> newTaskDivideStrategy(List<I> params);

    /**
     * @return
     */
    protected abstract Set<? extends SlaveSpec<T, V>> createSlaves();
}
