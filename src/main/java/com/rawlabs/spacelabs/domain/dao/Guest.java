package com.rawlabs.spacelabs.domain.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "guest")
@SQLDelete(sql = "update guest set is_deleted = true where id = ?")
@Where(clause = "is_deleted = false")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1"
    )
    private Long id;

    @Column(name = "full_name")
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "John Doe"
    )
    private String fullName;

    @Column(name = "phone_number", nullable = false)
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "08937238232"
    )
    private String  phoneNumber;

    @Column(name = "email")
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "johndoe@mail.local"
    )
    private String email;

    @Column(name = "date", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Jakarta")
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDate date;

    @Column(name = "time_start", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm", timezone = "Asia/Jakarta")
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalTime timeStart;

    @Column(name = "time_end", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm", timezone = "Asia/Jakarta")
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalTime timeEnd;

    @Column(name = "is_deleted", nullable = false)
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "false"
    )
    private Boolean isDeleted;

    @JsonIgnore
    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private List<Transaction> transaction;

}
