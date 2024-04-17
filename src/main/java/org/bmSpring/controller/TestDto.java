package org.bmSpring.controller;

import lombok.Getter;

@Getter
public class TestDto {
    private String name;
    private Integer test = 1000;
    private String className = this.getClassName();

    public TestDto(String name) {
        this.name = name;
    }
}
