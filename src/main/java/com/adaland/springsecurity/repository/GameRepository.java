package com.adaland.springsecurity.repository;


import com.adaland.springsecurity.model.dao.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {


    List<Game> findByTitle(String title);

    Optional<Game> findById(long id);
}
