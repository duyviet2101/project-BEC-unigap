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

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobRepositoryCustom {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    JobFieldRepositoryCustom jobFieldRepositoryCustom;
    JobProvinceRepositoryCustom jobProvinceRepositoryCustom;

    public Page<JobDtoOut> getJobsWithEmployerNamePaginated(BigInteger employerId, Pageable pageable) {
        // Start building the query
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM jobs A LEFT JOIN employer B ON A.EMPLOYER_ID = B.ID");
        if (employerId != null) {
            queryBuilder.append(" WHERE A.EMPLOYER_ID = :employerId");
        }

        // Dynamically build the ORDER BY clause based on Pageable's sort
        if (pageable.getSort().isSorted()) {
            String orderBy = pageable.getSort().stream()
                    .map(order -> order.getProperty() + " " + order.getDirection())
                    .collect(Collectors.joining(", "));
            queryBuilder.append(" ORDER BY ").append(orderBy);
        } else {
            // Default sort condition if no sort is specified
            queryBuilder.append(" ORDER BY expired_at DESC, B.name ASC");
        }

        // Append LIMIT and OFFSET
        queryBuilder.append(" LIMIT :limit OFFSET :offset");

        // Prepare parameters
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("employerId", employerId)
                .addValue("limit", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        // Execute the query
        List<JobDtoOut> jobs = namedParameterJdbcTemplate.query(queryBuilder.toString(), namedParameters, (rs, rowNum) -> JobDtoOut.builder()
                .employerName(rs.getString("name"))
                .title(rs.getString("title"))
                .quantity(rs.getInt("quantity"))
                .salary(rs.getInt("salary"))
                .expiredAt(rs.getTimestamp("expired_at"))
                .employerId(rs.getBigDecimal("employer_id").toBigInteger())
                .id(rs.getBigDecimal("A.id").toBigInteger())
                .build());

        // Count query remains the same
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
                .fields(jobFieldRepositoryCustom.getFieldsNameByIds(rs.getString("fields")))
                .provinces(jobProvinceRepositoryCustom.getProvinceByIds(rs.getString("provinces")))
                .build());

        return jobList.isEmpty() ? null : jobList.getFirst();
    }
}