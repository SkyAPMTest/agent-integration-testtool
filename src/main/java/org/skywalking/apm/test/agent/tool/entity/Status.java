package org.skywalking.apm.test.agent.tool.entity;

public class Status {

    public static final Status FAILED = new Status("failed", "red");
    public static final Status ERROR = new Status("error", "red");
    public static final Status PASSING = new Status("passing", "brightgreen");

    private String desc;
    private String color;

    private Status(String desc, String color) {
        this.desc = desc;
        this.color = color;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
