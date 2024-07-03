package vn.unigap.api.dto.in;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageDtoIn {
    @NotNull
    @Min(value = 1)
    Integer page = 1;

    @NotNull
    @Min(value = 1)
    @Max(value = 500)
    Integer pageSize = 10;
}
