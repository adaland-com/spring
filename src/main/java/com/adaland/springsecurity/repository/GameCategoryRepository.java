package com.adaland.springsecurity.repository;


import com.adaland.springsecurity.model.dao.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameCategoryRepository extends JpaRepository<GameCategory, Long> {


    List<GameCategory> findByName(String name);
}
