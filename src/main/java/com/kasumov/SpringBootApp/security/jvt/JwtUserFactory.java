package com.kasumov.SpringBootApp.security.jvt;

import com.kasumov.SpringBootApp.model.Role;
import com.kasumov.SpringBootApp.model.Status;
import com.kasumov.SpringBootApp.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus() == Status.ACTIVE,
                user.getUpdated(),
                mapToGrantedAuthorities(Collections.singletonList(user.getRole()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());
    }
}