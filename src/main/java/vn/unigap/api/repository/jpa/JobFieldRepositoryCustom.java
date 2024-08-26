package vn.unigap.api.repository.jpa;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.unigap.api.dto.out.JobFieldDtoOut;

import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobFieldRepositoryCustom {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    JobFieldRepository jobFieldRepository;

    public List<JobFieldDtoOut> getFieldsNameByIds(String in) {
        List<Integer> ids = Arrays.stream(in.split("-+")).filter(s -> !s.isEmpty()).map(Integer::valueOf).toList();

        if (ids.isEmpty())
            return null;

        String query = "SELECT id, name FROM job_field WHERE id IN (:ids)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("ids", ids);

        return namedParameterJdbcTemplate.query(query, mapSqlParameterSource,
                (rs, rowNum) -> JobFieldDtoOut.builder().id(rs.getInt("id")).name(rs.getString("name")).build());

    }

    public boolean checkAllIdsExist(String in) {
        List<Integer> ids = Arrays.stream(in.split("-+")).filter(s -> !s.isEmpty()).map(Integer::valueOf).toList();

        if (ids.isEmpty())
            return false;

        return jobFieldRepository.countByIdIn(ids) == ids.size();
    }

    public boolean existsById(Integer id) {
        return jobFieldRepository.existsById(id);
    }
}