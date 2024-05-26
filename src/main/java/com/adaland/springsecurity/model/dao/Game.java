package com.adaland.springsecurity.model.dao;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "games")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "status", nullable = true)
    @Enumerated(value = EnumType.STRING)
    private GameStatus status = GameStatus.AVAILABLE;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_category_id")
    private GameCategory gameCategory;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rent_id")
    public Rent rent;


}
