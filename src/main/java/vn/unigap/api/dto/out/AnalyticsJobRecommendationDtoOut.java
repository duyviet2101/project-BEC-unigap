package vn.unigap.api.dto.out;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalyticsJobRecommendationDtoOut {
    BigInteger id;
    String title;
    Integer quantity;
    JobFieldDtoOut fields;
    JobProvinceDtoOut provinces;
    Integer salary;
    Date expiredAt;
    BigInteger employerId;
    String employerName;
    List<SeekerDtoOut> seekers;
}
