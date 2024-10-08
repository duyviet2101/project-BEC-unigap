package vn.unigap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.UpdateEmployerDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.entity.jpa.Employer;
import vn.unigap.api.mapper.EmployerMapper;
import vn.unigap.api.repository.jpa.EmployerRepository;
import vn.unigap.api.repository.jpa.EmployerRepositoryCustom;
import vn.unigap.common.errorcode.ErrorCode;
import vn.unigap.common.exception.ApiException;

import java.math.BigInteger;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployerServiceImpl implements EmployerService {
    private static final Logger log = LoggerFactory.getLogger(EmployerServiceImpl.class);
    EmployerRepository employerRepository;
    EmployerMapper employerMapper;
    EmployerRepositoryCustom employerRepositoryCustom;

    @Override
    public PageDtoOut<EmployerDtoOut> list(PageDtoIn pageDtoIn) {
        return employerRepositoryCustom.getEmployersPaginated(
                PageRequest.of(pageDtoIn.getPage() - 1, pageDtoIn.getPageSize(), Sort.by("id").ascending()));
    }

    @Override
    public void create(EmployerDtoIn employerDtoIn) {
        employerRepository.findByEmail(employerDtoIn.getEmail()).ifPresent(employer -> {
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Email already existed!");
        });

        employerRepository.save(Employer.builder().email(employerDtoIn.getEmail()).name(employerDtoIn.getName())
                .province(employerDtoIn.getProvince()).description(employerDtoIn.getDescription()).build());
    }

    @Override
    public EmployerDtoOut get(BigInteger id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer not found!"));

        return EmployerDtoOut.from(employer);
    }

    @Override
    public void update(BigInteger id, UpdateEmployerDtoIn updateEmployerDtoIn) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer not found"));

        employerMapper.updateEmployer(employer, updateEmployerDtoIn);
        employer.setUpdatedAt(new Date());

        employerRepository.save(employer);
    }

    @Override
    public void delete(BigInteger id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer not found"));

        employerRepository.delete(employer);
    }
}