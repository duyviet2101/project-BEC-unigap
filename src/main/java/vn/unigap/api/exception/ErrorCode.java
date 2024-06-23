package vn.unigap.api.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    EMPLOYER_EXISTED(1000, "Employer existed!"),
    UNCATEGORIZED_EXCEPTION(9999, "Unknown error!")
    ;

    int code;
    String message;
}
