package com.test.CoffeeBackend.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO
{
    private String phone;
    private String accessToken;
}
