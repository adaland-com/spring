package com.adaland.springsecurity.repository;


import com.adaland.springsecurity.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//@RepositoryRestResource(collectionResourceRel = "games", path = "games")
public interface GameRepository extends JpaRepository<Game, Long> {


    List<Game> findByTitle(String title);
    Optional<Game> findById(Long id);
}
