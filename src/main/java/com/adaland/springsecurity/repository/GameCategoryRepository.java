package com.adaland.springsecurity.repository;


import com.adaland.springsecurity.model.dao.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GameCategoryRepository extends JpaRepository<GameCategory, Long> {


    List<GameCategory> findByName(String name);
}
