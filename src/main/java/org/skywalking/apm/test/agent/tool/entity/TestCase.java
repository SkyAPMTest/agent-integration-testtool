package org.skywalking.apm.test.agent.tool.entity;

import java.util.List;

public class TestCase {
    private String caseName;
    private List<Component> components;
    private Status status;

    public TestCase(String name, List<Component> components) {
       this(name, components, Status.ERROR);
    }

    public TestCase(String name, List<Component> components, Status status) {
        this.caseName = name;
        this.components = components;
        this.status = status;
    }

    public boolean faileOrError() {
        return status == Status.FAILED || status == Status.ERROR;
    }

    public static class Component {
        private String name;

        public Component(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
