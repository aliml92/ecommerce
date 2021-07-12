package com.xco.spactshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileReq {

    private String id;
    private String email;
    private String name;
    private String password;

    public ProfileReq(String id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
