package com.adaland.springsecurity.repository;


import com.adaland.springsecurity.model.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "games", path = "games")
public interface GameCategoryRepository extends JpaRepository<GameCategory, Long> {


    List<GameCategory> findByName(String name);
}
