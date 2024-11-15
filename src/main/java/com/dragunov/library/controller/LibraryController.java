package com.dragunov.library.controller;

import com.dragunov.library.exception.SubscriptionNotFoundException;
import com.dragunov.library.model.Subscription;
import com.dragunov.library.service.LibraryServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
@Slf4j
public class LibraryController {

    private final LibraryServiceImpl libraryService;

    @GetMapping("/subscription/{user-full-name}")
    public ResponseEntity<Subscription> getSubscription(@PathVariable("user-full-name") String userName) throws SubscriptionNotFoundException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(libraryService.getSubscriptionByUserFullName(userName));
    }

    @PostMapping("/subscription")
    public ResponseEntity<String> loadSubscription(@RequestBody String json) {
        List<Subscription> subscriptions = libraryService.loadSubscription(json);
        log.info("Data from controller: {}", subscriptions);
        libraryService.subscriptionSave(subscriptions, 30);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Subscriptions loaded");
    }
}
