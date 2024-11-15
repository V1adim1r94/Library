package com.dragunov.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionDto {

    @JsonProperty("username")
    private String username;

    @JsonProperty("userFullName")
    private String userFullName;

    @JsonProperty("userActive")
    private Boolean userActive;

    @JsonProperty("books")
    private List<BookDto> books;

}
