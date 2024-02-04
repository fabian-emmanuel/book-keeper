package com.findar.bookkeeper.utils;

import com.findar.bookkeeper.dtos.BookRequestDTO;
import com.findar.bookkeeper.enums.Status;
import com.findar.bookkeeper.mappers.BookMapper;
import com.findar.bookkeeper.models.Book;

import java.time.LocalDateTime;

import static com.findar.bookkeeper.utils.CustomUtil.FAKER;

public interface TestModels {
    static Book book(BookRequestDTO requestDTO){
        Book book = BookMapper.toBook(requestDTO);
        book.setId(FAKER.random().nextLong());
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        return book;
//        return BookMapper.toBook()

    }

    static Book book(Long id, String title, String author){
        return Book.builder()
                .id(id)
                .title(title)
                .author(author)
                .publisher(FAKER.book().publisher())
                .genre(FAKER.book().genre())
                .isbn(UniqueRefUtil.generateUniqueRef("ISBN", 12))
                .price(FAKER.random().nextLong())
                .status(Status.AVAILABLE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
