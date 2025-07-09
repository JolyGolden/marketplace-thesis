package nazar.zhanabergenov.shop_monolith.controller.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nazar.zhanabergenov.shop_monolith.dto.UserResponse;
import nazar.zhanabergenov.shop_monolith.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }



}
