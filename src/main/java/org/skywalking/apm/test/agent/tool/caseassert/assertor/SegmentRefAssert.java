package org.skywalking.apm.test.agent.tool.caseassert.assertor;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.skywalking.apm.test.agent.tool.caseassert.entity.SegmentRef;
import org.skywalking.apm.test.agent.tool.caseassert.exception.AssertFailedException;

public class SegmentRefAssert {
    private static Logger logger = LogManager.getLogger(SegmentRefAssert.class);

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
            try {
                if (segmentRefEquals(expected, segmentRef)) {
                    return segmentRef;
                }
            } catch (AssertFailedException e) {
                logger.info("ref are not equal ignore this ref. \n{}", e.getMessage());
            }
        }
        return null;
    }

    private static boolean segmentRefEquals(SegmentRef expected, SegmentRef actual) {
        ExpressParser.parse(expected.entryServiceName()).assertValue("entry service name", actual.entryServiceName());
        ExpressParser.parse(expected.networkAddress()).assertValue("network address", actual.networkAddress());
        ExpressParser.parse(expected.parentTraceSegmentId()).assertValue("parent segment id", actual.parentTraceSegmentId());
        ExpressParser.parse(expected.parentSpanId()).assertValue("span id", actual.parentSpanId());
        ExpressParser.parse(expected.entryServiceId()).assertValue("entry service id", actual.entryServiceId());
        ExpressParser.parse(expected.networkAddressId()).assertValue("network address id", actual.networkAddressId());
        ExpressParser.parse(expected.parentApplicationInstanceId()).assertValue("parent application instance id", actual.parentApplicationInstanceId());
        ExpressParser.parse(expected.parentServiceId()).assertValue("parent service id", actual.parentServiceId());
        ExpressParser.parse(expected.parentServiceName()).assertValue("parent service name", actual.parentServiceName());
        ExpressParser.parse(expected.refType()).assertValue("ref type", actual.refType());
        ExpressParser.parse(expected.entryApplicationInstanceId()).assertValue("entry application instance id", actual.entryApplicationInstanceId());
        return true;
    }
}
