package org.example.signuplogin.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenDto {
    @NotBlank(message = "Please Provide Refresh Token")
    public String token;
}
