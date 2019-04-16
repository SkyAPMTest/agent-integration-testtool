package org.skywalking.apm.test.agent.tool.validator.assertor;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.skywalking.apm.test.agent.tool.validator.entity.Segment;
import org.skywalking.apm.test.agent.tool.validator.entity.SegmentItem;
import org.skywalking.apm.test.agent.tool.validator.entity.SegmentRef;
import org.skywalking.apm.test.agent.tool.validator.entity.Span;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.ActualSegmentItemEmptyException;
import org.skywalking.apm.test.agent.tool.validator.assertor.exception.SegmentItemNotEqualsException;

/**
 * Created by xin on 2017/7/15.
 */
public class SegmentItemsAssert {
    private static Logger logger = LogManager.getLogger(SegmentItemsAssert.class);

    public static void assertEquals(List<SegmentItem> expected, List<SegmentItem> actual) {
        if (expected == null) {
            logger.info("ignore segment items. because expected segment item is null.");
            return;
        }

        for (SegmentItem item : expected) {
            SegmentItem actualSegmentItem = findSegmentItem(actual, item.applicationCode());
            assertSegmentSize(item.segmentSize(), actualSegmentItem.segmentSize());
            SegmentAssert.assertEquals(item, actualSegmentItem);
        }

        for (SegmentItem item : expected) {
            if (item.segments() == null) {
                continue;
            }

            for (Segment segment : item.segments()) {
                convertParentSegmentId(segment, expected);

                for (Span span : segment.spans()) {
                    if (span.refs() == null || span.refs().size() == 0) {
                        continue;
                    }
                    SegmentRefAssert.assertEquals(span.refs(), span.actualRefs());
                }

            }
        }
    }

    private static void convertParentSegmentId(Segment segment, List<SegmentItem> actual) {
        for (Span span : segment.spans()) {
            if (span.refs() == null || span.refs().size() == 0) {
                continue;
            }

            for (SegmentRef ref : span.refs()) {
                String actualParentSegmentId = ParentSegmentIdExpressParser.parse(ref.parentTraceSegmentId(), actual);
                ref.parentTraceSegmentId(actualParentSegmentId);
            }
        }
    }

    private static void assertSegmentSize(String expected, String actual) {
        if (expected == null) {
            return;
        }
        ExpressParser.parse(expected).assertValue("segment size", actual);
    }

    private static SegmentItem findSegmentItem(List<SegmentItem> actual, String applicationCode) {
        if (actual == null) {
            throw new ActualSegmentItemEmptyException();
        }

        for (SegmentItem segmentItem : actual) {
            if (applicationCode.equals(segmentItem.applicationCode())) {
                return segmentItem;
            }
        }

        throw new SegmentItemNotEqualsException(applicationCode);
    }
}
