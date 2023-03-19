package com.rawlabs.spacelabs.domain.dao;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coworking_space_facility")
@SQLDelete(sql = "update coworking_space_facility set is_deleted = true where id = ?")
@Where(clause = "is_deleted = false")
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "icon", nullable = false)
    private String icon;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;
    @ManyToOne
    @Schema(
            description = "Coworking Space",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CoworkingSpace coworkingSpace;
}
