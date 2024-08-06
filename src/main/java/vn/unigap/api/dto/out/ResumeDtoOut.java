package vn.unigap.api.dto.out;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResumeDtoOut {
    BigInteger id;
    BigInteger seekerId;
    String seekerName;
    String careerObj;
    String title;
    Integer salary;
    List<JobFieldDtoOut> fields;
    List<JobProvinceDtoOut> provinces;
}
