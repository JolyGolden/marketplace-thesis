package nazar.zhanabergenov.shop_monolith.dto;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName
) {}
