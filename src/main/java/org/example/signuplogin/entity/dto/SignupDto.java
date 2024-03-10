package org.example.signuplogin.entity.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data

public class SignupDto {

    @NotBlank(message = "displayName is required")
    public String displayName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")

    public String email;

    @NotBlank(message = "Password is required")
    private String password;




}

