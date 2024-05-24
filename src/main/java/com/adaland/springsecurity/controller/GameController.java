package com.adaland.springsecurity.controller;

import com.adaland.springsecurity.model.dto.game.GameCreationDto;
import com.adaland.springsecurity.model.dto.game.GameDto;
import com.adaland.springsecurity.model.dto.game.GameUpdateDto;
import com.adaland.springsecurity.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/games", produces = "application/json")
public class GameController {

    private final GameService gameService;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
//    @RolesAllowed({"ROLE_CUSTOMER", "ROLE_ADMIN"})
    public List<GameDto> getAll() {
        return gameService.findAll();
    }

    @GetMapping("/{gameId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
//    @RolesAllowed({"ROLE_OPERATOR", "ROLE_USER", "ROLE_ADMIN"})
    public GameDto findById(@PathVariable long gameId) {
        return gameService.findById(gameId);
    }

    @GetMapping("/title={gameTitle}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<GameDto> findByTitle(@PathVariable String gameTitle) {
        return gameService.findByTitle(gameTitle);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDto createGame(@RequestBody GameCreationDto game) {
        return gameService.createGame(game);
    }

    @PutMapping("/{gameId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GameDto updateGame(@PathVariable long gameId, @RequestBody GameUpdateDto game) {
        return gameService.updateGame(gameId, game);
    }

    @DeleteMapping("/{gameId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> deleteGame(@PathVariable long gameId) {
        return gameService.deleteGame(gameId);
    }

}
