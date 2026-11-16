package com.solance.assessment.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

  private boolean success;
  private T data;
  private ErrorDetail error;

  public static <T> ApiResponse<T> success(T data) {
    return ApiResponse.<T>builder().success(true).data(data).build();
  }

  public static <T> ApiResponse<T> error(int code, String message) {
    return ApiResponse.<T>builder()
        .success(false)
        .error(new ErrorDetail(code, message))
        .build();
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ErrorDetail {
    private int code;
    private String message;
  }
}
