package org.skywalking.apm.test.agent.tool.caseassert.entity;

/**
 * Created by xin on 2017/7/15.
 */
public interface SegmentRef {

    String parentServiceId();

    String parentServiceName();

    String networkAddressId();

    String entryServiceId();

    String refType();

    String parentSpanId();

    String parentTraceSegmentId();

    String parentApplicationInstanceId();

    String networkAddress();

    String entryServiceName();

    void parentTraceSegmentId(String parentTraceSegmentId);

}
