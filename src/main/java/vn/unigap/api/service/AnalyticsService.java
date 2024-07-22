package vn.unigap.api.service;

import vn.unigap.api.dto.in.DateRangeDtoIn;
import vn.unigap.api.dto.out.AnalyticsJobRecommendationDtoOut;
import vn.unigap.api.dto.out.AnalyticsNewsDtoOut;

import java.math.BigInteger;

public interface AnalyticsService {
    AnalyticsNewsDtoOut getNews(DateRangeDtoIn dateRangeDtoIn);

    AnalyticsJobRecommendationDtoOut getRecommendationsForJob(BigInteger id);
}
