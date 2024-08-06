package vn.unigap.api.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.ResumeDtoOut;
import vn.unigap.api.dto.out.SeekerDtoOut;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResumeRepositoryCustom {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    JobFieldRepositoryCustom jobFieldRepositoryCustom;
    JobProvinceRepositoryCustom jobProvinceRepositoryCustom;

    public List<SeekerDtoOut> getRecommendationsForJob(Integer salary, String fields, String provinces) {
        if (!jobFieldRepositoryCustom.checkAllIdsExist(fields))
            fields = "";
        if (!jobProvinceRepositoryCustom.checkAllIdsExist(provinces))
            provinces = "";

        StringBuilder queryBuilder = new StringBuilder(
                "SELECT A.seeker_id, B.name from resume A left join seeker B on A.seeker_id = B.id");

        StringBuilder whereClause = new StringBuilder(" where A.salary <= :salary");
        if (!fields.isEmpty()) {
            List<String> fieldsQuery = new ArrayList<>();
            Arrays.stream(fields.split("-+")).filter(s -> !s.isEmpty()).forEach(fieldId -> {
                fieldsQuery.add("A.fields LIKE '%-" + fieldId + "-%'");
            });
            whereClause.append(" AND (").append(String.join(" OR ", fieldsQuery)).append(")");
        }

        if (!provinces.isEmpty()) {
            List<String> provincesQuery = new ArrayList<>();
            Arrays.stream(provinces.split("-+")).filter(s -> !s.isEmpty()).forEach(provinceId -> {
                provincesQuery.add("A.provinces LIKE '%-" + provinceId + "-%'");
            });
            whereClause.append(" AND (").append(String.join(" OR ", provincesQuery)).append(")");
        }

        queryBuilder.append(whereClause);
        queryBuilder.append(" group by A.seeker_id, B.name");

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("salary", salary);

        List<SeekerDtoOut> names = namedParameterJdbcTemplate.query(queryBuilder.toString(), mapSqlParameterSource, (rs,
                rowNum) -> SeekerDtoOut.builder().id(rs.getInt("A.seeker_id")).name(rs.getString("B.name")).build());

        return names.stream().filter(s -> !(s.getName() == null)).toList();
    }

    @Cacheable(value = "resumesList", key = "'seekerId=' + #seekerId + ',page=' + #pageable.pageNumber + ',size=' + #pageable.pageSize + ',sort=' + #pageable.sort.toString().replace(': ', '-')")
    public PageDtoOut<ResumeDtoOut> getResumesWithSeekerNamePaginated(BigInteger seekerId, Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT * FROM resume A LEFT JOIN seeker B ON A.seeker_id = B.id");

        String whereClause = "";
        if (!seekerId.equals(BigInteger.valueOf(-1))) {
            whereClause = " WHERE A.seeker_id = :seekerId";
            queryBuilder.append(whereClause);
        }

        if (pageable.getSort().isSorted()) {
            String orderBy = pageable.getSort().stream().map(order -> order.getProperty() + " " + order.getDirection())
                    .collect(Collectors.joining(", "));
            queryBuilder.append(" ORDER BY ").append(orderBy);
        }

        queryBuilder.append(" LIMIT :limit OFFSET :offset");

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("seekerId", seekerId)
                .addValue("limit", pageable.getPageSize()).addValue("offset", pageable.getOffset());

        List<ResumeDtoOut> resumes = namedParameterJdbcTemplate.query(queryBuilder.toString(), mapSqlParameterSource,
                (rs, rowNum) -> ResumeDtoOut.builder().id(rs.getBigDecimal("A.id").toBigInteger())
                        .seekerId(rs.getBigDecimal("seeker_id").toBigInteger()).seekerName(rs.getString("B.name"))
                        .careerObj(rs.getString("career_obj")).title(rs.getString("A.title"))
                        .salary(rs.getInt("A.salary"))
                        .fields(jobFieldRepositoryCustom.getFieldsNameByIds(rs.getString("A.fields")))
                        .provinces(jobProvinceRepositoryCustom.getProvinceByIds(rs.getString("A.provinces"))).build());

        String countQuery = "SELECT COUNT(*) FROM resume A" + whereClause;
        SqlParameterSource countParameters = new MapSqlParameterSource().addValue("seekerId", seekerId);
        long total = namedParameterJdbcTemplate.queryForObject(countQuery, countParameters, Long.class);

        return PageDtoOut.from(pageable.getPageNumber() + 1, pageable.getPageSize(), total, resumes);
    }
}