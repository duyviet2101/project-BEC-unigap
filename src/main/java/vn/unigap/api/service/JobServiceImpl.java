package vn.unigap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.JobDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.out.JobDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.entity.jpa.Job;
import vn.unigap.api.mapper.JobMapper;
import vn.unigap.api.repository.jpa.JobFieldRepositoryCustom;
import vn.unigap.api.repository.jpa.JobProvinceRepositoryCustom;
import vn.unigap.api.repository.jpa.JobRepository;
import vn.unigap.api.repository.jpa.JobRepositoryCustom;
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
    JobRepositoryCustom jobRepositoryCustom;
    JobFieldRepositoryCustom jobFieldRepositoryCustom;
    JobProvinceRepositoryCustom jobProvinceRepositoryCustom;

    @Override
    public void create(JobDtoIn jobDtoIn) {
        if (!jobFieldRepositoryCustom.checkAllIdsExist(jobDtoIn.getFieldIds()))
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Field ids are invalid!");

        if (!jobProvinceRepositoryCustom.checkAllIdsExist(jobDtoIn.getProvinceIds()))
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Province ids are invalid!");

        jobRepository.save(Job.builder().title(jobDtoIn.getTitle()).employerId(jobDtoIn.getEmployerId())
                .quantity(jobDtoIn.getQuantity()).description(jobDtoIn.getDescription()).fields(jobDtoIn.getFieldIds())
                .provinces(jobDtoIn.getProvinceIds()).salary(jobDtoIn.getSalary()).expiredAt(jobDtoIn.getExpiredAt())
                .build());
    }

    @Override
    public void update(BigInteger id, JobDtoIn jobDtoIn) {
        if (!jobFieldRepositoryCustom.checkAllIdsExist(jobDtoIn.getFieldIds()))
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Field ids are invalid!");

        if (!jobProvinceRepositoryCustom.checkAllIdsExist(jobDtoIn.getProvinceIds()))
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Province ids are invalid!");

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Job not found"));

        jobMapper.updateJob(job, jobDtoIn);
        job.setUpdatedAt(new Date());

        jobRepository.save(job);
    }

    @Override
    public JobDtoOut get(BigInteger id) {
        return jobRepositoryCustom.getJobWithEmployerName(id);
    }

    @Override
    public PageDtoOut<JobDtoOut> list(PageDtoIn pageDtoIn, BigInteger employerId) {
        // NamedParameterJdbcTemplate
//        Page<JobDtoOut> result = jobRepositoryCustom.getJobsWithEmployerNamePaginated(employerId,
//                PageRequest.of(pageDtoIn.getPage() - 1, pageDtoIn.getPageSize(), Sort.by(Sort.Order.desc("expired_at"), Sort.Order.asc("name"))));
//
//        return PageDtoOut.from(pageDtoIn.getPage(), pageDtoIn.getPageSize(), result.getTotalElements(), result.getContent());

        return jobRepositoryCustom.getJobsWithEmployerNamePaginated(employerId, PageRequest.of(pageDtoIn.getPage() - 1,
                pageDtoIn.getPageSize(), Sort.by(Sort.Order.desc("expired_at"), Sort.Order.asc("name"))));
    }

    @Override
    public void delete(BigInteger id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Job not found"));

        jobRepository.delete(job);
    }
}