package com.dyns.api_fitness_buddy.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private boolean isSuccess;
    private T data;
    private String errorMessage;

    public ApiResponse(boolean isSuccess, T data, String errorMessage) {
        this.isSuccess = isSuccess;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public ApiResponse(T data) {
        this(true, data, null);
    }

    public ApiResponse(String errorMessage) {
        this(false, null, errorMessage);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> failure(String errorMessage) {
        return new ApiResponse<>(errorMessage);
    }
}
