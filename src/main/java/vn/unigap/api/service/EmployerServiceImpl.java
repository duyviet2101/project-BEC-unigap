package vn.unigap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.UpdateEmployerDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.mapper.EmployerMapper;
import vn.unigap.api.repository.EmployerRepository;
import vn.unigap.common.errorcode.ErrorCode;
import vn.unigap.common.exception.ApiException;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployerServiceImpl implements EmployerService {
    EmployerRepository employerRepository;
    EmployerMapper employerMapper;

    @Override
    public PageDtoOut<EmployerDtoOut> list(PageDtoIn pageDtoIn) {
        Page<Employer> employers = this.employerRepository
                .findAll(PageRequest.of(pageDtoIn.getPage() - 1, pageDtoIn.getPageSize(), Sort.by("id").ascending()));
        return PageDtoOut.from(pageDtoIn.getPage(), pageDtoIn.getPageSize(), employers.getTotalElements(),
                employers.stream().map(EmployerDtoOut::from).toList());
    }

    @Override
    public EmployerDtoOut create(EmployerDtoIn employerDtoIn) {
        employerRepository.findByEmail(employerDtoIn.getEmail()).ifPresent(employer -> {
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Email already existed!");
        });

        Employer employer = employerRepository.save(Employer.builder()
                                .email(employerDtoIn.getEmail())
                                .name(employerDtoIn.getName())
                                .province(employerDtoIn.getProvinceId())
                                .description(employerDtoIn.getDescription())
                                .build());

        return EmployerDtoOut.from(employer);
    }

    @Override
    public EmployerDtoOut get(BigInteger id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "User not found!"));

        return EmployerDtoOut.from(employer);
    }

    @Override
    public EmployerDtoOut update(BigInteger id, UpdateEmployerDtoIn updateEmployerDtoIn) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "User not found"));

        employerMapper.updateEmployer(employer, updateEmployerDtoIn);

        employer = employerRepository.save(employer);

        return EmployerDtoOut.from(employer);
    }

    @Override
    public void delete(BigInteger id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "User not found"));

        employerRepository.delete(employer);
    }
}