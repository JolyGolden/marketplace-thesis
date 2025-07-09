package nazar.zhanabergenov.shop_monolith.dto;

import lombok.Builder;
import lombok.Data;
import nazar.zhanabergenov.shop_monolith.model.User;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private boolean active;

    public static UserResponseDto fromUser(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .active(user.isActive())
                .build();
    }
}
