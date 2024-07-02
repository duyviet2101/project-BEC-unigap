package vn.unigap.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.unigap.api.dto.in.EmployerCreationRequest;
import vn.unigap.api.dto.in.EmployerUpdateRequest;
import vn.unigap.api.dto.out.EmployerResponse;
import vn.unigap.api.entity.Employer;

@Mapper(componentModel = "spring")
public interface EmployerMapper {
    Employer toEmployer(EmployerCreationRequest request);
    EmployerResponse toEmployerResponse(Employer employer);
    void updateEmployer(@MappingTarget Employer employer, EmployerUpdateRequest request);
}
