package org.skywalking.apm.test.agent.tool.caseassert.assertor;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.skywalking.apm.test.agent.tool.caseassert.entity.KeyValuePair;
import org.skywalking.apm.test.agent.tool.caseassert.entity.LogEvent;
import org.skywalking.apm.test.agent.tool.caseassert.entity.Segment;
import org.skywalking.apm.test.agent.tool.caseassert.entity.SegmentItem;
import org.skywalking.apm.test.agent.tool.caseassert.entity.SegmentRef;
import org.skywalking.apm.test.agent.tool.caseassert.entity.Span;
import org.skywalking.apm.test.agent.tool.caseassert.exception.AssertFailedException;

public class SegmentAssert {
    private static Logger logger = LogManager.getLogger(SegmentAssert.class);

    public static void assertEquals(SegmentItem expected, SegmentItem actual) {
        if (expected.segments() == null) {
            return;
        }

        for (Segment segment : expected.segments()) {
            Segment actualSegment = findSegment(actual, segment);
            if (actualSegment == null) {
                throw new AssertFailedException("assert application[" + expected.applicationCode() + "] segment: \n expected count : 1 \n actual: 0");
            }
            segment.setSegmentId(actualSegment.segmentId());
        }
    }

    private static Segment findSegment(SegmentItem actual, Segment expectedSegment) {
        for (Segment actualSegment : actual.segments()) {
            if (spansEquals(expectedSegment.spans(), actualSegment.spans())) {
                return actualSegment;
            }
        }
        return null;
    }

    private static boolean spansEquals(List<Span> excepted, List<Span> actual) {
        if (excepted == null) {
            return true;
        }

        if (actual == null || excepted.size() != actual.size()) {
            logger.info("excepted span size are not equals actual span size, ignore this segment.");
            return false;
        }

        logger.info("excepted span size is equals actual span size, begin to assert span.");
        for (int index = 0; index < excepted.size(); index++) {
            Span exceptedSpan = excepted.get(index);
            Span actualSpan = actual.get(index);
            try {
                spanEquals(exceptedSpan, actualSpan);
            } catch (AssertFailedException e) {
                logger.info("span[{}] are not equal ignore this segment. \n{}", exceptedSpan.operationName(), e.getMessage());
                return false;
            }
        }

        return true;
    }

    private static boolean spanEquals(Span excepted, Span actualSpan) {
        ExpressParser.parse(excepted.componentId()).assertValue("component id", actualSpan.componentId());
        ExpressParser.parse(excepted.componentName()).assertValue("component name", actualSpan.componentName());
        ExpressParser.parse(excepted.startTime()).assertValue("start time", actualSpan.startTime());
        ExpressParser.parse(excepted.endTime()).assertValue("end time", actualSpan.endTime());
        ExpressParser.parse(excepted.parentSpanId()).assertValue("parent span id", actualSpan.parentSpanId());
        ExpressParser.parse(excepted.spanId()).assertValue("span id", actualSpan.spanId());
        ExpressParser.parse(excepted.operationId()).assertValue("operation id", actualSpan.operationId());
        ExpressParser.parse(excepted.peer()).assertValue("peer", actualSpan.peer());
        ExpressParser.parse(excepted.spanLayer()).assertValue("span layer", actualSpan.spanLayer());
        ExpressParser.parse(excepted.peerId()).assertValue("peer id", actualSpan.peerId());
        tagsEquals(excepted.tags(), actualSpan.tags());
        logsEquals(excepted.logs(), actualSpan.logs());

        refEquals(excepted.refs(), actualSpan.refs());
        excepted.setActualRefs(actualSpan.refs());
        return true;

    }

    private static void refEquals(List<SegmentRef> excepted, List<SegmentRef> actual) {
        if (actual == null || excepted.size() != actual.size()) {
            throw new AssertFailedException("ref is not equals");
        }
    }

    private static void tagsEquals(List<KeyValuePair> excepted, List<KeyValuePair> actual) {
        if (excepted.size() != actual.size()) {
            throw new AssertFailedException("assert tags: \nexcepted size: " + excepted.size() + "\n actual size: " + actual.size());
        }

        for (int index = 0; index < excepted.size(); index++) {
            keyValuePairEquals("tags", excepted.get(index), actual.get(index));
        }
    }

    private static void logsEquals(List<LogEvent> excepted, List<LogEvent> actual) {
        if (excepted.size() != actual.size()) {
            throw new AssertFailedException("assert logs: \nexcepted size: " + excepted.size() + "\n actual size: " + actual.size());
        }

        for (int index = 0; index < excepted.size(); index++) {
            logEventEquals(excepted.get(index), actual.get(index));
        }

    }

    private static void logEventEquals(LogEvent exceptedEvent, LogEvent actualEvent) {
        List<KeyValuePair> exceptedKey = exceptedEvent.events();
        List<KeyValuePair> actualKey = actualEvent.events();

        if (exceptedKey.size() != actualKey.size()) {
            throw new AssertFailedException("assert log event: \nexcepted size: " + exceptedKey.size() + "\n actual size: " + actualKey.size());
        }

        for (int index = 0; index < exceptedKey.size(); index++) {
            keyValuePairEquals("log event ", exceptedKey.get(index), actualKey.get(index));
        }
    }

    private static void keyValuePairEquals(String desc, KeyValuePair excepted, KeyValuePair actual) {
        if (!excepted.key().equals(actual.key())) {
            throw new AssertFailedException("assert key of keyValuePair \nexcepted: " + excepted.key() + "\nactual: " + actual.key());
        }

        ExpressParser.parse(excepted.value()).assertValue(desc, actual.value());
    }

}
