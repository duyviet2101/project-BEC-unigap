package vn.unigap.api.dto.out;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalyticsJobRecommendationDtoOut {
    BigInteger id;
    String title;
    Integer quantity;
    List<JobFieldDtoOut> fields;
    List<JobProvinceDtoOut> provinces;
    Integer salary;
    Date expiredAt;
    BigInteger employerId;
    String employerName;
    List<SeekerDtoOut> seekers;
}
