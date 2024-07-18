package com.adaland.springsecurity.repository;

import com.adaland.springsecurity.model.dao.GameCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class GameCategoryRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GameCategoryRepository gameCategoryRepository;

    String existingCategoryName = "test category";

    @BeforeEach
    void setUp() {
        GameCategory gameCategoryToAdd = new GameCategory();
        gameCategoryToAdd.setName(existingCategoryName);

        entityManager.persist(gameCategoryToAdd);
    }

    @Test
    public void whenFindByName_thenReturnGameCategory() {
        // arrange
        String gameCategoryName = existingCategoryName;

        // act
        GameCategory found = gameCategoryRepository.findByName(gameCategoryName).get();

        // assert
        assertThat(found.getName()).isEqualTo(gameCategoryName);
    }

    @Test
    public void whenFindByNonExistingGameCategory_thenThrowsException() {
        // arrange
        String gameCategoryName = "nonexisting";

        // act
        Optional<GameCategory> found = gameCategoryRepository.findByName(gameCategoryName);

        // assert
        assertTrue(found.isEmpty());
    }

    @Test
    void givenGameCategoryCreated_whenUpdate_thenSuccess() {
        GameCategory gameCategory = GameCategory.builder().name("category name").build();
        String newName = "new category name";
        gameCategory.setName(newName);
        entityManager.persist(gameCategory);
        gameCategoryRepository.save(gameCategory);
            assertThat(entityManager.find(GameCategory.class, gameCategory.getId()).getName()).isEqualTo(newName);
    }

    @Test
    void givenGameCategoryCreated_whenDelete_thenSuccess() {
        GameCategory gameCategory = GameCategory.builder().name("category name").build();
        entityManager.persist(gameCategory);
        gameCategoryRepository.delete(gameCategory);
        assertThat(entityManager.find(GameCategory.class, gameCategory.getId())).isNull();

    }
}