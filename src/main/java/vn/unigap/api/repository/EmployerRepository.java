package vn.unigap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.Employer;

import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, BigInteger> {
    Optional<Employer> findByEmail(String email);

    Integer countEmployersByCreatedAtBetween(Date fromDate, Date toDate);

    Employer getEmployerById(BigInteger id);
}