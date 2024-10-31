package com.selected.inventory_dashboard.dtovo.res;

import lombok.Builder;

import java.util.List;

@Builder
public record ResponseWrapper<T>(List<T> responseData, String errorMessage) {
    public static <T> ResponseWrapper<T> fromSingleResponseData(T data) {
        return ResponseWrapper.<T>builder().responseData(List.of(data)).build();
    }

    public static <T> ResponseWrapper<T> fromListOfResponseData(List<T> data) {
        return ResponseWrapper.<T>builder().responseData(data).build();
    }
}
