package com.adaland.springsecurity.repository;

import com.adaland.springsecurity.model.dao.Game;
import com.adaland.springsecurity.model.dao.GameCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class GameCategoryRepositoryTest {
    

    @Autowired
    private GameCategoryRepository gameCategoryRepository;

    String existingCategoryName = "test category";
    

    @Test
    public void givenGameCategoryObject_whenSave_thenReturnSavedGameCategory() {
        String gameCategoryName="cooperative";
        GameCategory gameCategory = GameCategory.builder()
                .name(gameCategoryName)
                .build();

        GameCategory savedGameCategory = gameCategoryRepository.save(gameCategory);
        
        assertThat(savedGameCategory).isNotNull();
        assertThat(savedGameCategory.getName()).isEqualTo(gameCategoryName);
    }

    @Test
    public void given_whenFindAll_thenReturnListGameCategory() {
        String gameCategoryName="cooperative";
        GameCategory gameCategory = GameCategory.builder()
                .name(gameCategoryName)
                .build();

        String gameCategoryName2="family";
        GameCategory gameCategory2 = GameCategory.builder()
                .name(gameCategoryName2)
                .build();

        GameCategory savedGameCategory = gameCategoryRepository.save(gameCategory);
        GameCategory savedGameCategory2 = gameCategoryRepository.save(gameCategory2);
        

        List<GameCategory> orderList = gameCategoryRepository.findAll();

        assertThat(orderList.size()).isEqualTo(2);
    }

    @Test
    public void givenGameCategoryObject_whenFindById_thenReturnGameCategory() {
        
        String gameCategoryName="family";
        GameCategory gameCategory = GameCategory.builder()
                .name(gameCategoryName)
                .build();

        GameCategory savedGameCategory = gameCategoryRepository.save(gameCategory);
        
        GameCategory foundGameCategory = gameCategoryRepository.findById(savedGameCategory.getId()).orElse(null);

        assertThat(foundGameCategory).isNotNull();
        assertThat(foundGameCategory.getName()).isEqualTo(gameCategoryName);

    }

    @Test
    public void givenGameCategoryObject_whenUpdateGameCategory_thenReturnUpdatedGameCategory() {
        String gameCategoryName="family";
        GameCategory gameCategory = GameCategory.builder()
                .name(gameCategoryName)
                .build();
        String gameCategoryUpdatedName="logical";

        GameCategory savedGameCategory = gameCategoryRepository.save(gameCategory);
        GameCategory foundGameCategory = gameCategoryRepository.findById(savedGameCategory.getId()).orElse(null);

        savedGameCategory.setName(gameCategoryUpdatedName);
        GameCategory updatedGameCategory = gameCategoryRepository.save(foundGameCategory);

        assertThat(updatedGameCategory).isNotNull();
        assertThat(updatedGameCategory.getName()).isEqualTo(gameCategoryUpdatedName);

    }



    @Test
    public void whenFindByNonExistingGameCategory_thenThrowsException() {
      
        String gameCategoryName = "nonexisting";
     
        Optional<GameCategory> found = gameCategoryRepository.findByName(gameCategoryName);
        
        assertTrue(found.isEmpty());
    }

    @Test
    public void givenGameCategoryObject_whenDelete_thenReturnGameCategoryIsEmpty() {

        String gameCategoryName="logical";
        GameCategory gameCategory = GameCategory.builder()
                .name(gameCategoryName)
                .build();

        gameCategoryRepository.save(gameCategory);

        gameCategoryRepository.deleteById(gameCategory.getId());


        Optional<GameCategory> gameCategoryReturn = gameCategoryRepository.findById(gameCategory.getId());

        Assertions.assertThat(gameCategoryReturn).isEmpty();
    }

}