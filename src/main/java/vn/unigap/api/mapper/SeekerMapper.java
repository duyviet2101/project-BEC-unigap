package vn.unigap.api.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.unigap.api.dto.in.SeekerDtoIn;
import vn.unigap.api.dto.out.SeekerDtoOut;
import vn.unigap.api.entity.Seeker;

@Mapper(componentModel = "spring")
public interface SeekerMapper {
    @Mapping(source = "seekerDtoIn.provinceId", target = "seeker.province")
    void updateSeeker(@MappingTarget Seeker seeker, SeekerDtoIn seekerDtoIn);

    @Mapping(source = "seeker.province", target = "provinceId")
    SeekerDtoOut toSeekerDtoOut(Seeker seeker, String provinceName);
}
