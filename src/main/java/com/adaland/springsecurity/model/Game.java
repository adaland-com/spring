package com.adaland.springsecurity.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Table(name = "games")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "status", nullable = true)
    @Enumerated(value = EnumType.STRING)
    private GameStatus status = GameStatus.AVAILABLE;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gameCategory_id", referencedColumnName = "id")
    private GameCategory gameCategory;

}
