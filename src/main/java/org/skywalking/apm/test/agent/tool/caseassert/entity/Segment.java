package org.skywalking.apm.test.agent.tool.caseassert.entity;

import java.util.List;

public interface Segment {
    String segmentId();

    List<Span> spans();

    List<SegmentRef> refs();

    void setSegmentId(String segmentId);

    void actualRefs(List<SegmentRef> actualRefs);

    List<SegmentRef> actualRefs();
}
