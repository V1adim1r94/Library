package com.dragunov.library.service;

import com.dragunov.library.model.Book;
import com.dragunov.library.model.Subscription;
import com.dragunov.library.repository.SubscriptionRepository;
import com.dragunov.library.service.inerface.ScheduledTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTaskServiceImpl implements ScheduledTaskService {

    private final SubscriptionRepository subscriptionRepository;

    private final EmailServiceImpl emailService;

    @Override
    @Scheduled(cron = "${spring.schedule.delay}", zone = "Europe/Moscow")
    public void checkBookRentalData() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        log.info("scheduler start");
        for (Subscription subscription : subscriptions) {
            for (Book book : subscription.getBooks()) {
                if (isExpired(daysBetween(book.getRentalStart(), LocalDate.now()))) {
                    emailService.sendSimpleEmail(subscription.getUsername(), "Your library",
                            book.getBookName() + "this book is overdue");
                }
            }
        }
        log.info("scheduler end");
    }

    private boolean isExpired(long daysBetween) {
        return daysBetween > 20;
    }

    private long daysBetween(LocalDate rentalStart, LocalDate currentDate) {
        return Duration.between(rentalStart.atStartOfDay(), currentDate.atStartOfDay()).toDays();
    }
}
