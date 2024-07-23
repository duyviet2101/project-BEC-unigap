package vn.unigap.api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "JOBS")
public class Job implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    BigInteger id;

    @Column(name = "employer_id")
    BigInteger employerId;

    @Column(name = "title")
    String title;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "description")
    String description;

    @Column(name = "salary")
    Integer salary;

    @Column(name = "fields")
    String fields;

    @Column(name = "provinces")
    String provinces;

    @Builder.Default
    @Column(name = "created_at")
    Date createdAt = new Date();

    @Builder.Default
    @Column(name = "updated_at")
    Date updatedAt = new Date();

    @Column(name = "expired_at")
    Date expiredAt;
}