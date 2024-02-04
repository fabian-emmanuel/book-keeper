package com.findar.bookkeeper.unit.service;

import com.findar.bookkeeper.constants.Message;
import com.findar.bookkeeper.dtos.BookRequestDTO;
import com.findar.bookkeeper.dtos.BookUpdateRequestDTO;
import com.findar.bookkeeper.enums.Status;
import com.findar.bookkeeper.exceptions.ResourceNotFoundException;
import com.findar.bookkeeper.models.Book;
import com.findar.bookkeeper.repositories.BookRepository;
import com.findar.bookkeeper.services.implementations.BookServiceImpl;
import com.findar.bookkeeper.utils.DateUtil;
import com.findar.bookkeeper.utils.TestModels;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.findar.bookkeeper.utils.CustomUtil.FAKER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(SpringExtension.class)
class BookServiceTest {

    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Test
    void shouldRegisterBookSuccessfully() {
        BookRequestDTO request = BookRequestDTO.builder()
                .title(FAKER.book().title())
                .author(FAKER.book().author())
                .build();

        Book book = TestModels.book(request);

        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(book));

        StepVerifier.create(bookService.registerBook(request))
                .expectNextMatches(bookResponseDTO -> {
                    assertNotNull(bookResponseDTO.bookId());
                    assertEquals(request.title(), bookResponseDTO.title());
                    assertEquals(request.author(), bookResponseDTO.author());
                    assertNotNull(bookResponseDTO.publisher());
                    assertNotNull(bookResponseDTO.isbn());
                    assertTrue(bookResponseDTO.price() > 0.0);
                    assertNotNull(bookResponseDTO.genre());
                    assertEquals(Status.AVAILABLE, bookResponseDTO.status());
                    assertNotNull(bookResponseDTO.createdAt());
                    return true;
                })
                .expectComplete()
                .verify();

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void shouldRetrieveBookByIdSuccessfully() {

        Book book = TestModels.book(1L, "Heal The World", "Michael Jackson");

        when(bookRepository.findBookById(anyLong())).thenReturn(Mono.just(book));

        StepVerifier.create(bookService.retrieveBookById(book.getId()))
                .expectNextMatches(bookResponseDTO -> {
                    assertEquals(book.getId(), bookResponseDTO.bookId());
                    assertEquals(DateUtil.formatLocalDateTime(book.getCreatedAt()), bookResponseDTO.createdAt());
                    assertEquals(book.getTitle(), bookResponseDTO.title());
                    assertEquals(book.getAuthor(), bookResponseDTO.author());
                    assertEquals(book.getPublisher(), bookResponseDTO.publisher());
                    assertEquals(book.getGenre(), bookResponseDTO.genre());
                    assertEquals(book.getIsbn(), bookResponseDTO.isbn());
                    assertEquals(book.getPrice(), bookResponseDTO.price());
                    return true;
                })
                .expectComplete()
                .verify();

        verify(bookRepository, times(1)).findBookById(anyLong());
    }

    @Test
    void shouldReturnResourceNotFoundExceptionWhenRetrieveBookByIdAndBookDoesNotExist() {
        Long bookId = 1L;

        when(bookRepository.findBookById(anyLong())).thenReturn(Mono.empty());

        StepVerifier.create(bookService.retrieveBookById(bookId))
                .expectErrorSatisfies(response -> assertThat(response)
                        .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage(String.format(Message.BOOK_WITH_ID_NOT_FOUND, bookId)))
                .verify();

        verify(bookRepository, times(1)).findBookById(any());
    }


    @Test
    void shouldUpdateBookSuccessfully() {

        Book book = TestModels.book(1L, "Heal The World", "Michael Jackson");

        BookUpdateRequestDTO request = BookUpdateRequestDTO.builder()
                .bookId(book.getId())
                .title("Healing The Globe")
                .author("Michael Jordan")
                .status(Status.NOT_AVAILABLE.name())
                .build();

        when(bookRepository.findBookById(anyLong())).thenReturn(Mono.just(book));
        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(book));

        StepVerifier.create(bookService.updateBook(request))
                .expectNextMatches(bookResponseDTO -> {
                    assertNotNull(bookResponseDTO.bookId());
                    assertEquals(request.title(), bookResponseDTO.title());
                    assertEquals(request.author(), bookResponseDTO.author());
                    assertEquals(book.getPublisher(), bookResponseDTO.publisher());
                    assertEquals(book.getIsbn(), bookResponseDTO.isbn());
                    assertEquals(book.getPrice(), bookResponseDTO.price());
                    assertEquals(book.getGenre(), bookResponseDTO.genre());
                    assertEquals(Status.NOT_AVAILABLE, bookResponseDTO.status());
                    return true;
                })
                .expectComplete()
                .verify();

        verify(bookRepository, times(1)).findBookById(any());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void shouldReturnResourceNotFoundExceptionWhenUpdateBookAndBookDoesNotExist() {
        Long bookId = 1L;

        BookUpdateRequestDTO request = BookUpdateRequestDTO.builder()
                .bookId(bookId)
                .title("Healing The Globe")
                .author("Michael Jordan")
                .status(Status.NOT_AVAILABLE.name())
                .build();

        when(bookRepository.findBookById(anyLong())).thenReturn(Mono.empty());

        StepVerifier.create(bookService.updateBook(request))
                .expectErrorSatisfies(response -> assertThat(response)
                        .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage(String.format(Message.BOOK_WITH_ID_NOT_FOUND, bookId)))
                .verify();

        verify(bookRepository, times(1)).findBookById(any());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void shouldDeleteBookSuccessfully() {

        Book book = TestModels.book(1L, "Heal The World", "Michael Jackson");

        when(bookRepository.findBookById(anyLong())).thenReturn(Mono.just(book));
        when(bookRepository.delete(any(Book.class))).thenReturn(Mono.empty());

        StepVerifier.create(bookService.deleteBook(book.getId()))
                .expectComplete()
                .verify();

        verify(bookRepository, times(1)).findBookById(any());
        verify(bookRepository, times(1)).delete(any(Book.class));
    }

    @Test
    void shouldReturnResourceNotFoundExceptionWhenDeleteBookAndBookDoesNotExist() {
        Long bookId = 1L;

        when(bookRepository.findBookById(anyLong())).thenReturn(Mono.empty());

        StepVerifier.create(bookService.deleteBook(bookId))
                .expectErrorSatisfies(response -> assertThat(response)
                        .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage(String.format(Message.BOOK_WITH_ID_NOT_FOUND, bookId)))
                .verify();

        verify(bookRepository, times(1)).findBookById(any());
        verify(bookRepository, never()).delete(any(Book.class));
    }
}
