package com.findar.bookkeeper.mappers;

import com.findar.bookkeeper.dtos.BookRequestDTO;
import com.findar.bookkeeper.dtos.BookResponseDTO;
import com.findar.bookkeeper.dtos.BookUpdateRequestDTO;
import com.findar.bookkeeper.enums.Status;
import com.findar.bookkeeper.models.Book;
import com.findar.bookkeeper.utils.DateUtil;
import com.findar.bookkeeper.utils.UniqueRefUtil;
import io.netty.util.internal.StringUtil;

import static com.findar.bookkeeper.utils.CustomUtil.FAKER;

public interface BookMapper {
    static Book toBook(BookRequestDTO requestDTO){
        return Book.builder()
                .title(requestDTO.title())
                .author(requestDTO.author())
                .publisher(FAKER.book().publisher())
                .genre(FAKER.book().genre())
                .isbn(UniqueRefUtil.generateUniqueRef("ISBN", 12))
                .status(Status.AVAILABLE)
                .price(FAKER.number().randomDouble(2, 10, 100))
                .build();
    }

    static BookResponseDTO toBookResponseDTO(Book book) {
        return BookResponseDTO.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .genre(book.getGenre())
                .isbn(book.getIsbn())
                .status(book.getStatus())
                .price(book.getPrice())
                .createdBy(book.getCreatedBy())
                .createdAt(DateUtil.formatLocalDateTime(book.getCreatedAt()))
                .build();
    }

    static void updateBook(Book book, BookUpdateRequestDTO requestDTO) {

        if(!StringUtil.isNullOrEmpty(requestDTO.title())){
            book.setTitle(requestDTO.title());
        }

        if (!StringUtil.isNullOrEmpty(requestDTO.author())) {
            book.setAuthor(requestDTO.author());
        }

        if (!StringUtil.isNullOrEmpty(requestDTO.status())) {
            book.setStatus(Status.valueOf(requestDTO.status()));
        }
    }
}
