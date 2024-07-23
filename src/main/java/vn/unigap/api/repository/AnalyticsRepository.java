package vn.unigap.api.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.unigap.api.dto.out.AnalyticsNewsDtoOut;
import vn.unigap.api.dto.out.ElementChartDtoOut;
import vn.unigap.common.helpers.Helper;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnalyticsRepository {
    EmployerRepository employerRepository;
    JobRepository jobRepository;
    SeekerRepository seekerRepository;
    ResumeRepository resumeRepository;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<ElementChartDtoOut> getChartForDateRange(String fromDate, String toDate) {
        Date fromDateConverted = Helper.convertStringToDate(fromDate);
        Date toDateConverted = Helper.convertStringToDate(toDate);

        String sql = "WITH job_counts AS ( " +
                "SELECT DATE(created_at) AS createdAt, COUNT(*) AS numJob " +
                "FROM jobs " +
                "WHERE created_at BETWEEN :fromDate AND :toDate " +
                "GROUP BY DATE(created_at) " +
                "), " +
                "employer_counts AS ( " +
                "SELECT DATE(created_at) AS createdAt, COUNT(*) AS numEmployer " +
                "FROM employer " +
                "WHERE created_at BETWEEN :fromDate AND :toDate " +
                "GROUP BY DATE(created_at) " +
                "), " +
                "seeker_counts AS ( " +
                "SELECT DATE(created_at) AS createdAt, COUNT(*) AS numSeeker " +
                "FROM seeker " +
                "WHERE created_at BETWEEN :fromDate AND :toDate " +
                "GROUP BY DATE(created_at) " +
                "), " +
                "resume_counts AS ( " +
                "SELECT DATE(created_at) AS createdAt, COUNT(*) AS numResume " +
                "FROM resume " +
                "WHERE created_at BETWEEN :fromDate AND :toDate " +
                "GROUP BY DATE(created_at) " +
                "), " +
                "combined AS ( " +
                "SELECT createdAt, numJob, 0 AS numEmployer, 0 AS numSeeker, 0 AS numResume FROM job_counts " +
                "UNION ALL " +
                "SELECT createdAt, 0, numEmployer, 0, 0 FROM employer_counts " +
                "UNION ALL " +
                "SELECT createdAt, 0, 0, numSeeker, 0 FROM seeker_counts " +
                "UNION ALL " +
                "SELECT createdAt, 0, 0, 0, numResume FROM resume_counts " +
                ") " +
                "SELECT createdAt, " +
                "SUM(numJob) AS numJob, " +
                "SUM(numEmployer) AS numEmployer, " +
                "SUM(numSeeker) AS numSeeker, " +
                "SUM(numResume) AS numResume " +
                "FROM combined " +
                "GROUP BY createdAt " +
                "ORDER BY createdAt";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("fromDate", fromDateConverted)
                .addValue("toDate", toDateConverted);

        return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> ElementChartDtoOut.builder()
                .date(rs.getString("createdAt"))
                .numJob(rs.getInt("numJob"))
                .numEmployer(rs.getInt("numEmployer"))
                .numSeeker(rs.getInt("numSeeker"))
                .numResume(rs.getInt("numResume"))
                .build());
    }

    @Cacheable("news")
    public AnalyticsNewsDtoOut getNewsByDateRange(String fromDate, String toDate) {
        Date fromDateConverted = Helper.convertStringToDate(fromDate);
        Date toDateConverted = Helper.convertStringToDate(toDate);

        return AnalyticsNewsDtoOut.builder()
                .numEmployer(employerRepository.countEmployersByCreatedAtBetween(fromDateConverted, toDateConverted))
                .numJob(jobRepository.countJobsByCreatedAtBetween(fromDateConverted, toDateConverted))
                .numSeeker(seekerRepository.countSeekersByCreatedAtBetween(fromDateConverted, toDateConverted))
                .numResume(resumeRepository.countResumesByCreatedAtBetween(fromDateConverted, toDateConverted))
                .chart(getChartForDateRange(fromDate, toDate))
                .build();
    }
}
