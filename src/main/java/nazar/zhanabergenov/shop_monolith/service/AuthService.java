package nazar.zhanabergenov.shop_monolith.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nazar.zhanabergenov.shop_monolith.dto.LoginRequest;
import nazar.zhanabergenov.shop_monolith.dto.RegisterRequest;
import nazar.zhanabergenov.shop_monolith.dto.UserDto;
import nazar.zhanabergenov.shop_monolith.dto.UserResponse;
import nazar.zhanabergenov.shop_monolith.mapper.UserMapper;
import nazar.zhanabergenov.shop_monolith.model.User;
import nazar.zhanabergenov.shop_monolith.repository.UserRepository;
import nazar.zhanabergenov.shop_monolith.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepo;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public User register(RegisterRequest req) {
        String hash = encoder.encode(req.password());
        User u = mapper.toEntity(req);
        u.setPassword(hash);
        return userRepo.save(u);
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User u = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return new org.springframework.security.core.userdetails.User(
                u.getEmail(), u.getPassword(), List.of());
    }

    public String loginAndGetToken(LoginRequest req) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.email(), req.password())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            return jwtUtil.generateToken(req.email());
        } catch (AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неверный email или пароль", ex);
        }
    }

    public UserResponse toResponse(User u) {
        return mapper.toResponse(u);
    }
}
