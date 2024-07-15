package vn.unigap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.DateRangeDtoIn;
import vn.unigap.api.dto.out.AnalyticsNewsDtoOut;
import vn.unigap.api.repository.AnalyticsServiceRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnalyticsServiceImpl implements AnalyticsService {
    AnalyticsServiceRepository analyticsServiceRepository;

    @Override
    public AnalyticsNewsDtoOut getNews(DateRangeDtoIn dateRangeDtoIn) {
        return analyticsServiceRepository.getNewsByDateRange(dateRangeDtoIn.getFromDate(), dateRangeDtoIn.getToDate());
    }
}
