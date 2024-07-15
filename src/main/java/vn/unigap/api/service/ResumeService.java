package vn.unigap.api.service;

import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.ResumeDtoIn;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.ResumeDtoOut;

import java.math.BigInteger;

public interface ResumeService {
    void create(ResumeDtoIn resumeDtoIn);
    void update(BigInteger id, ResumeDtoIn resumeDtoIn);
    ResumeDtoOut get(BigInteger id);
    PageDtoOut<ResumeDtoOut> list(PageDtoIn pageDtoIn, BigInteger seekerId);
    void delete(BigInteger id);
}