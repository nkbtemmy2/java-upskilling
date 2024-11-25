package com.imanzi.rsa_authanticator.config;

import com.imanzi.rsa_authanticator.utils.JwtTokenVerifier;
import com.imanzi.rsa_authanticator.utils.KeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private static final String[] WHITE_LIST_URLS = {
            "/api/v1/auths/**",
            "/api/v1/docs",
            "/api/v1/swagger-ui.html",
            "/api/v1/swagger-ui/**",
            "/api/v1/swagger-resources/**",
            "/api/v1/configuration/ui",
            "/api/v1/configuration/security",
            "/api/v1/webjars/**",
            "/api/v1/v2/api-docs",
            "/api/v1/v3/api-docs",
            "/api/v1/v3/api-docs/**",
            "/api/v1/swagger-resources",
    };

    // Bean to handle PublicKey loading and JwtTokenVerifier
    @Bean
    public JwtTokenVerifier jwtTokenVerifier() throws Exception {
        return new JwtTokenVerifier(KeyUtil.getPublicKey("public_key.pem"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtTokenVerifier jwtTokenVerifier) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(WHITE_LIST_URLS)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenVerifier), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
