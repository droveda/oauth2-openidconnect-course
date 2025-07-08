package org.jaubs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@Primary
public class JaubsBookRemoteService implements IJaubsBookService {

    private final Logger log = LoggerFactory.getLogger(JaubsBookRemoteService.class);

    @Value("${jaubs.api.url}")
    private String API_URL;

    private final OAuth2AuthorizedClientService azdCliService;
    private final RestClient restClient;

    public JaubsBookRemoteService(OAuth2AuthorizedClientService azdCliService) {
        this.azdCliService = azdCliService;
        this.restClient = RestClient.create();
    }

    @Override
    public void createBookItem(BookItem item) {
        String token = getAccessToken();

        restClient
                .post()
                .uri(API_URL + "/admin/book")
                .header("Authorization", "bearer " + token)
                .body(item)
                .retrieve()
                .body(Void.class);
    }

    @Override
    public void updateBookItem(BookItem item) {
        restClient
                .put()
                .uri(API_URL + "/admin/book")
                .header("Authorization", "bearer " + getAccessToken())
                .body(item)
                .retrieve()
                .body(Void.class);
    }

    @Override
    public BookItem getItem(long id) {
        return restClient
                .get()
                .uri(API_URL + "/book/" + id)
                .header("Authorization", "bearer " + getAccessToken())
                .retrieve()
                .body(BookItem.class);
    }

    @Override
    public void deleteItem(long id) {
        restClient
                .delete()
                .uri(API_URL + "/admin/book/" + id)
                .header("Authorization", "bearer " + getAccessToken())
                .retrieve()
                .body(Void.class);
    }

    @Override
    public void buyItem(long id) {
        restClient
                .get()
                .uri(API_URL + "/buy/" + id)
                .header("Authorization", "bearer " + getAccessToken())
                .retrieve()
                .body(Void.class);
    }

    @Override
    public List<BookItem> findAllOpenItems() {
        return restClient
                .get()
                .uri(API_URL + "/open-items")
                .header("Authorization", "bearer " + getAccessToken())
                .retrieve()
                .body(new ParameterizedTypeReference<List<BookItem>>() {
                });
    }

    @Override
    public List<SoldItem> findSoldItems(String user) {
        return restClient
                .get()
                .uri(API_URL + "/sold-items/" + user)
                .header("Authorization", "bearer " + getAccessToken())
                .retrieve()
                .body(new ParameterizedTypeReference<List<SoldItem>>() {
                });
    }

    private String getAccessToken() {
        var authn = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String authzId = authn.getAuthorizedClientRegistrationId();
        String name = authn.getName();

        OAuth2AuthorizedClient authorizedClient = azdCliService.loadAuthorizedClient(authzId, name);
        OAuth2AccessToken token = authorizedClient.getAccessToken();

        String tokenValue = token.getTokenValue();
        log.info("Access token for {} : {}", name, tokenValue);

        return tokenValue;
    }
}
