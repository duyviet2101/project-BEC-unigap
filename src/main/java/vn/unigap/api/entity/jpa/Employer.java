package vn.unigap.api.entity.jpa;

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
@Table(name = "EMPLOYER")
public class Employer implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
