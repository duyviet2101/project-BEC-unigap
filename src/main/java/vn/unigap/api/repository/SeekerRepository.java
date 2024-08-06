package vn.unigap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.Seeker;

import java.math.BigInteger;
import java.util.Date;

@Repository
public interface SeekerRepository extends JpaRepository<Seeker, BigInteger> {
    Seeker getSeekerById(BigInteger id);

    boolean existsSeekerById(BigInteger id);

    Integer countSeekersByCreatedAtBetween(Date fromDate, Date toDate);

    @Query("SELECT count(A) from Seeker A where DATE(A.createdAt) = DATE(?1) group by DATE(A.createdAt)")
    Integer countSeekersByCreatedAt(Date createdAt);
}