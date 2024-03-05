package com.kasumov.SpringBootApp.rest;

import com.kasumov.SpringBootApp.dto.UserDto;
import com.kasumov.SpringBootApp.model.User;
import com.kasumov.SpringBootApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        UserDto result = UserDto.fromUser(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/byname/{name}")
    public ResponseEntity<UserDto> getUserByName(@PathVariable String name) {
        User user = userService.findByUsername(name);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        UserDto result = UserDto.fromUser(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        List<User> users = userService.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<UserDto> userDtos = UserDto.toUserDtos(users);
        return ResponseEntity.ok(userDtos);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        User resultUser = userService.create(user);
        return ResponseEntity.ok(resultUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        if (id == null || user == null) {
            return ResponseEntity.noContent().build();
        }
        User updateUser = userService.update(user);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.noContent().build();
        }
        User user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        userService.deleteById(id);
        return ResponseEntity.ok(user);
    }
}