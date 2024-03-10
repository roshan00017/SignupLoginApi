package org.example.signuplogin.helper.Response;

import lombok.Data;


// Class for Login Response
@Data
public class LoginResponse {
    private boolean  flag;
    private String message;

    private String token;

    private String refreshToken;

    public LoginResponse(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
        this.token = "Token Cannot be genrated";
        this.refreshToken = "Refresh Token Cannot be genrated";
    }

    public LoginResponse(boolean flag, String message, String token, String refreshToken) {
        this.flag = flag;
        this.message = message;
        this.token = token;
        this.refreshToken = refreshToken;
    }


}
