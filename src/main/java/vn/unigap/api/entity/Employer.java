package vn.unigap.api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    BigInteger id;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "name")
    String name;

    @Column(name = "province")
    Integer province;

    @Column(name = "description")
    String description;

    @Column(name = "created_at")
    @Builder.Default
    Date createdAt = new Date();

    @Column(name = "updated_at")
    @Builder.Default
    Date updatedAt = new Date();
}
