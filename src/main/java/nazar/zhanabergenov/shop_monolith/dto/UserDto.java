package nazar.zhanabergenov.shop_monolith.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Positive
    private Long id;
    private String name;
    @Email
    private String email;
}
