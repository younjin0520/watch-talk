package com.mysite.springboard;

import lombok.Getter;

/**
** User에게 부여할 권한 목록
**/
@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
