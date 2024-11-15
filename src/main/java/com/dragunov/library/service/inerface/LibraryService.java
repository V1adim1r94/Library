package com.dragunov.library.service.inerface;

import com.dragunov.library.exception.SubscriptionNotFoundException;
import com.dragunov.library.model.Subscription;

import java.util.List;

public interface LibraryService {

    Subscription getSubscriptionByUserFullName(String userFullName) throws SubscriptionNotFoundException;

    List<Subscription> loadSubscription(String json);

    void partSave(List<Subscription> part);

    void subscriptionSave(List<Subscription> subscriptions, int partSize);
}
