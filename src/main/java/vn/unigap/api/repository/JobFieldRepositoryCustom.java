package vn.unigap.api.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobFieldRepositoryCustom {
    JobFieldRepository jobFieldRepository;

    public boolean checkAllIdsExist(String in) {
        List<Integer> ids = Arrays.stream(in.split("-+"))
                .filter(s -> !s.isEmpty())
                .map(Integer::valueOf)
                .toList();

        return jobFieldRepository.countByIdIn(ids) == ids.size();
    }

    public boolean existsById(Integer id) {
        return jobFieldRepository.existsById(id);
    }
}