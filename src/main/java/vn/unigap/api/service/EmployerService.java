package vn.unigap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.EmployerCreationRequest;
import vn.unigap.api.dto.in.EmployerUpdateRequest;
import vn.unigap.api.dto.out.ApiResponse;
import vn.unigap.api.dto.out.EmployerResponse;
import vn.unigap.api.dto.out.PagingResponse;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.exception.AppException;
import vn.unigap.api.exception.ErrorCode;
import vn.unigap.api.mapper.EmployerMapper;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployerService {
    ArrayList<Employer> list = new ArrayList<>();
    EmployerMapper employerMapper;

    public EmployerResponse createEmployer(EmployerCreationRequest request) {
        Employer newEmployer = employerMapper.toEmployer(request);
        newEmployer.setCreatedAt(LocalDateTime.now());
        newEmployer.setUpdatedAt(LocalDateTime.now());

        Optional<Employer> result = list.stream()
                .filter(employer -> employer.getEmail().equals(request.getEmail()))
                .findFirst();

        if (result.isPresent()) {
            throw new AppException(ErrorCode.EMPLOYER_EXISTED);
        }

        list.add(newEmployer);
        return employerMapper.toEmployerResponse(newEmployer);
    }

    public EmployerResponse updateEmployer(BigInteger employerId, EmployerUpdateRequest request) {
        Optional<Employer> result = list.stream()
                .filter(employer -> employer.getId().equals(employerId))
                .findFirst();
        if (result.isEmpty()) {
            throw new AppException(ErrorCode.EMPLOYER_NOT_EXISTED);
        }
        Employer employer = result.get();

        employerMapper.updateEmployer(employer, request);

        return employerMapper.toEmployerResponse(employer);
    }

    public EmployerResponse getEmployer(BigInteger employerId) {
        Optional<Employer> result = list.stream()
                .filter(employer -> employer.getId().equals(employerId))
                .findFirst();
        if (result.isEmpty()) {
            throw new AppException(ErrorCode.EMPLOYER_NOT_EXISTED);
        }
        return employerMapper.toEmployerResponse(result.get());
    }

    public PagingResponse<EmployerResponse> getEmployers(Integer page, Integer pageSize) {
        ArrayList<EmployerResponse> res = new ArrayList<>();
        list.forEach(item -> res.add(employerMapper.toEmployerResponse(item)));

        return PagingResponse.<EmployerResponse>builder()
                .data(res)
                .totalElements((long) res.size())
                .totalPages((long) res.size() / pageSize)
                .page(1)
                .pageSize(10)
                .build();
    }

    public void deleteEmployer(BigInteger employerId) {
        list.removeIf(employer -> employer.getId().equals(employerId));
    }
}