package vn.unigap.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import vn.unigap.api.validator.DateFormat;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DateRangeDtoIn {

    @DateFormat
    @NotEmpty
    String fromDate;

    @DateFormat
    @NotEmpty
    String toDate;

}