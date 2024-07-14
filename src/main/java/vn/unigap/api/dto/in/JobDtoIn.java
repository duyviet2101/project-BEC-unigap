package vn.unigap.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobDtoIn {
    @NotEmpty
    String title;

    @NotNull
    BigInteger employerId;

    @NotNull
    Integer quantity;

    @NotEmpty
    String description;

    @NotNull
    Integer salary;

    @NotEmpty
    String fieldIds;

    @NotEmpty
    String provinceIds;

    Date expiredAt;
}
