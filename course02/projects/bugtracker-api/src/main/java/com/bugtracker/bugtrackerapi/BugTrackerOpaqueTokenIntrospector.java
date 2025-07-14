package com.bugtracker.bugtrackerapi;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BugTrackerOpaqueTokenIntrospector extends SpringOpaqueTokenIntrospector {

    public BugTrackerOpaqueTokenIntrospector(String introspectionUrl, String clientId, String clientSecret) {
        super(introspectionUrl, clientId, clientSecret);
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        OAuth2AuthenticatedPrincipal principal = super.introspect(token);
        return new DefaultOAuth2AuthenticatedPrincipal(principal.getAttributes(), extractAuthorities(principal));
    }

    private Collection<GrantedAuthority> extractAuthorities(OAuth2AuthenticatedPrincipal principal) {
        List<String> roles = principal.getAttribute("roles");
        return roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
