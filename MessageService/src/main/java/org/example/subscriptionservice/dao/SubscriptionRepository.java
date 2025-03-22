package org.example.subscriptionservice.dao;

import org.example.subscriptionservice.dto.Subscription;

import java.util.UUID;

public interface SubscriptionRepository {
    public Subscription getSubscription(UUID subscriberId);
    public boolean createSubscription(Subscription subscription);
    public boolean updateSubscription(Subscription subscription);
    public boolean deleteSubscription(UUID subscriberId);
}