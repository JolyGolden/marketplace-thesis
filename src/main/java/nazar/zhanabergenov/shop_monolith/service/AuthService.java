package nazar.zhanabergenov.shop_monolith.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nazar.zhanabergenov.shop_monolith.dto.UserDto;
import nazar.zhanabergenov.shop_monolith.mapper.UserMapper;
import nazar.zhanabergenov.shop_monolith.model.User;
import nazar.zhanabergenov.shop_monolith.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepo;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    public UserDto createUser(UserDto dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        return mapper.toDto(userRepo.save(mapper.toEntity(dto)));
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User u = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return new org.springframework.security.core.userdetails.User(
                u.getEmail(), u.getPassword(), List.of());
    }

    public UserDto getUserByEmail(String email) {
        return mapper.toDto(userRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No user " + email)));
    }
}
