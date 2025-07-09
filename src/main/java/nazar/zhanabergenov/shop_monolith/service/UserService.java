package nazar.zhanabergenov.shop_monolith.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nazar.zhanabergenov.shop_monolith.dto.UserDto;
import nazar.zhanabergenov.shop_monolith.exception.NotFoundException;
import nazar.zhanabergenov.shop_monolith.mapper.UserMapper;
import nazar.zhanabergenov.shop_monolith.model.User;
import nazar.zhanabergenov.shop_monolith.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        User user = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto getUser(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found")));
    }
}
