package com.mudra.fakealbumsapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/*
 * Server is set up with the following
 * 	- All requests are authenticated (they should have a valid token)
 * 	- All requests to /fakealbums/** must have scope of "photolibrary.read"
 * 	- tokens would be of JWT format
 * 	- No HttpSession will be created for a request. 
 * 
 * CHANGE : With Spring Boot 3.0, we no longer need to extend WebSecurityConfigurerAdapter
 */
@Configuration
public class OAuth2SecurityConfig {

	// CHANGE : With Spring Boot 3.0, Create a SecurityFilterChain 
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .cors(Customizer.withDefaults())
          .authorizeHttpRequests(authz -> authz
            .requestMatchers("/jacksonville/**","/newyork/**","/miami/**","/random/**").permitAll()
            //.requestMatchers("/fakealbums/**").hasAuthority("SCOPE_photolibrary.read")
            .requestMatchers("/fakealbums/**").hasAuthority("SCOPE_fakebookapi.read")
            .anyRequest().authenticated())
          .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
          .sessionManagement(sMgmt -> sMgmt.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        return http.build();
	}
}
