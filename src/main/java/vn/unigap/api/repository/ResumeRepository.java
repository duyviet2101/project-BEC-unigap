package vn.unigap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.unigap.api.entity.Resume;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, BigInteger> {
    Integer countResumesByCreatedAtBetween(Date fromDate, Date toDate);

    List<Resume> findBySeekerId(BigInteger id);
}