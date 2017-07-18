package org.skywalking.apm.test.agent.tool.caseassert.assertor;

import java.util.List;
import org.skywalking.apm.test.agent.tool.caseassert.entity.SegmentItem;
import org.skywalking.apm.test.agent.tool.caseassert.exception.AssertFailedException;

/**
 * Created by xin on 2017/7/16.
 */
public class ParentSegmentIdExpressParser {
    public static String parse(String express, List<SegmentItem> actual) {
        if (!express.trim().startsWith("${") && !express.trim().endsWith("}")) {
            return express;
        }

        String parentSegmentExpress = express.trim().substring(2, express.trim().length() - 1);

        int startIndexOfIndex = parentSegmentExpress.indexOf("[");
        String applicationCode = parentSegmentExpress.substring(0, startIndexOfIndex);
        int endIndexOfIndex = parentSegmentExpress.indexOf("]", startIndexOfIndex);
        int expectedSize = Integer.parseInt(parentSegmentExpress.substring(startIndexOfIndex + 1, endIndexOfIndex));
        for (SegmentItem segmentItem : actual) {
            if (segmentItem.applicationCode().equals(applicationCode)) {
                if (segmentItem.segments().size() <= expectedSize) {
                    throw new AssertFailedException("segment size\nexpected: " + (expectedSize + 1) + ", actual: " + segmentItem.segments().size());
                }

                return segmentItem.segments().get(expectedSize).segmentId();
            }
        }
        return express;
    }
}
