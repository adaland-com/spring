package com.adaland.springsecurity.service;


import com.adaland.springsecurity.exception.EntityAlreadyExistsException;
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


    @Autowired
    private GameCategoryRepository gameCategoryRepository;
    private final GameCategoryMapper mapper;

    public List<GameCategoryDto> findAll() {
        return gameCategoryRepository.findAll().stream()
                .map(mapper::fromGameCategoryToGameCategoryDto)
                .collect(Collectors.toList());
    }

    public GameCategoryDto findById(long gameCategoryId) {
        GameCategory gameCategory = gameCategoryRepository.findById(gameCategoryId).orElseThrow(() ->
                new EntityNotFoundException(EntityNotFoundException.ENTITY_GAME_CATEGORY_NOT_FOUND_BY_ID + gameCategoryId));
        return mapper.fromGameCategoryToGameCategoryDto(gameCategory);
    }

    public GameCategoryDto findByName(String name) {
        GameCategory gameCategory = gameCategoryRepository.findByName(name).orElseThrow(() ->
                new EntityNotFoundException(EntityNotFoundException.ENTITY_GAME_CATEGORY_NOT_FOUND_BY_NAME, name));
        return mapper.fromGameCategoryToGameCategoryDto(gameCategory);
    }

    public GameCategoryDto createGameCategory(GameCategoryUpdateDto gameCategoryUpdateDto) {
        if (gameCategoryRepository.findByName(gameCategoryUpdateDto.getName()).isPresent()) {
            throw new EntityAlreadyExistsException(EntityAlreadyExistsException.GAME_CATEGORY_AlREADY_EXISTS_MESSAGE, gameCategoryUpdateDto.getName());
        }
        GameCategory gameCategory = mapper.fromGameCategoryDtoToGameCategory(gameCategoryUpdateDto);
        GameCategory savedGameCategory = gameCategoryRepository.save(gameCategory);
        return mapper.fromGameCategoryToGameCategoryDto(savedGameCategory);

    }

    public GameCategoryDto updateGameCategory(long gameCategoryId, GameCategoryDto update) {
        GameCategory gameCategory = gameCategoryRepository.findById(gameCategoryId)
                .orElseThrow(()
                        -> new EntityNotFoundException(EntityNotFoundException.ENTITY_GAME_CATEGORY_NOT_FOUND_BY_ID, String.valueOf(gameCategoryId)));

        GameCategory gameUpdated = mapper.fromGameCategoryDtoToGameCategory(update);
        GameCategory savedGame = gameCategoryRepository.save(gameUpdated);
        return mapper.fromGameCategoryToGameCategoryDto(savedGame);

    }

    public ResponseEntity<String> deleteGameCategory(long gameCategoryId) {

        GameCategory gameCategory = gameCategoryRepository.findById(gameCategoryId)
                .orElseThrow(()
                        -> new EntityNotFoundException(EntityNotFoundException.ENTITY_GAME_CATEGORY_NOT_FOUND_BY_ID, String.valueOf(gameCategoryId)));


        gameCategoryRepository.deleteById(gameCategoryId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
