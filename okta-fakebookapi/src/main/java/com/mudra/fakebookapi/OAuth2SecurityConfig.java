package com.mudra.fakebookapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/*
 * Security settings
 * 	- HTTP GET calls to /books/** requires fakebookapi.read scope
 * 	- HTTP POST call to /books require fakebookapi.admin scope
 * 	- tokens come in as JWT 
 * 	- No sessions created during requests (No JSESSIONID Cookie created)
 * 
 * CHANGE : With Spring Boot 3.0, we no longer need to extend WebSecurityConfigurerAdapter
 */
@Configuration
public class OAuth2SecurityConfig {

	// CHANGE : With Spring Boot 3.0, Create a SecurityFilterChain 
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .authorizeHttpRequests(authz -> authz
            .requestMatchers(HttpMethod.GET, "/books/**").hasAuthority("SCOPE_fakebookapi.read")
            .requestMatchers(HttpMethod.POST, "/books").hasAuthority("SCOPE_fakebookapi.admin")
            .anyRequest().authenticated())
          .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
          .sessionManagement(sMgmt -> sMgmt.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
	}
}
