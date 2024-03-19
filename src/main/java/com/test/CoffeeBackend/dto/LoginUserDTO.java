package com.test.CoffeeBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO
{
    @Schema(description = "phone number of user", type = "string", example = "0912345678")
    private String phone;
    @Schema(description = "password", type = "string", example = "123456")
    private String password;
}
