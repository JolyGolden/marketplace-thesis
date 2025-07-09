package nazar.zhanabergenov.shop_monolith.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotBlank @Size(min = 2) String firstName,
        @NotBlank @Size(min = 2) String lastName
) {}
