package vn.unigap.api.dto.out;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElementChartDtoOut {
    String date;
    Integer numEmployer = 0;
    Integer numJob = 0;
    Integer numSeeker = 0;
    Integer numResume = 0;
}