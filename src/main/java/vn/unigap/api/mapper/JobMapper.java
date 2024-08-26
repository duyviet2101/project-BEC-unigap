package vn.unigap.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.unigap.api.dto.in.JobDtoIn;
import vn.unigap.api.entity.jpa.Job;

@Mapper(componentModel = "spring")
public interface JobMapper {
    void updateJob(@MappingTarget Job job, JobDtoIn jobDtoIn);
}
