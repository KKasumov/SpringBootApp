package com.kasumov.SpringBootApp.config;

import com.kasumov.SpringBootApp.security.jvt.JwtConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/",
            "/api/v1/auth/**"
    };

    private static final String[] USER_ROLES_ENDPOINTS = {
            "/api/v1/users/**",
            "/api/v1/events/**",
            "/api/v1/files/**"
    };

    private static final String[] ADMIN_ROLES_ENDPOINTS = {
            "/api/v1/users/**",
            "/api/v1/events/**",
            "/api/v1/files/**"
    };

    private final JwtConfigurer jwtConfigurer;

    @Autowired
    public SecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.GET, USER_ROLES_ENDPOINTS).hasAnyRole("ADMIN", "MODERATOR", "USER")
                .antMatchers(HttpMethod.POST, ADMIN_ROLES_ENDPOINTS).hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, ADMIN_ROLES_ENDPOINTS).hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.DELETE, ADMIN_ROLES_ENDPOINTS).hasAnyRole("ADMIN", "MODERATOR")
                .anyRequest().authenticated()
                .and()
                .apply(jwtConfigurer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}