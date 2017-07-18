package org.skywalking.apm.test.agent.tool.caseassert.entity;

import java.util.List;

/**
 * Created by xin on 2017/7/15.
 */
public interface SegmentItem {
    String applicationCode();

    String segmentSize();

    List<Segment> segments();
}
