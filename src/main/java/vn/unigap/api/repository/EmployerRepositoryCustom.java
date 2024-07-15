package vn.unigap.api.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployerRepositoryCustom {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    EmployerRepository employerRepository;

    void countEmployersGroupByCreatedAtBetween(Date fromDate, Date toDate) {

    }
}
