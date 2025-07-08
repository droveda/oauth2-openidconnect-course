package com.droveda.bugtrackercli.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

@Component
public class BugStatisticsScheduler {

    private static final Logger log = LoggerFactory.getLogger(BugStatisticsScheduler.class);

    private final IBugTrackerService service;

    private final AuthorizedClientServiceOAuth2AuthorizedClientManager cliMgr;

    public BugStatisticsScheduler(IBugTrackerService service, AuthorizedClientServiceOAuth2AuthorizedClientManager cliMgr) {
        this.service = service;
        this.cliMgr = cliMgr;
    }

    @Scheduled(fixedDelay = 5000)
    public void dumpStatistics() {

        log.info("firing dumpStatistics()");

        //build an oauth2 request for the provider
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("bugstat")
                .principal("bugtracker-stats")
                .build();

        //perform the actual authorization request using the authorized client service and authorized client manager
        //This is where the JWT is retrieved from keycloak
        OAuth2AuthorizedClient authorizedClient = this.cliMgr.authorize(authorizeRequest);

        //get the token from the authorized client object
        OAuth2AccessToken token = authorizedClient.getAccessToken();
        log.info("Token = {}", token.getTokenValue());

        BugStatistics statistics = service.getBugStatistics(token.getTokenValue());
        log.info("Open : {}, Closed : {}", statistics.numOpen(), statistics.numClosed());

    }
}
