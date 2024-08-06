package vn.unigap.api.service;

import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.SeekerDtoIn;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.SeekerDtoOut;

import java.math.BigInteger;

public interface SeekerService {
    void create(SeekerDtoIn seekerDtoIn);

    void update(BigInteger id, SeekerDtoIn seekerDtoIn);

    SeekerDtoOut get(BigInteger id);

    PageDtoOut<SeekerDtoOut> list(PageDtoIn pageDtoIn, Integer provinceId);

    void delete(BigInteger id);
}
