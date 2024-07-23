package vn.unigap.api.dto.out;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalyticsNewsDtoOut implements Serializable {
    Integer numEmployer;
    Integer numJob;
    Integer numSeeker;
    Integer numResume;
    List<ElementChartDtoOut> chart;
}
