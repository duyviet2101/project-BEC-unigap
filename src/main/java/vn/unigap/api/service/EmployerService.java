package vn.unigap.api.service;

import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.UpdateEmployerDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;

import java.math.BigInteger;

public interface EmployerService {
    PageDtoOut<EmployerDtoOut> list(PageDtoIn pageDtoIn);

    EmployerDtoOut get(BigInteger id);

    void create(EmployerDtoIn employerDtoIn);

    void update(BigInteger id, UpdateEmployerDtoIn updateEmployerDtoIn);

    void delete(BigInteger id);

    String getEmployerNameById(BigInteger id);
}
