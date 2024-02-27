package com.gl.employeemanagementsystem.model;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN(1, "ADMIN"),
    USER(2, "USER");

    private final int id;
    private final String name;

    UserRole(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
