package com.selected.inventory_dashboard.dtovo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class ResponseWrapper<T> {
    final List<T> responseData;
    final String errorMessage;

    public static <T> ResponseWrapper<T> fromSingleResponseData(T data) {
        return ResponseWrapper.<T>builder().responseData(List.of(data)).build();
    }

    public static <T> ResponseWrapper<T> fromListOfResponseData(List<T> data) {
        return ResponseWrapper.<T>builder().responseData(data).build();
    }
}
