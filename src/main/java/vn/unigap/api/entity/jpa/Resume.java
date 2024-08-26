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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "resume")
public class Resume implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    BigInteger id;

    @Column(name = "seeker_id")
    BigInteger seekerId;

    @Column(name = "career_obj")
    String careerObj;

    @Column(name = "title")
    String title;

    @Column(name = "salary")
    Integer salary;

    @Column(name = "fields")
    String fields;

    @Column(name = "provinces")
    String provinces;

    @Column(name = "created_at")
    @CreatedDate
    @Builder.Default
    Date createdAt = new Date();

    @Column(name = "updated_at")
    @UpdateTimestamp
    Date updatedAt;
}
