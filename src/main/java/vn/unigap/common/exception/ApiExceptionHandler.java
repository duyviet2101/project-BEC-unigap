package vn.unigap.common.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.unigap.common.errorcode.ErrorCode;
import vn.unigap.common.response.ApiResponse;

import java.util.stream.Collectors;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        return responseEntity(e.getErrorCode(), e.getHttpStatus(), e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String fieldErrors = ex.getFieldErrors().stream()
                .map(fieldError -> String.format("%s:%s", fieldError.getObjectName(), fieldError.getField()))
                .collect(Collectors.joining(","));

        String glObjectErrors = ex.getGlobalErrors().stream().map(ObjectError::getObjectName)
                .collect(Collectors.joining(","));

        String msg = String.format("MethodArgumentNotValid field errors: %s, global errors: %s", fieldErrors,
                glObjectErrors);

        return responseEntity(ErrorCode.BAD_REQUEST, status, msg);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleUnknownException(Exception e) {
        e.printStackTrace();
        return responseEntity(ErrorCode.INTERNAL_ERR, HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    private ResponseEntity<Object> responseEntity(Integer errorCode, HttpStatusCode statusCode, String msg) {
        return new ResponseEntity<>(
                ApiResponse.builder().errorCode(errorCode).statusCode(statusCode.value()).message(msg).build(),
                statusCode);
    }
}