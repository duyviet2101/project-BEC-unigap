package vn.unigap.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.Employer;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, BigInteger> {
    Optional<Employer> findByEmail(String email);

    Page<Employer> findAll(Pageable page);

    Integer countEmployersByCreatedAtBetween(Date fromDate, Date toDate);

    @Query(value = "SELECT DATE(A.created_at) as createdAt, count(*) as count from employer A where DATE(A.created_at) BETWEEN :fromDate AND :toDate group by DATE(A.created_at)", nativeQuery = true)
    List<Object[]> countEmployersGroupByCreatedAtBetween(Date fromDate, Date toDate);
}