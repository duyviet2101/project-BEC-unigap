package vn.unigap.api.entity.jpa;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "seeker")
public class Seeker implements Serializable {
    @Id
    @Column(name = "id", columnDefinition = "BIGINT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    BigInteger id;

    @Column(name = "name")
    String name;

    @Column(name = "birthday")
    String birthday;

    @Column(name = "address")
    String address;

    @Column(name = "province")
    Integer province;

    @CreatedDate
    @Column(name = "created_at")
    @Builder.Default
    Date createdAt = new Date();

    @UpdateTimestamp
    @Column(name = "updated_at")
    Date updatedAt;
}
