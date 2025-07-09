package nazar.zhanabergenov.shop_monolith.mapper;

import nazar.zhanabergenov.shop_monolith.dto.RegisterRequest;
import nazar.zhanabergenov.shop_monolith.dto.UserDto;
import nazar.zhanabergenov.shop_monolith.dto.UserResponse;
import nazar.zhanabergenov.shop_monolith.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);
    UserDto toDto(User user);

    User toEntity(RegisterRequest req);
    UserResponse toResponse(User user);
}
