package vn.unigap.common.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.unigap.common.errorcode.ErrorCode;
import vn.unigap.common.response.ApiResponse;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        return responseEntity(e.getErrorCode(), e.getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleUnknownException(Exception e) {
        e.printStackTrace();
        return responseEntity(ErrorCode.INTERNAL_ERR, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    private ResponseEntity<Object> responseEntity(Integer errorCode, HttpStatusCode statusCode, String msg) {
        return new ResponseEntity<>(
                ApiResponse.builder().errorCode(errorCode).statusCode(statusCode.value()).message(msg).build(),
                statusCode);
    }
}