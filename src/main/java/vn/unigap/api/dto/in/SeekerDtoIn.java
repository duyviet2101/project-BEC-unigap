package vn.unigap.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.unigap.api.validator.DateFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeekerDtoIn {
    @NotEmpty
    String name;

    @NotEmpty
    @DateFormat
    String birthday;

    String address;

    @NotNull
    Integer provinceId;
}