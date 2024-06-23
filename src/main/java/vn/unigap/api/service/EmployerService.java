package vn.unigap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.EmployerCreationRequest;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.exception.AppException;
import vn.unigap.api.exception.ErrorCode;
import vn.unigap.api.mapper.EmployerMapper;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployerService {
    ArrayList<Employer> list = new ArrayList<>();
    EmployerMapper employerMapper;

    public Employer createEmployer(EmployerCreationRequest request) {
        Employer newEmployer = employerMapper.toEmployer(request);

        Optional<Employer> result = list.stream()
                .filter(employer -> employer.getEmail().equals(request.getEmail()))
                .findFirst();

        if (result.isPresent()) {
            throw new AppException(ErrorCode.EMPLOYER_EXISTED);
        }

        list.add(newEmployer);
        return newEmployer;
    }
}