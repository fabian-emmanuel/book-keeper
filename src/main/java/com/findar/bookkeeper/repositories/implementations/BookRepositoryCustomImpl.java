package com.findar.bookkeeper.repositories.implementations;

import com.findar.bookkeeper.constants.Schema;
import com.findar.bookkeeper.dtos.BookResponseDTO;
import com.findar.bookkeeper.dtos.FilterParam;
import com.findar.bookkeeper.models.Book;
import com.findar.bookkeeper.repositories.BookRepositoryCustom;
import com.findar.bookkeeper.mappers.BookMapper;
import com.findar.bookkeeper.utils.PageUtil;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final R2dbcEntityOperations r2dbcEntityOperations;

    @Override
    public Mono<Map<String, Object>> retrieveAllBooks(FilterParam filterParam) {

        List<Criteria> criteriaList = new ArrayList<>();

        if (!StringUtil.isNullOrEmpty(filterParam.status())) {
            criteriaList.add(Criteria.where(Schema.COLUMN_STATUS).is(filterParam.status()));
        }

        Pageable pageRequest = PageUtil.buildPageRequest(filterParam.page(), filterParam.size());

        Query query = Query.query(Criteria.from(criteriaList))
                .sort(Sort.by(Sort.Order.asc(Schema.COLUMN_CREATED_AT)))
                .limit(pageRequest.getPageSize())
                .offset(pageRequest.getOffset());

        return r2dbcEntityOperations.select(query, Book.class)
                .map(BookMapper::toBookResponseDTO)
                .collectList()
                .flatMap(data -> r2dbcEntityOperations.count(Query.query(Criteria.from(criteriaList)), Book.class)
                        .map(total -> {
                            PageImpl<BookResponseDTO> bookResponseDTOS = new PageImpl<>(data, pageRequest, total);
                            return PageUtil.buildPaginatedResponse(bookResponseDTOS, pageRequest, total, "books");
                        }));
    }
}
