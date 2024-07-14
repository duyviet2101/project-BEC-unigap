package vn.unigap.api.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.JobProvince;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobProvinceRepositoryCustom {
    JobProvinceRepository jobProvinceRepository;

    public boolean checkAllIdsExist(String in) {
        List<Integer> ids = Arrays.stream(in.split("-+"))
                .filter(s -> !s.isEmpty())
                .map(Integer::valueOf)
                .toList();

        return jobProvinceRepository.countByIdIn(ids) == ids.size();
    }

    public boolean existsById(Integer id) {
        return jobProvinceRepository.existsJobProvinceById(id);
    }

    public String getProvinceName(Integer id) {
        JobProvince jobProvince = jobProvinceRepository.getJobProvinceById(id);
        if (jobProvince == null) return null;
        return jobProvince.getName();
    }
}
