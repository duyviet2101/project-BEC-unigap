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
import vn.unigap.api.dto.out.SeekerDtoOut;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SeekerRepositoryCustom {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Page<SeekerDtoOut> getSeekersWithProvinceNamePaginated(Pageable pageable, Integer provinceId) {
        // Start building the base query
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM seeker A LEFT JOIN job_province B ON A.province = B.id");
        if (provinceId != -1) {
            queryBuilder.append(" WHERE A.province = :provinceId");
        }

        // Dynamically build the ORDER BY clause
        if (pageable.getSort().isSorted()) {
            String orderBy = pageable.getSort().stream()
                    .map(order -> "A." + order.getProperty() + " " + order.getDirection())
                    .collect(Collectors.joining(", "));
            queryBuilder.append(" ORDER BY ").append(orderBy);
        } else {
            // Default sort condition if no sort is specified
            queryBuilder.append(" ORDER BY A.name ASC");
        }

        // Append LIMIT and OFFSET for pagination
        queryBuilder.append(" LIMIT :limit OFFSET :offset");

        // Prepare parameters
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("provinceId", provinceId)
                .addValue("limit", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        // Execute the query
        List<SeekerDtoOut> seekers = namedParameterJdbcTemplate.query(queryBuilder.toString(), sqlParameterSource,
                (rs, rowNum) -> SeekerDtoOut.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("A.name"))
                        .birthday(rs.getString("birthday"))
                        .address(rs.getString("address"))
                        .provinceId(rs.getInt("A.province"))
                        .provinceName(rs.getString("B.name"))
                        .build());

        // Count query to get total number of records
        String countQuery = "SELECT COUNT(*) FROM seeker A" + (provinceId != -1 ? " WHERE A.province = :provinceId" : "");
        MapSqlParameterSource countParameters = new MapSqlParameterSource().addValue("provinceId", provinceId);
        long total = namedParameterJdbcTemplate.queryForObject(countQuery, countParameters, Long.class);

        return new PageImpl<>(seekers, pageable, total);
    }
}
