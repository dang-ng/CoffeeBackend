package com.test.CoffeeBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDTO
{
    @Schema(description = "name of user", type = "string", example = "John Doe")
    private String fullName;
    @Schema(description = "phone number of user", type = "string", example = "0912345678")
    private String phone;
    @Schema(description = "user's address", type = "string", example = "District 9, HCMC")
    private String address;
    @Schema(description = "password", type = "string", example = "123456")
    private String password;
    @Schema(description = "retype password", type = "string", example = "123456")
    private String retypePassword;
}
