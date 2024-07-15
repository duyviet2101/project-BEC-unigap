package vn.unigap.api.dto.out;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalyticsNewsDtoOut {
    Integer numEmployer;
    Integer numJob;
    Integer numSeeker;
    Integer numResume;
    List<ElementChartDtoOut> chart;
}
