package com.xco.spactshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class LoginReq {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

}
