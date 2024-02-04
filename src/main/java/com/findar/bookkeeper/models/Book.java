package com.findar.bookkeeper.models;

import com.findar.bookkeeper.constants.Schema;
import com.findar.bookkeeper.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@SuperBuilder
@Getter @Setter
@MappedSuperclass
@NoArgsConstructor @AllArgsConstructor
@Table(name = Schema.TABLE_BOOK)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book extends BaseModel{
    @Column(name = "title")
    String title;
    @Column(name = "author")
    String author;
    @Column(name = "publisher")
    String publisher;
    @Column(name = "isbn")
    String isbn;
    @Column(name = "genre")
    String genre;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status;
    @Column(name = "price")
    double price;
}
