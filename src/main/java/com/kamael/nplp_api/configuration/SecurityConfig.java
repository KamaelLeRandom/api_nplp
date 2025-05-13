package com.kamael.nplp_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kamael.nplp_api.filter.JwtFilter;
import com.kamael.nplp_api.filter.OriginVerificationFilter;
import com.kamael.nplp_api.service.AuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private AuthService authService;
	private JwtUtils jwtUtils;

    public SecurityConfig(AuthService authService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authentificationManager(HttpSecurity http, PasswordEncoder passwordEncoder) {
		AuthenticationManagerBuilder authentificationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		try {
			authentificationManagerBuilder.userDetailsService(authService).passwordEncoder(passwordEncoder);
			return authentificationManagerBuilder.build();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	        .cors(cors -> {})
	        .csrf(AbstractHttpConfigurer::disable)
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/auth/**", "/verify/**").permitAll()
	            .requestMatchers(HttpMethod.POST, "/result/create").authenticated()
	            .requestMatchers(HttpMethod.GET, "/**").authenticated()
	            .requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
	            .anyRequest().authenticated()
	        )
	        .addFilterBefore(new OriginVerificationFilter(), UsernamePasswordAuthenticationFilter.class) 
	        .addFilterBefore(new JwtFilter(authService, jwtUtils), UsernamePasswordAuthenticationFilter.class)
	        .build();
	}
}
