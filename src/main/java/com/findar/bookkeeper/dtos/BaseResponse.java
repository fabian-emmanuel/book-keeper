package com.findar.bookkeeper.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Setter @Getter
public class BaseResponse<T> extends ResponseEntity<ResponseBody<T>> {

    @JsonProperty
    private HttpStatus status;

    @JsonProperty
    private boolean success;

    @JsonProperty
    private String message;

    @JsonProperty
    @Nullable
    T data;

    public BaseResponse(boolean success,HttpStatus status, String message) {
        super(new ResponseBody<>(success, message, null), status);
        this.status = status;
        this.message = message;
    }

    public BaseResponse(boolean success, HttpStatus status, String message, @Nullable T data) {
        super(new ResponseBody<>(success, message, data), status);
        this.status = status;
        this.message = message;
        this.data = data;
    }
}