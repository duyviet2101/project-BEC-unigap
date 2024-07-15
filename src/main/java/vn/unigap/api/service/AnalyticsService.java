package vn.unigap.api.service;

import vn.unigap.api.dto.in.DateRangeDtoIn;
import vn.unigap.api.dto.out.AnalyticsNewsDtoOut;

public interface AnalyticsService {
    AnalyticsNewsDtoOut getNews(DateRangeDtoIn dateRangeDtoIn);
}
