package com.adaland.springsecurity.service;

import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryDto;
import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryUpdateDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GameCategoryService {
    List<GameCategoryDto> findAll();

    GameCategoryDto findById(long gameCategoryId);

    GameCategoryDto findByName(String name);

    GameCategoryDto createGameCategory(GameCategoryUpdateDto gameCategoryUpdateDto);

    GameCategoryDto updateGameCategory(long gameCategoryId, GameCategoryDto update);

    ResponseEntity<String> deleteGameCategory(long gameCategoryId);
}
