package com.dragunov.library.service;

import com.dragunov.library.dto.BookDto;
import com.dragunov.library.dto.Data;
import com.dragunov.library.dto.SubscriptionDto;
import com.dragunov.library.model.Book;
import com.dragunov.library.model.Subscription;
import com.dragunov.library.service.inerface.ConvertService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConvertServiceImpl implements ConvertService {

    private final ObjectMapper objectMapper;

    @Override
    public List<Subscription> convertData(String json) throws JsonProcessingException {
        Data data = objectMapper.readValue(json, Data.class);
        return getSubscriptionsList(data.getData());
    }

    @Override
    public List<Subscription> getSubscriptionsList(List<SubscriptionDto> resources) {
        return resources.stream()
                .map(this::convertDtoSubToSubEntity)
                .toList();
    }

    private Subscription convertDtoSubToSubEntity(SubscriptionDto subDto) {
        Subscription subscription = new Subscription();
        subscription.setUsername(subDto.getUsername());
        subscription.setUserFullName(subDto.getUserFullName());
        subscription.setUserActive(subDto.getUserActive());

        List<Book> books = convertBookDtoToEntity(subDto.getBooks());
        books.forEach(book -> {
            book.setSubscription(subscription);
            if (book.getRentalStart() == null) {
                book.setRentalStart(LocalDate.now());
            }
        });
        subscription.setBooks(books);

        return subscription;
    }

    @Override
    public List<Book> convertBookDtoToEntity(List<BookDto> books) {
        return books.stream()
                .map(this::convertBookDtoToEntity)
                .toList();
    }

    private Book convertBookDtoToEntity(BookDto bookDto) {
        return objectMapper.convertValue(bookDto, Book.class);
    }
}
