package vn.unigap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.JobDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.out.JobDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.entity.Job;
import vn.unigap.api.mapper.JobMapper;
import vn.unigap.api.repository.JobFieldRepositoryCustom;
import vn.unigap.api.repository.JobProvinceRepositoryCustom;
import vn.unigap.api.repository.JobRepository;
import vn.unigap.api.repository.JobRepositoryJdbcTemplate;
import vn.unigap.common.errorcode.ErrorCode;
import vn.unigap.common.exception.ApiException;

import java.math.BigInteger;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobServiceImpl implements JobService {
    JobRepository jobRepository;
    JobMapper jobMapper;
    JobRepositoryJdbcTemplate jobRepositoryJdbcTemplate;
    JobFieldRepositoryCustom jobFieldRepositoryCustom;
    JobProvinceRepositoryCustom jobProvinceRepositoryCustom;

    @Override
    public void create(JobDtoIn jobDtoIn) {
        if (!jobFieldRepositoryCustom.checkAllIdsExist(jobDtoIn.getFieldIds()))
            throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Field ids are invalid!");

        if (!jobProvinceRepositoryCustom.checkAllIdsExist(jobDtoIn.getProvinceIds()))
            throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Province ids are invalid!");

        jobRepository.save(Job.builder()
                        .title(jobDtoIn.getTitle())
                        .employerId(jobDtoIn.getEmployerId())
                        .quantity(jobDtoIn.getQuantity())
                        .description(jobDtoIn.getDescription())
                        .fields(jobDtoIn.getFieldIds())
                        .provinces(jobDtoIn.getProvinceIds())
                        .salary(jobDtoIn.getSalary())
                        .expiredAt(jobDtoIn.getExpiredAt())
                    .build());
    }

    @Override
    public void update(BigInteger id, JobDtoIn jobDtoIn) {
        if (!jobFieldRepositoryCustom.checkAllIdsExist(jobDtoIn.getFieldIds()))
            throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Field ids are invalid!");

        if (!jobProvinceRepositoryCustom.checkAllIdsExist(jobDtoIn.getProvinceIds()))
            throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Province ids are invalid!");

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Job not found"));

        jobMapper.updateJob(job, jobDtoIn);
        job.setUpdatedAt(new Date());

        jobRepository.save(job);
    }

    @Override
    public JobDtoOut get(BigInteger id) {
        return jobRepositoryJdbcTemplate.getJobWithEmployerName(id);
    }

    @Override
    public PageDtoOut<JobDtoOut> list(PageDtoIn pageDtoIn, BigInteger employerId) {
        //NamedParameterJdbcTemplate
        Page<JobDtoOut> result = jobRepositoryJdbcTemplate.getJobsWithEmployerNamePaginated(employerId,
                PageRequest.of(pageDtoIn.getPage() - 1, pageDtoIn.getPageSize(), Sort.by("id")));

        return PageDtoOut.from(pageDtoIn.getPage(), pageDtoIn.getPageSize(), result.getTotalElements(), result.getContent());
    }

    @Override
    public void delete(BigInteger id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Job not found"));

        jobRepository.delete(job);
    }
}