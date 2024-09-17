package com.adaland.springsecurity.service;


import com.adaland.springsecurity.model.dto.game.GameCreationDto;
import com.adaland.springsecurity.model.dto.game.GameDto;
import com.adaland.springsecurity.model.dto.game.GameUpdateDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface GameService {

    List<GameDto> findAll();

    GameDto findById(long gameId);

    List<GameDto> findByTitle(String title);

    GameDto createGame(GameCreationDto gameToCreate);

    GameDto updateGame(long gameId, GameUpdateDto update);

    ResponseEntity<String> deleteGame(long gameCategoryId);
}
