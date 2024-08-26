package vn.unigap.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.unigap.api.dto.in.UpdateEmployerDtoIn;
import vn.unigap.api.entity.jpa.Employer;

@Mapper(componentModel = "spring")
public interface EmployerMapper {
    void updateEmployer(@MappingTarget Employer employer, UpdateEmployerDtoIn employerDtoIn);
}
