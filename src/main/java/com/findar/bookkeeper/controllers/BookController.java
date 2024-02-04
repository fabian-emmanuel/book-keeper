package com.findar.bookkeeper.controllers;


import com.findar.bookkeeper.constants.Message;
import com.findar.bookkeeper.dtos.*;
import com.findar.bookkeeper.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<BaseResponse<BookResponseDTO>> registerBook(@Valid @RequestBody BookRequestDTO bookRequestDTO) {

        return bookService.registerBook(bookRequestDTO)
                .map(bookResponse -> new BaseResponse<>(true, HttpStatus.CREATED, Message.BOOK_REGISTERED_SUCCESSFULLY, bookResponse));
    }

    @GetMapping("/{bookId}")
    public Mono<BaseResponse<BookResponseDTO>> retrieveBookById(@PathVariable Long bookId){

        return bookService.retrieveBookById(bookId)
                .map(bookResponse -> new BaseResponse<>(true, HttpStatus.OK, Message.BOOK_RETRIEVED_SUCCESSFULLY, bookResponse));
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<BaseResponse<BookResponseDTO>> updateBookById(@Valid @RequestBody BookUpdateRequestDTO bookUpdateRequestDTO) {

        return bookService.updateBook(bookUpdateRequestDTO)
                .map(bookResponse -> new BaseResponse<>(true, HttpStatus.OK, Message.BOOK_UPDATED_SUCCESSFULLY, bookResponse));
    }

    @GetMapping
    public Mono<BaseResponse<Object>> retrieveAllBooks(@Valid FilterParam filterParam){

        return bookService.retrieveAllBooks(filterParam)
                .map(allBooksResponse -> new BaseResponse<>(true, HttpStatus.OK, Message.BOOK_RETRIEVED_SUCCESSFULLY, allBooksResponse));
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<BaseResponse<Object>> deleteBookById(@PathVariable Long bookId){

        return bookService.deleteBook(bookId)
                .thenReturn(new BaseResponse<>(true, HttpStatus.OK, Message.BOOK_DELETED_SUCCESSFULLY));
    }
}
