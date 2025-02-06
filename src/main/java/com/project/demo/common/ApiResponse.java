package com.project.demo.common;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.project.demo.common.constant.CommonConstant.RESULT;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {

    /** 결과: true/false */
    private boolean result;
    /** HTTP 상태 코드 */
    private HttpStatus status;
    /** 결과: 데이터 */
    private T data;
    /** 메시지 */
    private String message;

    /** 기본 생성자 */
    private ApiResponse(boolean result, HttpStatus status, T data, String message) {
        this.result = result;
        this.status = status;
        this.data = data;
        this.message = StringUtils.hasText(message) ? message : getDefaultMessageForStatusCode(status);
    }

    /** 성공/실패 여부에 따라 자동으로 응답을 생성 */
    public static <T> ApiResponse<T> of(boolean result, String message) {
        HttpStatus status = result ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ApiResponse<>(result, status, null, message);
    }

    /** 성공/실패 여부 + 데이터 */
    public static <T> ApiResponse<T> of(boolean result, HttpStatus status, T data, String message) {
        return new ApiResponse<>(result, status, data, message);
    }

    
    /** 성공 응답 (데이터 없이) */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(RESULT.OK, HttpStatus.OK, null, null);
    }

    /** 성공 응답 (메시지 포함) */
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(RESULT.OK, HttpStatus.OK, null, message);
    }

    /** 성공 응답 (데이터 포함) */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(RESULT.OK, HttpStatus.OK, data, null);
    }

    /** 성공 응답 (메시지 + 데이터 포함) */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(RESULT.OK, HttpStatus.OK, data, message);
    }


    /** 에러 응답 (기본 400 BAD_REQUEST) */
    public static <T> ApiResponse<T> error() {
        return new ApiResponse<>(RESULT.ERR, HttpStatus.BAD_REQUEST, null, null);
    }

    /** 에러 응답 (커스텀 메시지) */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(RESULT.ERR, HttpStatus.BAD_REQUEST, null, message);
    }

    /** 에러 응답 (커스텀 상태코드) */
    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return new ApiResponse<>(RESULT.ERR, status, null, message);
    }

    /** 에러 응답 (커스텀 상태코드 + 데이터 포함) */
    public static <T> ApiResponse<T> error(HttpStatus status, T data, String message) {
        return new ApiResponse<>(RESULT.ERR, status, data, message);
    }

    /** HTTP 상태 코드에 따른 기본 메시지 */
    private static String getDefaultMessageForStatusCode(HttpStatus status) {

        switch (status) {
          case OK: // 200
            return "Operation succeeded";
          case CREATED: // 201
            return "Resource created successfully";
          case NO_CONTENT: // 204
            return "Resource deleted successfully";
          case BAD_REQUEST: // 400
            return "Invalid request format";
          case UNAUTHORIZED: // 401
            return "Authentication required";
          case FORBIDDEN: // 403
            return "Access denied";
          case NOT_FOUND: // 404
            return "Resource not found";
          case CONFLICT: // 409
            return "Conflict detected";
          case UNPROCESSABLE_ENTITY: // 422
            return "Unable to process the contained instructions";
          case INTERNAL_SERVER_ERROR: // 500
            return "Internal server error occurred";
          default:
            return "HTTP Status " + status.value();
        }
    }
    
}
