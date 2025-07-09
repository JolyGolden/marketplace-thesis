package nazar.zhanabergenov.shop_monolith.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nazar.zhanabergenov.shop_monolith.dto.UserDto;
import nazar.zhanabergenov.shop_monolith.dto.UserResponse;
import nazar.zhanabergenov.shop_monolith.mapper.UserMapper;
import nazar.zhanabergenov.shop_monolith.model.User;
import nazar.zhanabergenov.shop_monolith.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final UserMapper mapper;

    public UserResponse getUserById(Long id) {
        User user = repo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
        return mapper.toResponse(user);
    }

    public UserResponse getUserByEmail(String email) {
        return mapper.toResponse(repo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No user with email: " + email)));
    }
}
