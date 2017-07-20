package org.skywalking.apm.test.agent.tool.caseassert.assertor;

import org.skywalking.apm.test.agent.tool.caseassert.assertor.element.ElementAssertor;
import org.skywalking.apm.test.agent.tool.caseassert.assertor.element.EqualsAssertor;
import org.skywalking.apm.test.agent.tool.caseassert.assertor.element.GreatThanAssertor;
import org.skywalking.apm.test.agent.tool.caseassert.assertor.element.NoopAssertor;
import org.skywalking.apm.test.agent.tool.caseassert.assertor.element.NotEqualsAssertor;
import org.skywalking.apm.test.agent.tool.caseassert.assertor.element.NotNullAssertor;
import org.skywalking.apm.test.agent.tool.caseassert.exception.AssertFailedException;

public class ExpressParser {
    public static ElementAssertor parse(String express) {
        if (express == null) {
            return new NoopAssertor();
        }

        String expressTrim = express.trim();
        if (expressTrim.equals("not null")) {
            return new NotNullAssertor();
        }

        if (expressTrim.equals("null")) {
            return new NotNullAssertor();
        }

        String[] expressSegment = expressTrim.split(" ");
        if (expressSegment.length == 1) {
            return new EqualsAssertor(expressSegment[0]);
        } else if (expressSegment.length == 2) {
            String exceptedValue = expressSegment[1];
            switch (expressSegment[0].trim()) {
                case "nq":
                    return new NotEqualsAssertor(exceptedValue);
                case "eq":
                    return new EqualsAssertor(exceptedValue);
                case "gt":
                    return new GreatThanAssertor(exceptedValue);
            }
        }

        return new EqualsAssertor(express);
    }
}
