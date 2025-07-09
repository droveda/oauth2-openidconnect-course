package com.droveda.bugtrackercli.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

@Component
public class BugStatisticsScheduler {

    private static final Logger log = LoggerFactory.getLogger(BugStatisticsScheduler.class);

    private final IBugTrackerService service;

    private final ClientRegistrationRepository regRepo;

    private final OAuth2AuthorizedClientService authService;

    public BugStatisticsScheduler(IBugTrackerService service,
                                  ClientRegistrationRepository regRepo,
                                  OAuth2AuthorizedClientService authService) {
        this.service = service;
        this.regRepo = regRepo;
        this.authService = authService;
    }

    @Scheduled(fixedDelay = 5000)
    public void dumpStatistics() {

        log.info("firing dumpStatistics()");

        //build an oauth2 request for the provider
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("bugstat")
                .principal("bugtracker-stats")
                .build();

        var cliMgr = authorizedClientServiceAndManager(regRepo, authService);

        //perform the actual authorization request using the authorized client service and authorized client manager
        //This is where the JWT is retrieved from keycloak
        OAuth2AuthorizedClient authorizedClient = cliMgr.authorize(authorizeRequest);

        //get the token from the authorized client object
        OAuth2AccessToken token = authorizedClient.getAccessToken();
        log.info("Token = {}", token.getTokenValue());

        BugStatistics statistics = service.getBugStatistics(token.getTokenValue());
        log.info("Open : {}, Closed : {}", statistics.numOpen(), statistics.numClosed());

    }

    private AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }
}
