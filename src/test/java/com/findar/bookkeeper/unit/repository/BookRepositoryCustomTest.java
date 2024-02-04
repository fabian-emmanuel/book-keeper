package com.findar.bookkeeper.unit.repository;

import com.findar.bookkeeper.dtos.BookResponseDTO;
import com.findar.bookkeeper.dtos.FilterParam;
import com.findar.bookkeeper.mappers.BookMapper;
import com.findar.bookkeeper.models.Book;
import com.findar.bookkeeper.repositories.implementations.BookRepositoryCustomImpl;
import com.findar.bookkeeper.utils.TestModels;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.relational.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(SpringExtension.class)
class BookRepositoryCustomTest {

    @InjectMocks
    BookRepositoryCustomImpl bookRepositoryCustom;

    @Mock
    R2dbcEntityOperations r2dbcEntityOperations;


    @Test
    void shouldRetrieveAllBooksSuccessfully() {
        Book book1 = TestModels.book(1L, "Here And Now", "Anderson May");
        Book book2 = TestModels.book(2L, "Buying Right", "John Doe");
        Book book3 = TestModels.book(3L, "Selling Right", "Sandra Binta");
        Book book4 = TestModels.book(4L, "Running To Finish", "Robert Carlos");
        Book book5 = TestModels.book(5L, "Heal The World", "Michael Jackson");

        BookResponseDTO responseDTO1 = BookMapper.toBookResponseDTO(book1);
        BookResponseDTO responseDTO2 = BookMapper.toBookResponseDTO(book2);
        BookResponseDTO responseDTO3 = BookMapper.toBookResponseDTO(book3);
        BookResponseDTO responseDTO4 = BookMapper.toBookResponseDTO(book4);
        BookResponseDTO responseDTO5 = BookMapper.toBookResponseDTO(book5);

        when(r2dbcEntityOperations.select(any(Query.class), eq(Book.class))).thenReturn(Flux.just(book1, book2, book3, book4, book5));
        when(r2dbcEntityOperations.count(any(Query.class), eq(Book.class))).thenReturn(Mono.just(5L));

        FilterParam filterParam = FilterParam.builder()
                .page(1L)
                .size(10L)
                .build();

        StepVerifier.create(bookRepositoryCustom.retrieveAllBooks(filterParam))
                .expectNextMatches(response -> {
                    List<BookResponseDTO> responseDTOList = (List<BookResponseDTO>) response.get("books");
                    assertEquals(5, responseDTOList.size());
                    assertEquals(responseDTO1, responseDTOList.getFirst());
                    assertEquals(responseDTO2, responseDTOList.get(1));
                    assertEquals(responseDTO3, responseDTOList.get(2));
                    assertEquals(responseDTO4, responseDTOList.get(3));
                    assertEquals(responseDTO5, responseDTOList.getLast());
                    return true;
                })
                .expectComplete()
                .verify();

        verify(r2dbcEntityOperations, times(1)).select(any(Query.class), eq(Book.class));
        verify(r2dbcEntityOperations, times(1)).count(any(Query.class), eq(Book.class));
    }

}
