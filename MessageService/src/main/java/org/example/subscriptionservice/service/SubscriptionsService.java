package org.example.subscriptionservice.service;

import lombok.RequiredArgsConstructor;
import org.example.subscriptionservice.dao.SubscriptionRepository;
import org.example.subscriptionservice.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionsService {
    private final SubscriptionRepository subscriptionRepository;
    private final UMSConnector umsConnector;

    @Value("${ums.paths.user}")
    private String uriUser;

    Map<String, Object> response = new HashMap<>();

    public Mono<ResponseEntity<Map<String, Object>>> getSubscriptionsForSubscriberById(UUID subscriberId) {
        return umsConnector.retrieveUmsData(uriUser + "/" + subscriberId.toString()).flatMap(res -> {
            Subscription subscriptions = new Subscription();
            User user = HttpResponseExtractor.extractDataFromHttpClientResponse(res, User.class);

            if (user.hasRole(Roles.SUBSCRIBER)) {
                subscriptions = subscriptionRepository.getSubscription(subscriberId);
            }
            if (subscriptions.getSubscriber() == null) {
                response.put(Constants.CODE, "404");
                response.put(Constants.MESSAGE,
                        "Subscriptions for user with ID " + subscriberId.toString() + " is not found");
                response.put(Constants.DATA, subscriptions);
            } else {
                response.put(Constants.CODE, "201");
                response.put(Constants.MESSAGE, "Subscriptions have been retrieved");
                response.put(Constants.DATA, subscriptions);
            }
            return Mono.just(ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));
        });
    }

    public Mono<ResponseEntity<Map<String, Object>>> createSubscription(Subscription subscription) {
        return umsConnector.retrieveUmsData(uriUser + "/" + subscription.getSubscriber().toString()).flatMap(res -> {
            boolean subscriptionId = false;
            User user = HttpResponseExtractor.extractDataFromHttpClientResponse(res, User.class);

            if (user.hasRole(Roles.SUBSCRIBER)) {
                subscriptionId = subscriptionRepository.createSubscription(subscription);
            }
            if (!subscriptionId) {
                response.put(Constants.CODE, "500");
                response.put(Constants.MESSAGE, "Subscriptions has not been created");
                response.put(Constants.DATA, false);
            } else {
                response.put(Constants.CODE, "200");
                response.put(Constants.MESSAGE, "Subscription has been created");
                response.put(Constants.DATA, true);
            }
            return Mono.just(ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));
        });
    }

    public Mono<ResponseEntity<Map<String, Object>>> updateSubscriptionForSubscriberById(Subscription subscription) {
        return umsConnector.retrieveUmsData(uriUser + "/" + subscription.getSubscriber().toString()).flatMap(res -> {
            boolean subscriptionId = false;
            User user = HttpResponseExtractor.extractDataFromHttpClientResponse(res, User.class);

            if (user.hasRole(Roles.SUBSCRIBER)) {
                subscriptionId = subscriptionRepository.updateSubscription(subscription);
            }
            if (!subscriptionId) {
                response.put(Constants.CODE, "500");
                response.put(Constants.MESSAGE, "Subscription has not been updated");
                response.put(Constants.DATA, false);
            } else {
                response.put(Constants.CODE, "201");
                response.put(Constants.MESSAGE, "Subscription has been updated");
                response.put(Constants.DATA, true);
            }
            return Mono.just(ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));
        });
    }

    public Mono<ResponseEntity<Map<String, Object>>> deleteSubscriptionForSubscriberById(UUID subscriberId) {
        return umsConnector.retrieveUmsData(uriUser + "/" + subscriberId.toString()).flatMap(res -> {
            boolean subscriptionId = false;
            User user = HttpResponseExtractor.extractDataFromHttpClientResponse(res, User.class);

            if (user.hasRole(Roles.SUBSCRIBER)) {
                subscriptionId = subscriptionRepository.deleteSubscription(subscriberId);
            }
            if (!subscriptionId) {
                response.put(Constants.CODE, "500");
                response.put(Constants.MESSAGE, "Subscription has not been deleted");
                response.put(Constants.DATA, false);
            } else {
                response.put(Constants.CODE, "201");
                response.put(Constants.MESSAGE, "Subscription has been deleted");
                response.put(Constants.DATA, true);
            }
            return Mono.just(ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));
        });
    }
}
