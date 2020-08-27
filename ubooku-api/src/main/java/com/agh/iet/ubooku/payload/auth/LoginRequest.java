package com.agh.iet.ubooku.payload.auth;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

}
