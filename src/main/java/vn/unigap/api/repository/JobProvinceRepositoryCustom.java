package vn.unigap.api.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.unigap.api.dto.out.JobProvinceDtoOut;
import vn.unigap.api.entity.JobProvince;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobProvinceRepositoryCustom {
    JobProvinceRepository jobProvinceRepository;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<JobProvinceDtoOut> getProvinceByIds(String in) {
        List<Integer> ids = Arrays.stream(in.split("-+")).filter(s -> !s.isEmpty()).map(Integer::valueOf).toList();

        if (ids.isEmpty())
            return null;

        String query = "SELECT id, name FROM job_province WHERE id IN (:ids)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("ids", ids);

        return namedParameterJdbcTemplate.query(query, mapSqlParameterSource,
                (rs, rowNum) -> JobProvinceDtoOut.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
    }

    public boolean checkAllIdsExist(String in) {
        List<Integer> ids = Arrays.stream(in.split("-+")).filter(s -> !s.isEmpty()).map(Integer::valueOf).toList();

        if (ids.isEmpty())
            return false;

        return jobProvinceRepository.countByIdIn(ids) == ids.size();
    }

    public boolean existsById(Integer id) {
        return jobProvinceRepository.existsJobProvinceById(id);
    }

    public String getProvinceName(Integer id) {
        JobProvince jobProvince = jobProvinceRepository.getJobProvinceById(id);
        if (jobProvince == null)
            return null;
        return jobProvince.getName();
    }
}
