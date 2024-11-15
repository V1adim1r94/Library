package com.dragunov.library.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "idx_userFullName", columnList = "userFullName")})
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 30)
    @Column(unique = true)
    private String username;

    @NotEmpty
    @Size(min = 4, max = 30)
    private String userFullName;

    private Boolean userActive;

    @JsonManagedReference
    @OneToMany(mappedBy = "subscription", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Book> books;

    @Override
    public String toString() {
        return "Subscription {" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", books=" + books.stream()
                .map(Book::getBookName)
                .toList() +
                '}';
    }
}
