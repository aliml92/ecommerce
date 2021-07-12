package com.xco.spactshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class UpdateReq {

    private String _id;
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private Boolean isAdmin;
}
