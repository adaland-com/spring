package com.adaland.springsecurity.service.impl;


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
import com.adaland.springsecurity.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameCategoryRepository categoryRepository;
    private final GameMapper mapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, GameCategoryRepository categoryRepository, GameMapper mapper) {
        this.gameRepository = gameRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

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
                        -> new EntityNotFoundException(EntityNotFoundException.ENTITY_GAME_NOT_FOUND_BY_ID,  String.valueOf(gameId)));
        GameCategory gameCategory= categoryRepository.findByName(update.getCategory())
                 .orElseThrow(()
                         -> new EntityNotFoundException(EntityNotFoundException.ENTITY_GAME_CATEGORY_NOT_FOUND_BY_NAME, update.getCategory()));

        game.setGameCategory(gameCategory);
        Game gameUpdated = mapper.fromGameUpdateDtoToGame(game, update);
        Game savedGame = gameRepository.save(gameUpdated);
        return mapper.fromGameToGameDto(savedGame);

    }

    public ResponseEntity<String> deleteGame(long gameId) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(()
                        -> new EntityNotFoundException(EntityNotFoundException.ENTITY_GAME_NOT_FOUND_BY_ID, String.valueOf(gameId)));


        gameRepository.deleteById(game.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Game deleted");
    }
}
