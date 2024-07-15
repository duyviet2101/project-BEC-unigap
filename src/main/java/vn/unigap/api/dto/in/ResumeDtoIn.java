package vn.unigap.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResumeDtoIn {
    @NotNull
    BigInteger seekerId;

    @NotEmpty
    String careerObj;

    @NotEmpty
    String title;

    @NotNull
    Integer salary;

    @NotEmpty
    String fieldIds;

    @NotEmpty
    String provinceIds;
}