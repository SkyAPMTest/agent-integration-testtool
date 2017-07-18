package org.skywalking.apm.test.agent.tool.caseassert.assertor;

import java.util.List;
import org.skywalking.apm.test.agent.tool.caseassert.entity.SegmentRef;
import org.skywalking.apm.test.agent.tool.caseassert.exception.AssertFailedException;

public class SegmentRefAssert {
    public static void assertEquals(List<SegmentRef> excepted, List<SegmentRef> actual) {
        if (excepted == null) {
            return;
        }

        if (actual == null || excepted.size() != actual.size()) {
            throw new AssertFailedException("segment refs: \n expected: " + excepted.size() + "\n actual: " + actual.size());
        }

        for (SegmentRef ref : excepted) {
            SegmentRef actualRef = findSegmentRef(actual, ref);
            if (actualRef == null) {
                throw new AssertFailedException("segment refs: \n expected: 1\n actual: not found");
            }
        }
    }

    private static SegmentRef findSegmentRef(List<SegmentRef> actual, SegmentRef expected) {
        for (SegmentRef segmentRef : actual) {
            if (segmentRefEquals(expected, segmentRef)) {
                return segmentRef;
            }
        }
        return null;
    }

    private static boolean segmentRefEquals(SegmentRef expected, SegmentRef actual) {
        try {
            ExpressParser.parse(expected.entryServiceName()).assertValue(actual.entryServiceName());
            ExpressParser.parse(expected.networkAddress()).assertValue(actual.networkAddress());
            ExpressParser.parse(expected.parentSegmentId()).assertValue(actual.parentSegmentId());
            ExpressParser.parse(expected.spanId()).assertValue(actual.spanId());
            return true;
        } catch (AssertFailedException e) {
            return false;
        }
    }
}
