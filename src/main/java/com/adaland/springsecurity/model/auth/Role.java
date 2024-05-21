package com.adaland.springsecurity.model.auth;

import java.util.Arrays;
import java.util.List;

public enum Role {

    CUSTOMER(List.of(Permission.READ_ALL_PRODUCTS)),

    ADMIN(Arrays.asList(Permission.READ_ALL_PRODUCTS, Permission.SAVE_ONE_PRODUCT));

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
