package org.skywalking.apm.test.agent.tool.caseassert.entity;

/**
 * Created by xin on 2017/7/15.
 */
public interface SegmentRef {

    String spanId();

    String parentSegmentId();

    void setParentSegmentId(String segmentId);

    String networkAddress();

    String entryServiceName();
}
