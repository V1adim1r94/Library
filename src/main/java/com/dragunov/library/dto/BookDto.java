package com.dragunov.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BookDto {

    @JsonProperty("bookName")
    private String bookName;

    @JsonProperty("bookAuthor")
    private String bookAuthor;

    @JsonProperty("publication")
    private LocalDate publication;

}
