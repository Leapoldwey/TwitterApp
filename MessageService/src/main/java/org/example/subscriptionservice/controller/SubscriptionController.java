package org.example.subscriptionservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.subscriptionservice.dto.Constants;
import org.example.subscriptionservice.dto.Subscription;
import org.example.subscriptionservice.service.SubscriptionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionsService subscriptionsService;

    Map<String, Object> response = new HashMap<>();

    @RequestMapping(method = RequestMethod.GET, path = Constants.URI_SUBSCRIPTION + "/{subscriber-id}")
    public Mono<ResponseEntity<Map<String, Object>>> getSubscriptionBySubscriberId(
            @PathVariable(value = "subscriber-id", required = true) UUID subscriberId) {
        return subscriptionsService.getSubscriptionsForSubscriberById(subscriberId);
    }

    @RequestMapping(method = RequestMethod.PUT, path = Constants.URI_SUBSCRIPTIONS, consumes = Constants.APPLICATION_JSON)
    public Mono<ResponseEntity<Map<String, Object>>> up(@RequestBody Subscription subscription) {
        return subscriptionsService.updateSubscriptionForSubscriberById(subscription);
    }

    @RequestMapping(method = RequestMethod.POST, path = Constants.URI_SUBSCRIPTIONS, consumes = Constants.APPLICATION_JSON)
    public Mono<ResponseEntity<Map<String, Object>>> createSubscription(@RequestBody Subscription subscription) {
        return subscriptionsService.createSubscription(subscription);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = Constants.URI_SUBSCRIPTION + "/{subscriber-id}")
    public Mono<ResponseEntity<Map<String, Object>>> createSubscription(
            @PathVariable(value = "subscriber-id", required = true) UUID subscriberId) {
        return subscriptionsService.deleteSubscriptionForSubscriberById(subscriberId);
    }
}
