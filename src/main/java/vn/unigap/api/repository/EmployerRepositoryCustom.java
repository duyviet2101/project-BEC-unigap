package vn.unigap.api.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployerRepositoryCustom {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Cacheable(cacheNames = "employersList", key = "'page=' + #pageable.pageNumber + ',size=' + #pageable.pageSize + ',sort=' + #pageable.sort.toString().replace(': ', '-')")
    public PageDtoOut<EmployerDtoOut> getEmployersPaginated(Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM employer");

        if (pageable.getSort().isSorted()) {
            String orderBy = pageable.getSort().stream()
                    .map(order -> order.getProperty() + " " + order.getDirection())
                    .collect(Collectors.joining(", "));
            queryBuilder.append(" ORDER BY ").append(orderBy);
        } else {
            queryBuilder.append(" ORDER BY id ASC");
        }

        queryBuilder.append(" LIMIT :limit OFFSET :offset");

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("limit", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        List<EmployerDtoOut> employers = namedParameterJdbcTemplate.query(queryBuilder.toString(), mapSqlParameterSource, (rs, rowNum) -> EmployerDtoOut.builder()
                .id(rs.getBigDecimal("id").toBigInteger())
                .email(rs.getString("email"))
                .name(rs.getString("name"))
                .province(rs.getInt("province"))
                .description(rs.getString("description"))
                .createdAt(rs.getTimestamp("created_at"))
                .updatedAt(rs.getTimestamp("updated_at"))
                .build());

        Long totalElements = namedParameterJdbcTemplate.queryForObject("SELECT COUNT(*) FROM employer", new MapSqlParameterSource(), Long.class);

        return PageDtoOut.from(pageable.getPageNumber(), pageable.getPageSize(), totalElements, employers);
    }
}
