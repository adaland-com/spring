package com.adaland.springsecurity.service;

import com.adaland.springsecurity.exception.EntityNotFoundException;
import com.adaland.springsecurity.mapper.GameMapper;
import com.adaland.springsecurity.model.dao.Game;
import com.adaland.springsecurity.model.dao.GameCategory;
import com.adaland.springsecurity.model.dto.game.GameDto;
import com.adaland.springsecurity.model.dto.game.GameUpdateDto;
import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryDto;
import com.adaland.springsecurity.repository.GameCategoryRepository;
import com.adaland.springsecurity.repository.GameRepository;
import com.adaland.springsecurity.service.impl.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GameServiceUnitTest {
    @Mock
    private GameRepository gameRepository;
    @Mock
    private GameCategoryRepository gameCategoryRepository;
    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private GameServiceImpl gameServiceImpl;


    private GameCategory gameCategory;
    private GameCategoryDto gameCategoryDto;
    private Game game;
    private GameDto gameDto;

    @BeforeEach
    public void init() {
        gameCategory = GameCategory.builder().name("family").build();
        gameCategoryDto = GameCategoryDto.builder().name("family").build();

        game=Game.builder()
                .id(1L)
                .title("Abalone")
                .build();
        gameDto=GameDto.builder()
                .id(1L)
                .title("Abalone")
                .build();
    }

    @Test
    void whenFindAll_shouldReturnGameList() {

        String gameTitle1 = "Abalone";
        String gameTitle2 = "Carccasonne";

        Game game1 = Game.builder()
                .title(gameTitle1)
                .build();

        Game game2 = Game.builder()
                .title(gameTitle2)
                .build();

        given(gameRepository.findAll()).willReturn(List.of(game1, game2));

        List<GameDto> foundGameCategories = gameServiceImpl.findAll();

        assertThat(foundGameCategories).isNotNull();
        assertThat(foundGameCategories.size()).isEqualTo(2);
    }

    @Test
    void whenFindById_shouldReturnGame() {
        String gameTitle = "Abalone";
        long gameId = 1L;

        Game game = Game.builder()
                .id(gameId)
                .title(gameTitle)
                .build();

        GameDto gameDto = GameDto.builder()
                .id(gameId)
                .title(gameTitle)
                .build();

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(gameMapper.fromGameToGameDto(game))
                .thenReturn(gameDto);

        // When
        GameDto result = gameServiceImpl.findById(gameId);

        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(gameDto);
    }

    @Test
    public void givenGameId_whenFindByIdGame_thenThrowException() {
        long gameId = 1;

        when(gameRepository.findById(gameId)).thenThrow(new EntityNotFoundException(EntityNotFoundException.ENTITY_GAME_NOT_FOUND_BY_ID, String.valueOf(gameId)));

        assertThatThrownBy(() -> gameServiceImpl.findById(gameId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(EntityNotFoundException.ENTITY_GAME_NOT_FOUND_BY_ID, gameId);
    }


    @Test
    void whenFindByTitle_shouldReturnGame() {
        String gameTitle = "catan";
        long gameId = 2;

        Game game = Game.builder()
                .id(gameId)
                .title(gameTitle)
                .build();

        GameDto gameDto = GameDto.builder()
                .id(gameId)
                .title(gameTitle)
                .build();


        when(gameRepository.findByTitle(gameTitle)).thenReturn(List.of(game));
        when(gameMapper.fromGameToGameDto(game)).thenReturn(gameDto);


        // When
        List<GameDto> foundGameList = gameServiceImpl.findByTitle(gameTitle);

        //then
        assertThat(foundGameList).isNotNull();
        assertThat(foundGameList.size()).isEqualTo(1);
    }

    @Test
    void whenUpdateGame_shouldReturnGame() {
        long gameId=1;
        String gameCategoryName="family";
        String gameUpdatedTitle="7 wonders";
        GameUpdateDto updateDto = GameUpdateDto.builder()
                .category(gameCategoryName)
                .title(gameUpdatedTitle)
                .build();
        Game updatedGame=game;
        updatedGame.setTitle(gameUpdatedTitle);

        GameDto updatedGameDto = gameDto;
        updatedGameDto.setTitle(gameUpdatedTitle);


        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(gameCategoryRepository.findByName(updateDto.getCategory())).thenReturn(Optional.of(gameCategory));
        when(gameMapper.fromGameUpdateDtoToGame(game, updateDto)).thenReturn(updatedGame);
        when(gameRepository.save(updatedGame)).thenReturn(updatedGame);
        when(gameMapper.fromGameToGameDto(updatedGame)).thenReturn(updatedGameDto);

        GameDto result = gameServiceImpl.updateGame(gameId, updateDto);

        assertThat(result).usingRecursiveComparison().isEqualTo(updatedGameDto);

    }
    @Test
    void deleteGame_ShouldReturnOk_WhenGameExists() {
        String gameTitle = "catan";
        long gameId = 2;

        Game game = Game.builder()
                .id(gameId)
                .title(gameTitle)
                .build();


        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        ResponseEntity<String> response = gameServiceImpl.deleteGame(gameId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(gameRepository).findById(gameId);
        verify(gameRepository).deleteById(gameId);
    }

}