package com.adaland.springsecurity.repository;

import com.adaland.springsecurity.model.dao.Game;
import com.adaland.springsecurity.model.dao.GameCategory;
import com.adaland.springsecurity.model.dao.GameStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;


    @Test
    public void givenGameObject_whenSave_thenReturnSavedGame() {
        String gameTitle = "Abalone";
        Game game = Game.builder()
                .title(gameTitle)
                .status(GameStatus.AVAILABLE)
                .build();

        Game savedGame = gameRepository.save(game);

        assertThat(savedGame).isNotNull();
        assertThat(savedGame.getTitle()).isEqualTo(gameTitle);
    }

    @Test
    public void given_whenFindAll_thenReturnListGame() {
        String gameTitle = "Abalone";
        Game game = Game.builder()
                .title(gameTitle)
                .status(GameStatus.AVAILABLE)
                .build();

        String gameTitle2 = "Azul";
        Game game2 = Game.builder()
                .title(gameTitle2)
                .status(GameStatus.AVAILABLE)
                .build();

        Game savedGame = gameRepository.save(game);
        Game savedGame2 = gameRepository.save(game2);
        List<Game> gameList = gameRepository.findAll();

        assertThat(gameList.size()).isEqualTo(2);
        assertThat(gameList.containsAll(List.of(savedGame,savedGame2)));
    }

    @Test
    public void givenGameObject_whenFindById_thenReturnGame() {
        String gameTitle = "Abalone";
        Game game = Game.builder()
                .title(gameTitle)
                .status(GameStatus.AVAILABLE)
                .build();

        Game savedGame = gameRepository.save(game);
        Game foundGame= gameRepository.findById(savedGame.getId()).orElse(null);

        assertThat(foundGame).isNotNull();
        assertThat(foundGame.getTitle()).isEqualTo(gameTitle);
    }

    @Test
    public void givenGameCategoryObject_whenUpdateGameCategory_thenReturnUpdatedGameCategory() {
        String gameTitle = "Abalone";
        String updatedGameTitle = "Catan";
        Game game = Game.builder()
                .title(gameTitle)
                .status(GameStatus.AVAILABLE)
                .build();

        Game savedGame = gameRepository.save(game);
        Game foundGame = gameRepository.findById(savedGame.getId()).get();

        foundGame.setTitle(updatedGameTitle);

        Game updatedGame = gameRepository.save(foundGame);

        assertThat(updatedGame).isNotNull();
        assertThat(updatedGame.getTitle()).isEqualTo(updatedGameTitle);
    }


    @Test
    public void whenFindByNonExistingGame_thenThrowsException() {

        String gameTitle = "nonexisting";

        List<Game> byTitle = gameRepository.findByTitle(gameTitle);

        assertTrue(byTitle.isEmpty());
        assertThat(byTitle.size()).isEqualTo(0);
    }

    @Test
    public void givenGameCategoryObject_whenDelete_thenReturnGameCategoryIsEmpty() {

        String gameTitle = "Abalone";
        String updatedGameTitle = "Catan";
        Game game = Game.builder()
                .title(gameTitle)
                .status(GameStatus.AVAILABLE)
                .build();

        Game savedGame = gameRepository.save(game);
        gameRepository.deleteById(savedGame.getId());
        Optional<Game> foundGame = gameRepository.findById(savedGame.getId());

        assertThat(foundGame).isEmpty();
    }

}