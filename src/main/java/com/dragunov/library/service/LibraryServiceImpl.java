package com.dragunov.library.service;

import com.dragunov.library.exception.SubscriptionNotFoundException;
import com.dragunov.library.model.Subscription;
import com.dragunov.library.repository.SubscriptionRepository;
import com.dragunov.library.service.inerface.LibraryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryServiceImpl implements LibraryService {

    private final SubscriptionRepository subscriptionRepository;

    private final ConvertServiceImpl convertServiceImpl;

    private final ApplicationContext applicationContext;

    @Override
    public Subscription getSubscriptionByUserFullName(String userFullName) throws SubscriptionNotFoundException {
        return Optional.ofNullable(subscriptionRepository
                        .findByUserFullName(userFullName))
                .orElseThrow(() -> new SubscriptionNotFoundException("subscription for " + userFullName + " not found"));
    }

    @Override
    public List<Subscription> loadSubscription(String json) {
        List<Subscription> subscriptions = new ArrayList<>();
        try {
            subscriptions = convertServiceImpl.convertData(json);
            log.info("convert data success, size = {}", subscriptions.size());
            return subscriptions;
        } catch (JsonProcessingException e) {
            log.error("convert data failed - {}", e.getMessage());
        }
        return subscriptions;
    }

    @Override
    @Transactional
    public void partSave(List<Subscription> part) {
        try {
            subscriptionRepository.saveAll(part);
            log.info("part saved successfully: {}", part);
        } catch (Exception e) {
            log.error("failed to save Error: {}", e.getMessage(), e);
        }
    }

    @Override
    public void subscriptionSave(List<Subscription> subscriptions, int partSize) {
        LibraryServiceImpl instance = applicationContext.getBean(LibraryServiceImpl.class);
        List<Subscription> part = new ArrayList<>(partSize);
        for (Subscription subscription : subscriptions) {
            part.add(subscription);
            if (part.size() == partSize) {
                instance.partSave(part);
                part.clear();
            }
        }
        if (!part.isEmpty()) {
            instance.partSave(part);
        }
    }
}
