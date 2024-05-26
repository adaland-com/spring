package com.adaland.springsecurity.model.dao;

import com.adaland.springsecurity.model.auth.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "rents")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rent {

    @Id
    @Column(unique = true, nullable = false)
    private String id = String.valueOf(UUID.randomUUID());
    @Column(name = "cost", nullable = false)
    private BigDecimal cost = BigDecimal.ONE;
    @OneToMany(mappedBy = "rent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Game> games;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "creationTs", nullable = false)
    private LocalDateTime creationTs = LocalDateTime.now();
    @Column(name = "updateTs", nullable = false)
    private LocalDateTime updateTs = LocalDateTime.now();
    @Column(name = "startDate", nullable = false)
    private LocalDate startDate = LocalDate.from(LocalDateTime.now());
    @Column(name = "endDate", nullable = false)
    private LocalDate endDate = startDate.plusDays(7);
    @Column(name = "isActive", nullable = false)
    private boolean isActive = true;
    @Column(name = "isSettled", nullable = false)
    private boolean isSettled = false;

}
