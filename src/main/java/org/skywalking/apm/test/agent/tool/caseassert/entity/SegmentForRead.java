package org.skywalking.apm.test.agent.tool.caseassert.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SegmentForRead implements Segment {
    private String segmentId;
    private List<SpanForRead> spans;

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

    public static class SegmentRefForRead implements SegmentRef {
        private String parentServiceId;
        private String parentServiceName;
        private String networkAddressId;
        private String entryServiceId;
        private String refType;
        private String parentSpanId;
        private String parentTraceSegmentId;
        private String parentApplicationInstanceId;
        private String networkAddress;
        private String entryServiceName;
        private String entryApplicationInstanceId;

        public String getParentServiceId() {
            return parentServiceId;
        }

        public void setParentServiceId(String parentServiceId) {
            this.parentServiceId = parentServiceId;
        }

        public String getParentServiceName() {
            return parentServiceName;
        }

        public void setParentServiceName(String parentServiceName) {
            this.parentServiceName = parentServiceName;
        }

        public String getNetworkAddressId() {
            return networkAddressId;
        }

        public void setNetworkAddressId(String networkAddressId) {
            this.networkAddressId = networkAddressId;
        }

        public String getEntryServiceId() {
            return entryServiceId;
        }

        public void setEntryServiceId(String entryServiceId) {
            this.entryServiceId = entryServiceId;
        }

        public String getRefType() {
            return refType;
        }

        public void setRefType(String refType) {
            this.refType = refType;
        }

        public String getParentSpanId() {
            return parentSpanId;
        }

        public void setParentSpanId(String parentSpanId) {
            this.parentSpanId = parentSpanId;
        }

        public String getParentTraceSegmentId() {
            return parentTraceSegmentId;
        }

        public void setParentTraceSegmentId(String parentTraceSegmentId) {
            this.parentTraceSegmentId = parentTraceSegmentId;
        }

        public String getParentApplicationInstanceId() {
            return parentApplicationInstanceId;
        }

        public void setParentApplicationInstanceId(String parentApplicationInstanceId) {
            this.parentApplicationInstanceId = parentApplicationInstanceId;
        }

        public String getNetworkAddress() {
            return networkAddress;
        }

        public void setNetworkAddress(String networkAddress) {
            this.networkAddress = networkAddress;
        }

        public String getEntryServiceName() {
            return entryServiceName;
        }

        public void setEntryServiceName(String entryServiceName) {
            this.entryServiceName = entryServiceName;
        }

        public String getEntryApplicationInstanceId() {
            return entryApplicationInstanceId;
        }

        public void setEntryApplicationInstanceId(String entryApplicationInstanceId) {
            this.entryApplicationInstanceId = entryApplicationInstanceId;
        }

        @Override public String parentServiceId() {
            return parentServiceId;
        }

        @Override public String parentServiceName() {
            return parentServiceName;
        }

        @Override public String networkAddressId() {
            return networkAddressId;
        }

        @Override public String entryServiceId() {
            return entryServiceId;
        }

        @Override public String refType() {
            return refType;
        }

        @Override public String parentSpanId() {
            return parentSpanId;
        }

        @Override public String parentTraceSegmentId() {
            return parentTraceSegmentId;
        }

        @Override public String parentApplicationInstanceId() {
            return parentApplicationInstanceId;
        }

        @Override public String networkAddress() {
            return networkAddress;
        }

        @Override public String entryServiceName() {
            return entryServiceName;
        }

        @Override public void parentTraceSegmentId(String parentTraceSegmentId) {
            this.parentTraceSegmentId = parentTraceSegmentId;
        }

        @Override public String entryApplicationInstanceId() {
            return entryApplicationInstanceId;
        }

    }

    public static class SpanForRead implements Span {
        private String operationName;
        private String operationId;
        private String parentSpanId;
        private String spanId;
        private String spanLayer;
        private List<Map<String, String>> tags;
        private List<Map<String, List<Map<String, String>>>> logs;
        private List<SegmentRefForRead> refs;
        private List<SegmentRef> actualRefs;
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

        public void setLogs(List<Map<String, List<Map<String, String>>>> logs) {
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

        public void setRefs(
            List<SegmentRefForRead> refs) {
            this.refs = refs;
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
                return new ArrayList<>();
            }
            List<KeyValuePair> result = new ArrayList<>();
            for (Map<String, String> tag : tags) {
                result.add(new KeyValuePair.Impl(tag.get("key"), tag.get("value")));
            }
            return result;
        }

        @Override public List<LogEvent> logs() {
            if (logs == null) {
                return new ArrayList<>();
            }
            List<LogEvent> result = new ArrayList<>();
            for (Map<String, List<Map<String, String>>> log : logs) {
                List<Map<String, String>> events = log.get("logEvent");
                LogEvent.Impl logEvent = new LogEvent.Impl();
                for (Map<String, String> event : events) {
                    logEvent.add(event.get("key"), event.get("value"));
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

        @Override public List<SegmentRef> refs() {
            if (refs == null) {
                return null;
            }
            return new ArrayList<>(refs);
        }

        @Override public void setActualRefs(List<SegmentRef> refs) {
            this.actualRefs = refs;
        }

        @Override public List<SegmentRef> actualRefs() {
            return actualRefs;
        }
    }

    public static class LogEventForRead {
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

    @Override public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    public void setSpans(List<SpanForRead> spans) {
        this.spans = spans;
    }
}
