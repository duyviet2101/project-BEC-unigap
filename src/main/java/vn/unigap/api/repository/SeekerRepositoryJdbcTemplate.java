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

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SeekerRepositoryJdbcTemplate {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Page<SeekerDtoOut> getSeekersWithProvinceNamePaginated(Pageable pageable, Integer provinceId) {
        String query = "SELECT * FROM seeker A LEFT JOIN job_province B on A.province = B.id" + (provinceId == -1 ? "" : " WHERE A.province = :provinceId") + " ORDER BY :sort LIMIT :limit OFFSET :offset";
        String sort = String.join(" ", pageable.getSort().toString().split(": "));
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("provinceId", provinceId)
                .addValue("limit", pageable.getPageSize())
                .addValue("offset", pageable.getOffset())
                .addValue("sort", sort);
        List<SeekerDtoOut> seekers = namedParameterJdbcTemplate.query(query, sqlParameterSource,
                ((rs, rowNum) -> SeekerDtoOut.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("A.name"))
                        .birthday(rs.getString("birthday"))
                        .address(rs.getString("address"))
                        .provinceId(rs.getInt("A.province"))
                        .provinceName(rs.getString("B.name"))
                        .build()));

        String countQuery = "SELECT COUNT(*) FROM seeker" + (provinceId == -1 ? "" : " WHERE A.province = :provinceId");
        MapSqlParameterSource countParameters = new MapSqlParameterSource().addValue("provinceId", provinceId);
        long total = namedParameterJdbcTemplate.queryForObject(countQuery, countParameters, Long.class);
        return new PageImpl<>(seekers, pageable, total);
    }
}
