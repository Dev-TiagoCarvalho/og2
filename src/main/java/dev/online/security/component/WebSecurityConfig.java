package dev.online.security.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain configSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable().authorizeHttpRequests()
                .anyRequest().authenticated().and().httpBasic().and().build();
    }

    @Bean
    public PasswordEncoder configPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
