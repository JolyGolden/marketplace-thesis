package nazar.zhanabergenov.shop_monolith.controller.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nazar.zhanabergenov.shop_monolith.dto.LoginRequest;
import nazar.zhanabergenov.shop_monolith.dto.RegisterRequest;
import nazar.zhanabergenov.shop_monolith.dto.UserDto;
import nazar.zhanabergenov.shop_monolith.dto.UserResponse;
import nazar.zhanabergenov.shop_monolith.model.User;
import nazar.zhanabergenov.shop_monolith.service.AuthService;
import nazar.zhanabergenov.shop_monolith.service.UserService;
import nazar.zhanabergenov.shop_monolith.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest req) {
        User user = authService.register(req);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.toResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody @Valid LoginRequest req) {
        String token = authService.loginAndGetToken(req);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
