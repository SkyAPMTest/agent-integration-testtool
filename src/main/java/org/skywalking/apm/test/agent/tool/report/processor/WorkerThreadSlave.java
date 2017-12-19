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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xin
 */
public abstract class WorkerThreadSlave<T, V> implements SlaveSpec<T, V> {

    private final ExecutorService executorService;

    protected WorkerThreadSlave(BlockingQueue<Runnable> queue) {
        this.executorService = new ThreadPoolExecutor(0, 10, 10, TimeUnit.SECONDS, queue, r -> {
            Thread thread = new Thread(r, "[WorkerThreadSlave]");
            return thread;
        });
    }

    @Override
    public Future<V> submit(T task) throws InterruptedException {
        FutureTask<V> futureTask = new FutureTask<V>(new Callable<V>() {
            @Override public V call() throws Exception {
                V result;
                try {
                    result = doProcess(task);
                } catch (Exception e) {
                    throw new SubTaskFailureException(new RetryInfo<T, V>(task, () -> doProcess(task)), e);
                }
                return result;
            }
        });
        return (Future<V>)executorService.submit(futureTask);
    }

    /**
     * @param task
     * @return
     */
    protected abstract V doProcess(T task);

    @Override
    public final void shutdown() {
        executorService.shutdown();
    }
}
