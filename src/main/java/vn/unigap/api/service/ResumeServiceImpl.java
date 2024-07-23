package vn.unigap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.in.ResumeDtoIn;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.ResumeDtoOut;
import vn.unigap.api.entity.Resume;
import vn.unigap.api.entity.Seeker;
import vn.unigap.api.mapper.ResumeMapper;
import vn.unigap.api.repository.*;
import vn.unigap.common.errorcode.ErrorCode;
import vn.unigap.common.exception.ApiException;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResumeServiceImpl implements ResumeService {
    JobFieldRepositoryCustom jobFieldRepositoryCustom;
    JobProvinceRepositoryCustom jobProvinceRepositoryCustom;
    ResumeRepository resumeRepository;
    ResumeRepositoryCustom resumeRepositoryCustom;
    ResumeMapper resumeMapper;
    SeekerRepository seekerRepository;

    @Override
    public void create(ResumeDtoIn resumeDtoIn) {
        if (!jobFieldRepositoryCustom.checkAllIdsExist(resumeDtoIn.getFieldIds()))
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Field ids are invalid!");
        if (!jobProvinceRepositoryCustom.checkAllIdsExist(resumeDtoIn.getProvinceIds()))
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Province ids are invalid!");

        resumeRepository.save(Resume.builder()
                        .seekerId(resumeDtoIn.getSeekerId())
                        .careerObj(resumeDtoIn.getCareerObj())
                        .title(resumeDtoIn.getTitle())
                        .salary(resumeDtoIn.getSalary())
                        .fields(resumeDtoIn.getFieldIds())
                        .provinces(resumeDtoIn.getProvinceIds())
                        .build());
    }

    @Override
    public void update(BigInteger id, ResumeDtoIn resumeDtoIn) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Resume not found!"));

        if (!jobFieldRepositoryCustom.checkAllIdsExist(resumeDtoIn.getFieldIds()))
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Field ids are invalid!");
        if (!jobProvinceRepositoryCustom.checkAllIdsExist(resumeDtoIn.getProvinceIds()))
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Province ids are invalid!");

        resumeMapper.updateResume(resume, resumeDtoIn);

        resumeRepository.save(resume);
    }

    @Override
    public ResumeDtoOut get(BigInteger id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Resume not found!"));

        Seeker seeker = seekerRepository.getSeekerById(resume.getSeekerId());

        return ResumeDtoOut.builder()
                .id(resume.getId())
                .seekerId(resume.getSeekerId())
                .seekerName(seeker == null ? "" : seeker.getName())
                .careerObj(resume.getCareerObj())
                .title(resume.getTitle())
                .salary(resume.getSalary())
                .fields(jobFieldRepositoryCustom.getFieldsNameByIds(resume.getFields()))
                .provinces(jobProvinceRepositoryCustom.getProvinceByIds(resume.getProvinces()))
                .build();
    }

    @Override
    public PageDtoOut<ResumeDtoOut> list(PageDtoIn pageDtoIn, BigInteger seekerId) {
//        if (!seekerId.equals(BigInteger.valueOf(-1)) && !seekerRepository.existsSeekerById(seekerId))
//            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Seeker not found!");

//        Page<ResumeDtoOut> result = resumeRepositoryCustom.getResumesWithSeekerNamePaginated(seekerId,
//                PageRequest.of(pageDtoIn.getPage() - 1, pageDtoIn.getPageSize(), Sort.by("A.title", "B.name")));
//
//        return PageDtoOut.from(pageDtoIn.getPage(), pageDtoIn.getPageSize(), result.getTotalElements(), result.getContent());
        return resumeRepositoryCustom.getResumesWithSeekerNamePaginated(seekerId,
                PageRequest.of(pageDtoIn.getPage() - 1, pageDtoIn.getPageSize(), Sort.by("A.title", "B.name")));
    }

    @Override
    public void delete(BigInteger id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Resume not found!"));

        resumeRepository.delete(resume);
    }
}
