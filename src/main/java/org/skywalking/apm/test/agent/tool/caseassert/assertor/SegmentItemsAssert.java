package org.skywalking.apm.test.agent.tool.caseassert.assertor;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.skywalking.apm.test.agent.tool.caseassert.entity.Segment;
import org.skywalking.apm.test.agent.tool.caseassert.entity.SegmentItem;
import org.skywalking.apm.test.agent.tool.caseassert.entity.SegmentRef;
import org.skywalking.apm.test.agent.tool.caseassert.exception.AssertFailedException;

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
            if (actualSegmentItem == null) {
                throw new AssertFailedException("assert application[" + item.applicationCode() + "] segmentItems: \n expected: has " + item.segments() + " \n actual: not found");
            }


            assertSegmentSize(item.segmentSize(), actualSegmentItem.segmentSize());
            SegmentAssert.assertEquals(item, actualSegmentItem);
        }

        for (SegmentItem item : expected) {
            if (item.segments() == null) {
                continue;
            }

            for (Segment segment : item.segments()) {
                convertParentSegmentId(segment, expected);
                SegmentRefAssert.assertEquals(segment.refs(), segment.actualRefs());
            }
        }
    }

    private static void convertParentSegmentId(Segment segment, List<SegmentItem> actual) {
        if (segment.refs() == null) {
            return;
        }

        for (SegmentRef ref : segment.refs()) {
            String actualParentSegmentId = ParentSegmentIdExpressParser.parse(ref.parentSegmentId(), actual);
            ref.setParentSegmentId(actualParentSegmentId);
        }
    }

    private static void assertSegmentSize(String expected, String actual) {
        if (expected != null) {
            return;
        }
        ExpressParser.parse(expected).assertValue("segment size", actual);
    }

    private static SegmentItem findSegmentItem(List<SegmentItem> actual, String applicationCode) {
        if (actual == null) {
            return null;
        }

        for (SegmentItem segmentItem : actual) {
            if (applicationCode.equals(segmentItem.applicationCode())) {
                return segmentItem;
            }
        }

        return null;
    }
}
