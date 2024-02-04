package com.findar.bookkeeper.repositories;

import com.findar.bookkeeper.dtos.FilterParam;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface BookRepositoryCustom {
    Mono<Map<String, Object>> retrieveAllBooks(FilterParam filterParam);
}
