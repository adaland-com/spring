package com.adaland.springsecurity.service;


import com.adaland.springsecurity.exception.EntityNotFoundException;
import com.adaland.springsecurity.mapper.GameMapper;
import com.adaland.springsecurity.model.dao.Game;
import com.adaland.springsecurity.model.dao.GameCategory;
import com.adaland.springsecurity.model.dao.GameStatus;
import com.adaland.springsecurity.model.dto.game.GameCreationDto;
import com.adaland.springsecurity.model.dto.game.GameDto;
import com.adaland.springsecurity.model.dto.game.GameUpdateDto;
import com.adaland.springsecurity.repository.GameCategoryRepository;
import com.adaland.springsecurity.repository.GameRepository;
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
public class GameService {

    private static final String CREATED_ACCOUNT = "Account created ";
    private static final String UPDATED_ACCOUNT = "Account updated ";
    private static final String DELETE_ACCOUNT = "Account deleted ";
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GameCategoryRepository categoryRepository;
    private final GameMapper mapper;

    public List<GameDto> findAll() {
        return gameRepository.findAll().stream()
                .map(mapper::fromGameToGameDto)
                .collect(Collectors.toList());
    }

    public GameDto findById(long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() ->
                new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "game with id: " + gameId));
        return mapper.fromGameToGameDto(game);
    }

    public List<GameDto> findByTitle(String title) {
        return gameRepository.findByTitle(title)
                .stream()
                .map(mapper::fromGameToGameDto)
                .collect(Collectors.toList());
    }

    public GameDto createGame(GameCreationDto gameToCreate) {
        Game game = mapper.fromGameCreationDtoToGame(gameToCreate);
        game.setStatus(GameStatus.AVAILABLE);
        Game savedGame = gameRepository.save(game);
        return mapper.fromGameToGameDto(savedGame);

    }

    public GameDto updateGame(long gameId, GameUpdateDto update) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(()
                        -> new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "game with id: " + gameId));
        List<GameCategory> byName = categoryRepository.findByName(update.getCategory());
        if (byName.isEmpty())
            throw new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "category with name: " + update.getCategory());
        else {
            game.setGameCategory(byName.get(0));
        }
        Game gameUpdated = mapper.fromGameUpdateDtoToGame(game, update);
        Game savedGame = gameRepository.save(gameUpdated);
        return mapper.fromGameToGameDto(savedGame);

    }

    public ResponseEntity<String> deleteGame(long gameId) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(()
                        -> new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "game with id: " + gameId));


        gameRepository.deleteById(game.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
