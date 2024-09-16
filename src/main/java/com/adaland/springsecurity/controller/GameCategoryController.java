package com.adaland.springsecurity.controller;

import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryDto;
import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryUpdateDto;
import com.adaland.springsecurity.service.GameCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping(path = "/api/game_categories", produces = "application/json")
public class GameCategoryController {

    private final GameCategoryService gameCategoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<GameCategoryDto> getAll() {
        return gameCategoryService.findAll();
    }

    @GetMapping("/{gameCategoryId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GameCategoryDto findById(@PathVariable long gameCategoryId) {
        return gameCategoryService.findById(gameCategoryId);
    }

    @GetMapping("/name={name}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GameCategoryDto findByName(@PathVariable String name) {
        return gameCategoryService.findByName(name);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GameCategoryDto createGameCategory(@RequestBody GameCategoryUpdateDto name) {
        return gameCategoryService.createGameCategory(name);
    }

    @PutMapping("/{gameCategoryId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GameCategoryDto updateGameCategory(@PathVariable long gameCategoryId, @RequestBody GameCategoryDto game) {
        return gameCategoryService.updateGameCategory(gameCategoryId, game);
    }

    @DeleteMapping("/{gameCategoryId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteGameCategory(@PathVariable long gameCategoryId) {
        return gameCategoryService.deleteGameCategory(gameCategoryId);
    }

}
