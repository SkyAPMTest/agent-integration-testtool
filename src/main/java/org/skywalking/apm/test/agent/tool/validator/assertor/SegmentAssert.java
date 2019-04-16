package org.skywalking.apm.test.agent.tool.validator.assertor;

import java.util.ArrayList;
import java.util.List;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.ActualSegmentRefEmptyException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.KeyValueNotEqualsException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.LogEventKeyNotEqualsException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.LogEventSizeNotEqualsException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.LogEventValueNotEqualsException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.LogSizeNotEqualsException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.RefSizeNotEqualsException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.SegmentItemAssertFailedException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.SegmentNotFoundException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.SpanAssertFailedException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.SpanAttributeValueAssertFailedException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.SpanSizeNotEqualsException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.TagKeyNotEqualsException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.TagSizeNotEqualsException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.TagValueNotEqualsException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.ValueAssertFailedException;
import org.skywalking.apm.test.agent.tool.validator.entity.KeyValuePair;
import org.skywalking.apm.test.agent.tool.validator.entity.LogEvent;
import org.skywalking.apm.test.agent.tool.validator.entity.Segment;
import org.skywalking.apm.test.agent.tool.validator.entity.SegmentItem;
import org.skywalking.apm.test.agent.tool.validator.entity.SegmentRef;
import org.skywalking.apm.test.agent.tool.validator.entity.Span;

public class SegmentAssert {
    public static void assertEquals(SegmentItem expected, SegmentItem actual) {
        if (expected.segments() == null) {
            return;
        }

        for (Segment segment : expected.segments()) {
            try {
                Segment actualSegment = findSegment(actual, segment);
                segment.setSegmentId(actualSegment.segmentId());
            } catch (SegmentNotFoundException e) {
                throw new SegmentItemAssertFailedException(e);
            }
        }
    }

    private static Segment findSegment(SegmentItem actual, Segment expectedSegment) {
        List<SegmentPredictionFailedCause> exceptions = new ArrayList<>();
        for (Segment actualSegment : actual.segments()) {
            try {
                if (spansEquals(expectedSegment.spans(), actualSegment.spans())) {
                    return actualSegment;
                }
            } catch (SpanSizeNotEqualsException e) {
            } catch (SpanAssertFailedException e) {
                exceptions.add(new SegmentPredictionFailedCause(e, actualSegment));
            }
        }

        throw new SegmentNotFoundException(exceptions);
    }

    private static boolean spansEquals(List<Span> excepted, List<Span> actual) {
        if (excepted == null) {
            return true;
        }

        if (actual == null || excepted.size() != actual.size()) {
            throw new SpanSizeNotEqualsException();
        }

        for (int index = 0; index < excepted.size(); index++) {
            Span exceptedSpan = excepted.get(index);
            Span actualSpan = actual.get(index);
            try {
                spanEquals(exceptedSpan, actualSpan);
            } catch (SpanAttributeValueAssertFailedException e) {
                throw new SpanAssertFailedException(e, exceptedSpan, actualSpan);
            }
        }

        return true;
    }

    private static void spanEquals(Span excepted, Span actualSpan) {
        try {
            ExpressParser.parse(excepted.operationName()).assertValue("operation name", actualSpan.operationName());
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
        } catch (ValueAssertFailedException e) {
            throw new SpanAttributeValueAssertFailedException();
        }
    }

    private static void refEquals(List<SegmentRef> excepted, List<SegmentRef> actual) {
        if (excepted == null) {
            return;
        }

        if (actual == null) {
            throw new ActualSegmentRefEmptyException(excepted.size());
        }

        if (excepted.size() != actual.size()) {
            throw new RefSizeNotEqualsException(excepted.size(), actual.size());
        }
    }

    private static void tagsEquals(List<KeyValuePair> excepted, List<KeyValuePair> actual) {
        if (excepted.size() != actual.size()) {
            throw new TagSizeNotEqualsException(excepted.size(), actual.size());
        }

        for (int index = 0; index < excepted.size(); index++) {
            tagEquals(excepted.get(index), actual.get(index));
        }
    }

    private static void logsEquals(List<LogEvent> excepted, List<LogEvent> actual) {
        if (excepted.size() != actual.size()) {
            throw new LogSizeNotEqualsException(excepted.size(), actual.size());
        }

        for (int index = 0; index < excepted.size(); index++) {
            logEventEquals(excepted.get(index), actual.get(index));
        }

    }

    private static void logEventEquals(LogEvent exceptedEvent, LogEvent actualEvent) {
        List<KeyValuePair> exceptedKey = exceptedEvent.events();
        List<KeyValuePair> actualKey = actualEvent.events();

        if (exceptedKey.size() != actualKey.size()) {
            throw new LogEventSizeNotEqualsException(exceptedKey.size(), actualKey.size());
        }

        for (int index = 0; index < exceptedKey.size(); index++) {
            logEventPairEquals(exceptedKey.get(index), actualKey.get(index));
        }
    }

    private static void keyValuePairEquals(KeyValuePair excepted, KeyValuePair actual) {
        if (!excepted.key().equals(actual.key())) {
            throw new KeyValueNotEqualsException(excepted.key(), actual.key());
        }

        ExpressParser.parse(excepted.value()).assertValue("", actual.value());
    }

    private static void logEventPairEquals(KeyValuePair excepted, KeyValuePair actual) {
        try {
            keyValuePairEquals(excepted, actual);
        } catch (KeyValueNotEqualsException e) {
            throw new LogEventKeyNotEqualsException(excepted.key(), actual.key());
        } catch (ValueAssertFailedException e) {
            throw new LogEventValueNotEqualsException(excepted.key(), excepted.value(), actual.value());
        }
    }

    private static void tagEquals(KeyValuePair excepted, KeyValuePair actual) {
        try {
            keyValuePairEquals(excepted, actual);
        } catch (KeyValueNotEqualsException e) {
            throw new TagKeyNotEqualsException(excepted.key(), actual.key());
        } catch (ValueAssertFailedException e) {
            throw new TagValueNotEqualsException(excepted.key(), excepted.value(), actual.value());
        }
    }

}
