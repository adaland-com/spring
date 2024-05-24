package com.adaland.springsecurity.service;


import com.adaland.springsecurity.exception.EntityNotFoundException;
import com.adaland.springsecurity.mapper.GameCategoryMapper;
import com.adaland.springsecurity.model.dao.GameCategory;
import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryDto;
import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryUpdateDto;
import com.adaland.springsecurity.repository.GameCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameCategoryService {

    private static final String CREATED_ACCOUNT = "Account created ";
    private static final String UPDATED_ACCOUNT = "Account updated ";
    private static final String DELETE_ACCOUNT = "Account deleted ";
    @Autowired
    private GameCategoryRepository gameCategoryRepository;
    private final GameCategoryMapper mapper;

    public List<GameCategoryDto> findAll() {
        return gameCategoryRepository.findAll().stream()
                .map(mapper::fromGameCategoryToGameCategoryDto)
                .collect(Collectors.toList());
    }

    public GameCategoryDto findById(long gameId) {
        GameCategory account = gameCategoryRepository.findById(gameId).orElseThrow(() ->
                new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "game with id: " + gameId));
        return mapper.fromGameCategoryToGameCategoryDto(account);
    }

    public List<GameCategoryDto> findByName(String name) {
        return gameCategoryRepository.findByName(name)
                .stream()
                .map(mapper::fromGameCategoryToGameCategoryDto)
                .collect(Collectors.toList());
    }

    public GameCategoryDto createGameCategory(String name) {
        GameCategory gameCategory = new GameCategory();
        gameCategory.setName(name);
        GameCategory savedGameCategory = gameCategoryRepository.save(gameCategory);
        return mapper.fromGameCategoryToGameCategoryDto(savedGameCategory);

    }

    public GameCategoryDto updateGameCategory(long gameId, GameCategoryDto update) {
        GameCategory gameCategory = gameCategoryRepository.findById(gameId)
                .orElseThrow(()
                        -> new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "game with id: " + gameId));

        GameCategory gameUpdated = mapper.fromGameCategoryDtoToGameCategory(update);
        GameCategory savedGame = gameCategoryRepository.save(gameUpdated);
        return mapper.fromGameCategoryToGameCategoryDto(savedGame);

    }

    public ResponseEntity<String> deleteGameCategory(long gameId) {

        GameCategory gameCategory = gameCategoryRepository.findById(gameId)
                .orElseThrow(()
                        -> new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "game with id: " + gameId));


        gameCategoryRepository.deleteById(gameId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
