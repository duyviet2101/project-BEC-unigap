package vn.unigap.api.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import vn.unigap.api.dto.out.JobDtoOut;
import vn.unigap.api.dto.out.JobFieldDtoOut;
import vn.unigap.api.dto.out.JobProvinceDtoOut;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobRepositoryJdbcTemplate {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    List<JobFieldDtoOut> getFieldsNameByIds(String in) {
        List<Integer> ids = Arrays.stream(in.split("-+"))
                .filter(s -> !s.isEmpty())
                .map(Integer::valueOf)
                .toList();

        String query = "SELECT id, name FROM job_field WHERE id IN (:ids)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("ids", ids);

        return namedParameterJdbcTemplate.query(query, mapSqlParameterSource,
                (rs, rowNum) -> JobFieldDtoOut.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build());

    }

    List<JobProvinceDtoOut> getProvinceByIds(String in) {
        List<Integer> ids = Arrays.stream(in.split("-+"))
                .filter(s -> !s.isEmpty())
                .map(Integer::valueOf)
                .toList();

        String query = "SELECT id, name FROM job_province WHERE id IN (:ids)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("ids", ids);

        return namedParameterJdbcTemplate.query(query, mapSqlParameterSource,
                (rs, rowNum) -> JobProvinceDtoOut.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build());
    }

    public Page<JobDtoOut> getJobsWithEmployerNamePaginated(BigInteger employerId, Pageable pageable) {
        String query = "SELECT * FROM jobs A LEFT JOIN employer B ON A.EMPLOYER_ID = B.ID" + (employerId != null ? " WHERE A.EMPLOYER_ID = :employerId" : "") + " ORDER BY :sort LIMIT :limit OFFSET :offset";
        String sort = String.join(" ", pageable.getSort().toString().split(": "));
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("employerId", employerId)
                .addValue("limit", pageable.getPageSize())
                .addValue("offset", pageable.getOffset())
                .addValue("sort", sort);
        List<JobDtoOut> jobs = namedParameterJdbcTemplate.query(query, namedParameters, (rs, rowNum) -> JobDtoOut.builder()
                .employerName(rs.getString("name"))
                .title(rs.getString("title"))
                .quantity(rs.getInt("quantity"))
                .description(rs.getString("description"))
                .fields(getFieldsNameByIds(rs.getString("fields")))
                .provinces(getProvinceByIds(rs.getString("provinces")))
                .salary(rs.getInt("salary"))
                .expiredAt(rs.getDate("expired_at"))
                .employerId(rs.getBigDecimal("employer_id").toBigInteger())
                .id(rs.getBigDecimal("A.id").toBigInteger())
                .build());

        String countQuery = "SELECT COUNT(*) FROM jobs" + (employerId != null ? " WHERE EMPLOYER_ID = :employerId" : "");
        SqlParameterSource countParameters = new MapSqlParameterSource().addValue("employerId", employerId);
        long total = namedParameterJdbcTemplate.queryForObject(countQuery, countParameters, Long.class);

        return new PageImpl<>(jobs, pageable, total);
    }

    public JobDtoOut getJobWithEmployerName(BigInteger id) {
        String query = "SELECT * FROM jobs A LEFT JOIN employer B ON A.employer_id = B.id WHERE A.id = :id";

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);

        List<JobDtoOut> jobList = namedParameterJdbcTemplate.query(query, namedParameters, (rs, rowNum) -> JobDtoOut.builder()
                .title(rs.getString("title"))
                .quantity(rs.getInt("quantity"))
                .salary(rs.getInt("salary"))
                .expiredAt(rs.getDate("expired_at"))
                .employerId(rs.getBigDecimal("employer_id").toBigInteger())
                .id(rs.getBigDecimal("A.id").toBigInteger())
                .employerName(rs.getString("name"))
                .build());

        return jobList.isEmpty() ? null : jobList.getFirst();
    }
}