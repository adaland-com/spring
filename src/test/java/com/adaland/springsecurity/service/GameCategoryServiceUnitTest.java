package com.adaland.springsecurity.service;

import com.adaland.springsecurity.exception.EntityNotFoundException;
import com.adaland.springsecurity.mapper.GameCategoryMapper;
import com.adaland.springsecurity.model.dao.GameCategory;
import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryDto;
import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryUpdateDto;
import com.adaland.springsecurity.repository.GameCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameCategoryServiceUnitTest {
    public static final String CATEGORY_NAME = "detective";
    @Mock
    private GameCategoryRepository gameCategoryRepository;

    @Mock
    private GameCategoryMapper gameCategoryMapper;
    @InjectMocks
    private GameCategoryService gameCategoryService;


    @Test
    void whenFindById_shouldReturnGameCategory() {
        String name = "cooperative";
        GameCategory gameCategory = GameCategory.builder()
                .id(2L)
                .name(name).build();

        GameCategoryDto gameCategoryDto = GameCategoryDto.builder()
                .name(name)
                .id(2L)
                .build();

        when(gameCategoryRepository.findById(2L)).thenReturn(Optional.of(gameCategory));
        when(gameCategoryMapper.fromGameCategoryToGameCategoryDto(gameCategory))
                .thenReturn(gameCategoryDto);

        // When
        GameCategoryDto result = gameCategoryService.findById(2L);

        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(gameCategoryDto);

    }

    @Test
    public void whenFindById_shouldThrowsException() {
        long id = 2;

        assertThrowsExactly(EntityNotFoundException.class, () -> gameCategoryService.findById(id));

    }

    @Test
    void whenFindByName_shouldReturnGameCategory() {
        String name = "cooperative";
        long id = 2;
        GameCategory gameCategory = GameCategory.builder()
                .id(id)
                .name(name).build();

        GameCategoryDto gameCategoryDto = GameCategoryDto.builder()
                .name(name)
                .id(id)
                .build();

        when(gameCategoryRepository.findByName(name)).thenReturn(Optional.of(gameCategory));

        when(gameCategoryMapper.fromGameCategoryToGameCategoryDto(gameCategory))
                .thenReturn(gameCategoryDto);
        // When
        GameCategoryDto result = gameCategoryService.findByName(name);

        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(gameCategoryDto);

    }

    @Test
    public void whenCreateGameCategory_shouldReturnGameCategoryDto() {
        GameCategory gameCategory = GameCategory.builder()
                .id(1L)
                .name(CATEGORY_NAME).build();
        GameCategoryDto gameCategoryDto = GameCategoryDto.builder()
                .name(CATEGORY_NAME)
                .id(1L)
                .build();
        GameCategoryUpdateDto gameCategoryUpdateDto = GameCategoryUpdateDto.builder()
                .name(CATEGORY_NAME
                ).build();
        when(gameCategoryMapper.fromGameCategoryDtoToGameCategory(gameCategoryUpdateDto))
                .thenReturn(gameCategory);

        when(gameCategoryRepository.save(gameCategory)).thenReturn(gameCategory);

        when(gameCategoryMapper.fromGameCategoryToGameCategoryDto(gameCategory))
                .thenReturn(gameCategoryDto);


        GameCategoryDto result = gameCategoryService.createGameCategory(gameCategoryUpdateDto);

        assertThat(result).isNotNull();

    }


    @Test
    void whenUpdateGameCategory_shouldReturnGameCategory() {
        String name = "cooperative";
        long id = 2;
        GameCategory gameCategory = GameCategory.builder()
                .id(id)
                .name(name).build();

        GameCategoryDto gameCategoryDto = GameCategoryDto.builder()
                .name(name)
                .id(id)
                .build();

        when(gameCategoryRepository.findById(2L)).thenReturn(Optional.of(gameCategory));
        when(gameCategoryMapper.fromGameCategoryToGameCategoryDto(gameCategory))
                .thenReturn(gameCategoryDto);

        // When
        GameCategoryDto result = gameCategoryService.findById(2L);

        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(gameCategoryDto);

    }

}