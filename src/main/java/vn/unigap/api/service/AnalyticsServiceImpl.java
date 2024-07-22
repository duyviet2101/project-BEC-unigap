package vn.unigap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.DateRangeDtoIn;
import vn.unigap.api.dto.out.AnalyticsJobRecommendationDtoOut;
import vn.unigap.api.dto.out.AnalyticsNewsDtoOut;
import vn.unigap.api.entity.Job;
import vn.unigap.api.entity.Resume;
import vn.unigap.api.repository.AnalyticsServiceRepository;
import vn.unigap.api.repository.JobRepository;
import vn.unigap.api.repository.ResumeRepository;
import vn.unigap.common.errorcode.ErrorCode;
import vn.unigap.common.exception.ApiException;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnalyticsServiceImpl implements AnalyticsService {
    AnalyticsServiceRepository analyticsServiceRepository;
    ResumeRepository resumeRepository;
    JobRepository jobRepository;

    @Override
    public AnalyticsNewsDtoOut getNews(DateRangeDtoIn dateRangeDtoIn) {
        return analyticsServiceRepository.getNewsByDateRange(dateRangeDtoIn.getFromDate(), dateRangeDtoIn.getToDate());
    }

    @Override
    public AnalyticsJobRecommendationDtoOut getRecommendationsForJob(BigInteger id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Job not found!"));

        List<Resume>

        return null;
    }
}
