package vn.unigap.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.Job;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, BigInteger> {
    Page<Job> findAllByEmployerId(BigInteger employerId, Pageable pageable);

    Integer countJobsByCreatedAtBetween(Date fromDate, Date toDate);

    @Query("SELECT count(A) from Job A where DATE(A.createdAt) = DATE(?1) group by DATE(A.createdAt)")
    Integer countJobsByCreatedAt(Date createdAt);

    List<Job> getJobsByCreatedAtBetween(Date fromDate, Date toDate);
}
