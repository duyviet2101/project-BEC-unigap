package vn.unigap.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import vn.unigap.common.errorcode.ErrorCode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    Integer errorCode;
    Integer statusCode;
    String message;
    T object;

    public static <T> ApiResponse<T> success(T object) {
        return ApiResponse.<T>builder()
                .errorCode(ErrorCode.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .object(object)
                .build();
    }

    public static <T> ApiResponse<T> error(Integer errorCode, HttpStatus httpStatus, String message) {
        return ApiResponse.<T>builder()
                .errorCode(errorCode)
                .statusCode(httpStatus.value())
                .message(message)
                .build();
    }
}