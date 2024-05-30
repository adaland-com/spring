package com.adaland.springsecurity.repository;


import com.adaland.springsecurity.model.dao.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameCategoryRepository extends JpaRepository<GameCategory, Long> {


    Optional<GameCategory> findByName(String name);
}
