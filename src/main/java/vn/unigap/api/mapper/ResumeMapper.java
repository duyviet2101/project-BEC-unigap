package vn.unigap.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.unigap.api.dto.in.ResumeDtoIn;
import vn.unigap.api.entity.Resume;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    @Mapping(source = "resumeDtoIn.fieldIds", target = "resume.fields")
    @Mapping(source = "resumeDtoIn.provinceIds", target = "resume.provinces")
    void updateResume(@MappingTarget Resume resume, ResumeDtoIn resumeDtoIn);
}
