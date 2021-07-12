package com.xco.spactshop.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JwtRes {
    private String _id , name, email, token;
    private Boolean isAdmin;

    public JwtRes(String _id, String name, String email, String token, Boolean isAdmin) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.token = token;
        this.isAdmin = isAdmin;
    }
}
