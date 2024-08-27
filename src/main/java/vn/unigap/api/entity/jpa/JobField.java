package vn.unigap.api.entity.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "job_field")
public class JobField implements Serializable {
    @Id
    @Column(name = "id", columnDefinition = "BIGINT")
    Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "slug")
    String slug;
}