package vn.unigap.api.mapper;

import org.mapstruct.Mapper;
import vn.unigap.api.dto.in.EmployerCreationRequest;
import vn.unigap.api.entity.Employer;

@Mapper(componentModel = "spring")
public interface EmployerMapper {
    Employer toEmployer(EmployerCreationRequest request);
}
