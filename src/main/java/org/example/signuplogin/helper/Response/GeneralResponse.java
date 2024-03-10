package org.example.signuplogin.helper.Response;

import lombok.Data;

//Custom Response Class for the Message and alerts
@Data
public class GeneralResponse {
    private boolean  flag;
    private String message;

    public GeneralResponse(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }
}
