package com.timely.demo.common.config.security.model;

import lombok.Data;

@Data
public class UserLoginDto {
    private String email;
    private String password;
}
