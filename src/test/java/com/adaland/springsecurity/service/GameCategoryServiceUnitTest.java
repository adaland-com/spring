package com.adaland.springsecurity.service;

import com.adaland.springsecurity.mapper.GameCategoryMapper;
import com.adaland.springsecurity.model.dao.GameCategory;
import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryDto;
import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryUpdateDto;
import com.adaland.springsecurity.repository.GameCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class GameCategoryServiceUnitTest {
    public static final String CATEGORY_NAME = "detective";
    @Mock
    private GameCategoryRepository gameCategoryRepository;

    @InjectMocks
    private GameCategoryService gameCategoryService;

    @Mock
    private GameCategoryMapper gameCategoryMapper;

    String existingCategoryName = "test category";
    GameCategory existingGameCategory;

    @BeforeEach
    void setUp() {
        existingGameCategory = GameCategory.builder()
                .id(1L)
                .name(CATEGORY_NAME)
                .build();

    }

    @Test
    public void GameCategoryService_CreateGameCategory_ReturnsGameCategoryDto() {
        GameCategory gameCategory = GameCategory.builder()
                .name(CATEGORY_NAME).build();
        gameCategoryRepository.save(gameCategory);
        ArgumentCaptor<GameCategory> gameCategoryArgumentCaptor = ArgumentCaptor.forClass(GameCategory.class);
        verify(gameCategoryRepository)
                .save(gameCategoryArgumentCaptor.capture());
        GameCategory capturedGameCategory = gameCategoryArgumentCaptor.getValue();
        assertThat(capturedGameCategory).isEqualTo(gameCategory);

    }

    @Test
    public void GameCategoryService_FindById_BDD() {
        long gameCategoryId = 1;

        GameCategory gameCategory = GameCategory.builder()
                .id(gameCategoryId)
                .name(CATEGORY_NAME).build();
        GameCategoryDto gameCategoryDto = GameCategoryDto.builder()
                .id(gameCategoryId)
                .name(CATEGORY_NAME).build();
        GameCategoryUpdateDto gameCategoryUpdateDto = GameCategoryUpdateDto.builder().name(CATEGORY_NAME).build();

        given(gameCategoryRepository.findById(gameCategoryId))
                .willReturn(Optional.ofNullable(gameCategory));

        gameCategoryService.findById(gameCategoryId);

        then(gameCategoryRepository)
                .should()
                .findById(gameCategoryId);

    }

    @Test
    public void GameCategoryService_FindByName() {
        long gameCategoryId = 1;

        GameCategory gameCategory = GameCategory.builder()
                .id(gameCategoryId)
                .name(CATEGORY_NAME).build();
        GameCategoryDto gameCategoryDto = GameCategoryDto.builder()
                .id(gameCategoryId)
                .name(CATEGORY_NAME).build();
        GameCategoryUpdateDto gameCategoryUpdateDto = GameCategoryUpdateDto.builder().name(CATEGORY_NAME).build();

        when(gameCategoryRepository.findByName(CATEGORY_NAME))
                .thenReturn(Optional.ofNullable(gameCategory));

        GameCategoryDto categoryDto = gameCategoryService.findByName(CATEGORY_NAME);

        verify(gameCategoryRepository).findByName(CATEGORY_NAME);
    }


}