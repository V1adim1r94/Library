package com.dragunov.library.service.inerface;

import com.dragunov.library.dto.BookDto;
import com.dragunov.library.dto.SubscriptionDto;
import com.dragunov.library.model.Book;
import com.dragunov.library.model.Subscription;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ConvertService {

    List<Subscription> convertData(String json) throws JsonProcessingException;

    List<Subscription> getSubscriptionsList(List<SubscriptionDto> resources);

    List<Book> convertBookDtoToEntity(List<BookDto> books);

}
