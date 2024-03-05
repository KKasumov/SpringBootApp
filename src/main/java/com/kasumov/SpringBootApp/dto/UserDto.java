package com.kasumov.SpringBootApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kasumov.SpringBootApp.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String username;
    private String email;

    public UserDto(Long id, String userName, String email) {
    }

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUserName(username);
        user.setEmail(email);
        return user;
    }

    public static UserDto fromUser(User user) {
        return Objects.isNull(user) ? null : new UserDto(user.getId(), user.getUserName(), user.getEmail());
    }

    public static List<UserDto> toUserDtos(List<User> users) {
        return users.stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
    }
}