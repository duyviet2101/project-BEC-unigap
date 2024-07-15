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
import vn.unigap.api.dto.out.ResumeDtoOut;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResumeRepositoryCustom {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    JobFieldRepositoryCustom jobFieldRepositoryCustom;
    JobProvinceRepositoryCustom jobProvinceRepositoryCustom;

    public Page<ResumeDtoOut> getResumesWithSeekerNamePaginated(BigInteger seekerId, Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM resume A LEFT JOIN seeker B ON A.seeker_id = B.id");

        String whereClause = "";
        if (!seekerId.equals(BigInteger.valueOf(-1))) {
            whereClause = " WHERE A.seeker_id = :seekerId";
            queryBuilder.append(whereClause);
        }

        if (pageable.getSort().isSorted()) {
            String orderBy = pageable.getSort().stream()
                    .map(order -> order.getProperty() + " " + order.getDirection())
                    .collect(Collectors.joining(", "));
            queryBuilder.append(" ORDER BY ").append(orderBy);
        }

        queryBuilder.append(" LIMIT :limit OFFSET :offset");

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("seekerId", seekerId)
                .addValue("limit", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        List<ResumeDtoOut> resumes = namedParameterJdbcTemplate.query(queryBuilder.toString(), mapSqlParameterSource,
                (rs, rowNum) -> ResumeDtoOut.builder()
                        .id(rs.getBigDecimal("A.id").toBigInteger())
                        .seekerId(rs.getBigDecimal("seeker_id").toBigInteger())
                        .seekerName(rs.getString("B.name"))
                        .careerObj(rs.getString("career_obj"))
                        .title(rs.getString("A.title"))
                        .salary(rs.getInt("A.salary"))
                        .fields(jobFieldRepositoryCustom.getFieldsNameByIds(rs.getString("A.fields")))
                        .provinces(jobProvinceRepositoryCustom.getProvinceByIds(rs.getString("A.provinces")))
                        .build());

        String countQuery = "SELECT COUNT(*) FROM resume A" + whereClause;
        SqlParameterSource countParameters = new MapSqlParameterSource().addValue("seekerId", seekerId);
        long total = namedParameterJdbcTemplate.queryForObject(countQuery, countParameters, Long.class);

        return new PageImpl<>(resumes, pageable, total);
    }
}