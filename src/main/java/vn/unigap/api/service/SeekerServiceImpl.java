package vn.unigap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.SeekerDtoIn;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.SeekerDtoOut;
import vn.unigap.api.entity.jpa.Seeker;
import vn.unigap.api.mapper.SeekerMapper;
import vn.unigap.api.repository.jpa.JobProvinceRepositoryCustom;
import vn.unigap.api.repository.jpa.SeekerRepository;
import vn.unigap.api.repository.jpa.SeekerRepositoryCustom;
import vn.unigap.common.errorcode.ErrorCode;
import vn.unigap.common.exception.ApiException;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SeekerServiceImpl implements SeekerService {
    SeekerRepository seekerRepository;
    SeekerRepositoryCustom seekerRepositoryCustom;
    JobProvinceRepositoryCustom jobProvinceRepositoryCustom;
    SeekerMapper seekerMapper;

    @Override
    public void create(SeekerDtoIn seekerDtoIn) {
        if (!jobProvinceRepositoryCustom.existsById(seekerDtoIn.getProvinceId()))
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Field ids are invalid!");

        seekerRepository.save(Seeker.builder().name(seekerDtoIn.getName()).birthday(seekerDtoIn.getBirthday())
                .address(seekerDtoIn.getAddress()).province(seekerDtoIn.getProvinceId()).build());
    }

    @Override
    public void update(BigInteger id, SeekerDtoIn seekerDtoIn) {
        if (!jobProvinceRepositoryCustom.existsById(seekerDtoIn.getProvinceId()))
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Province id are invalid!");

        Seeker seeker = seekerRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Seeker not found!"));

        seekerMapper.updateSeeker(seeker, seekerDtoIn);

        seekerRepository.save(seeker);
    }

    @Override
    public SeekerDtoOut get(BigInteger id) {
        Seeker seeker = seekerRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Seeker not found!"));

        return seekerMapper.toSeekerDtoOut(seeker, jobProvinceRepositoryCustom.getProvinceName(seeker.getProvince()));
    }

    @Override
    public PageDtoOut<SeekerDtoOut> list(PageDtoIn pageDtoIn, Integer provinceId) {
        if (provinceId != -1 && !jobProvinceRepositoryCustom.existsById(provinceId))
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Province id are invalid!");

//        Page<SeekerDtoOut> result = seekerRepositoryCustom.getSeekersWithProvinceNamePaginated(
//                PageRequest.of(pageDtoIn.getPage() - 1, pageDtoIn.getPageSize(), Sort.by("name")),
//                provinceId
//        );
//
//        return PageDtoOut.from(pageDtoIn.getPage(), pageDtoIn.getPageSize(), result.getTotalElements(), result.getContent());

        return seekerRepositoryCustom.getSeekersWithProvinceNamePaginated(
                PageRequest.of(pageDtoIn.getPage() - 1, pageDtoIn.getPageSize(), Sort.by("name").descending()),
                provinceId);
    }

    @Override
    public void delete(BigInteger id) {
        Seeker seeker = seekerRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Seeker not found!"));
        seekerRepository.delete(seeker);
    }
}
