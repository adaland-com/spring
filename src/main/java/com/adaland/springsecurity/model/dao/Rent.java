package com.adaland.springsecurity.model.dao;

import com.adaland.springsecurity.model.auth.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

@Data
@Entity
@Table(name = "rents")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "cost", nullable = false)
    private BigDecimal cost = BigDecimal.ONE;
    @OneToMany(mappedBy = "rent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Game> games;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "creationTs", nullable = false)
    private LocalDateTime creationTs;
    @Column(name = "updateTs", nullable = false)
    private LocalDateTime updateTs;
    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;
    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;
    @Column(name = "status", nullable = true)
    @Enumerated(value = EnumType.STRING)
    private RentStatus status;
    @Column(name = "isSettled", nullable = false)
    private boolean isSettled;

}
