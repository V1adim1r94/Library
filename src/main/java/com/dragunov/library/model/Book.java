package com.dragunov.library.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(indexes = {@Index(name = "idx_bookName", columnList = "bookName")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 30)
    private String bookName;

    @NotEmpty
    @Size(min = 4, max = 30)
    private String bookAuthor;

    @Temporal(TemporalType.DATE)
    private LocalDate publication;

    @Temporal(TemporalType.DATE)
    private LocalDate rentalStart;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;
}
