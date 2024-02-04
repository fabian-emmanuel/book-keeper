package com.findar.bookkeeper.services;

import com.findar.bookkeeper.dtos.BookRequestDTO;
import com.findar.bookkeeper.dtos.BookResponseDTO;
import com.findar.bookkeeper.dtos.BookUpdateRequestDTO;
import com.findar.bookkeeper.dtos.FilterParam;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface BookService {

    Mono<BookResponseDTO> registerBook(BookRequestDTO bookRequestDTO);

    Mono<BookResponseDTO> retrieveBookById(Long bookId);

    Mono<BookResponseDTO> updateBook(BookUpdateRequestDTO bookUpdateRequestDTO);

    Mono<Map<String, Object>> retrieveAllBooks(FilterParam filterParam);

    Mono<Void> deleteBook(Long bookId);
}
