package vn.unigap.api.service;

import vn.unigap.api.dto.in.JobDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.out.JobDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;

import java.math.BigInteger;

public interface JobService {
    void create(JobDtoIn jobDtoIn);

    void update(BigInteger id, JobDtoIn jobDtoIn);

    JobDtoOut get(BigInteger id);

    PageDtoOut<JobDtoOut> list(PageDtoIn pageDtoIn, BigInteger employerId);

    void delete(BigInteger id);
}