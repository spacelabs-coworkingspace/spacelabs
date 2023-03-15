package com.rawlabs.spacelabs.domain.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coworking_space_galery")
@SQLDelete(sql = "update coworking_space_galery set is_deleted = true where id = ?")
@Where(clause = "is_deleted = false")
public class CoworkingSpaceGalery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "img_url", nullable = false)
    private String img_url;

    @ManyToOne
    @Schema(
            description = "Coworking Space",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CoworkingSpace coworkingSpace;

}
