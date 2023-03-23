package com.rawlabs.spacelabs.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coworking_space")
@SQLDelete(sql = "update coworking_space set is_deleted = true where id = ?")
@Where(clause = "is_deleted = false")
public class CoworkingSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "address", nullable = false)
    private String address;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="coworkingSpace")
    private List<CoworkingSpaceGalery> coworkingSpaceGaleries;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="coworkingSpace")
    private List<Facility> facilities;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "coworkingSpace", cascade = CascadeType.ALL)
    private List<Transaction> transaction;
}
