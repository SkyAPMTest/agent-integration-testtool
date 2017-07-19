package org.skywalking.apm.test.agent.tool.caseassert.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SegmentForRead implements Segment {
    private String segmentId;
    private List<SpanForRead> spans;
    private List<SegmentRefForRead> refs;
    private List<SegmentRef> actualRefs = new ArrayList<>();

    @Override
    public String segmentId() {
        return segmentId;
    }

    @Override public List<Span> spans() {
        if (spans == null) {
            return null;
        }
        return new ArrayList<>(spans);
    }

    @Override public List<SegmentRef> refs() {
        if (refs == null) {
            return null;
        }
        return new ArrayList<>(refs);
    }

    public static class SegmentRefForRead implements SegmentRef {
        private String spanId;
        private String parentSegmentId;
        private String networkAddress;
        private String entryServiceName;

        public String getSpanId() {
            return spanId;
        }

        public String getParentSegmentId() {
            return parentSegmentId;
        }

        public String getNetworkAddress() {
            return networkAddress;
        }

        public String getEntryServiceName() {
            return entryServiceName;
        }

        public void setSpanId(String spanId) {
            this.spanId = spanId;
        }

        public void setParentSegmentId(String parentSegmentId) {
            this.parentSegmentId = parentSegmentId;
        }

        public void setNetworkAddress(String networkAddress) {
            this.networkAddress = networkAddress;
        }

        public void setEntryServiceName(String entryServiceName) {
            this.entryServiceName = entryServiceName;
        }

        @Override public String spanId() {
            return spanId;
        }

        @Override public String parentSegmentId() {
            return parentSegmentId;
        }

        @Override public String networkAddress() {
            return networkAddress;
        }

        @Override public String entryServiceName() {
            return entryServiceName;
        }
    }

    public static class SpanForRead implements Span {
        private String operationName;
        private String operationId;
        private String parentSpanId;
        private String spanId;
        private String spanLayer;
        private List<Map<String, String>> tags;
        private List<LogEventForRead> logs;
        private String startTime;
        private String endTime;
        private String componentId;
        private String componentName;
        private boolean isError;
        private String spanType;
        private String peer;
        private String peerId;

        public void setOperationName(String operationName) {
            this.operationName = operationName;
        }

        public void setOperationId(String operationId) {
            this.operationId = operationId;
        }

        public void setParentSpanId(String parentSpanId) {
            this.parentSpanId = parentSpanId;
        }

        public void setSpanId(String spanId) {
            this.spanId = spanId;
        }

        public void setSpanLayer(String spanLayer) {
            this.spanLayer = spanLayer;
        }

        public void setTags(List<Map<String, String>> tags) {
            this.tags = tags;
        }

        public void setLogs(List<LogEventForRead> logs) {
            this.logs = logs;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public void setComponentId(String componentId) {
            this.componentId = componentId;
        }

        public void setComponentName(String componentName) {
            this.componentName = componentName;
        }

        public void setError(boolean error) {
            isError = error;
        }

        public void setSpanType(String spanType) {
            this.spanType = spanType;
        }

        public void setPeer(String peer) {
            this.peer = peer;
        }

        public void setPeerId(String peerId) {
            this.peerId = peerId;
        }

        @Override public String operationName() {
            return operationName;
        }

        @Override public String operationId() {
            return operationId;
        }

        @Override public String parentSpanId() {
            return parentSpanId;
        }

        @Override public String spanId() {
            return spanId;
        }

        @Override public String spanLayer() {
            return spanLayer;
        }

        @Override public List<KeyValuePair> tags() {
            if (tags == null) {
                return null;
            }
            List<KeyValuePair> result = new ArrayList<>();
            for (Map<String, String> tag : tags) {
                result.add(new KeyValuePair.Impl(tag.get("key"), tag.get("value")));
            }
            return result;
        }

        @Override public List<LogEvent> logs() {
            if (logs == null) {
                return null;
            }
            List<LogEvent> result = new ArrayList<>();
            for (LogEventForRead log : logs) {
                LogEvent.Impl logEvent = new LogEvent.Impl();
                for (Map<String, String> tag : log.getLogEvent()) {
                    logEvent.add(tag.get("key"), tag.get("value"));
                }
                result.add(logEvent);
            }

            return result;
        }

        @Override public String startTime() {
            return startTime;
        }

        @Override public String endTime() {
            return endTime;
        }

        @Override public String componentId() {
            return componentId;
        }

        @Override public String componentName() {
            return componentName;
        }

        @Override public boolean error() {
            return isError;
        }

        @Override public String spanType() {
            return spanType;
        }

        @Override public String peer() {
            return peer;
        }

        @Override public String peerId() {
            return peerId;
        }
    }

    static class LogEventForRead {
        private List<Map<String, String>> logEvent;

        public List<Map<String, String>> getLogEvent() {
            return logEvent;
        }

        public void setLogEvent(List<Map<String, String>> logEvent) {
            this.logEvent = logEvent;
        }
    }

    public String getSegmentId() {
        return segmentId;
    }

    public List<SpanForRead> getSpans() {
        return spans;
    }

    public List<SegmentRefForRead> getRefs() {
        return refs;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    @Override
    public void actualRefs(List<SegmentRef> actualRefs) {
        this.actualRefs = actualRefs;
    }

    @Override public List<SegmentRef> actualRefs() {
        return actualRefs;
    }

    public void setSpans(List<SpanForRead> spans) {
        this.spans = spans;
    }

    public void setRefs(List<SegmentRefForRead> refs) {
        this.refs = refs;
    }
}
