package com.adaland.springsecurity.service;

import com.adaland.springsecurity.exception.EntityAlreadyExistsException;
import com.adaland.springsecurity.exception.EntityNotFoundException;
import com.adaland.springsecurity.mapper.GameCategoryMapper;
import com.adaland.springsecurity.model.dao.GameCategory;
import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryDto;
import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryUpdateDto;
import com.adaland.springsecurity.repository.GameCategoryRepository;
import com.adaland.springsecurity.service.impl.GameCategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameCategoryServiceUnitTest {
    public static final String CATEGORY_NAME = "detective";
    @Mock
    private GameCategoryRepository gameCategoryRepository;

    @Mock
    private GameCategoryMapper gameCategoryMapper;
    @InjectMocks
    private GameCategoryServiceImpl gameCategoryServiceImpl;

    @Test
    void whenFindAll_shouldReturnGameCategoryList() {
        String gameCategoryName1="cooperative";
        String gameCategoryName2="family";

        GameCategory gameCategory1=GameCategory.builder()
                .name(gameCategoryName1).build();
        GameCategory gameCategory2=GameCategory.builder()
                .name(gameCategoryName2).build();

        given(gameCategoryRepository.findAll()).willReturn(List.of(gameCategory1,gameCategory2));

        List<GameCategoryDto> foundGameCategories=gameCategoryServiceImpl.findAll();

        assertThat(foundGameCategories).isNotNull();
        assertThat(foundGameCategories.size()).isEqualTo(2);


    }
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
        GameCategoryDto result = gameCategoryServiceImpl.findById(2L);

        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(gameCategoryDto);

    }
    @Test
    public void givenGameCategoryObject_whenGetGameCategoryById_thenThrowException() {
        long gameCategoryId = 1;

        when(gameCategoryRepository.findById(gameCategoryId)).thenThrow(new EntityNotFoundException(EntityNotFoundException.ENTITY_GAME_CATEGORY_NOT_FOUND_BY_ID,String.valueOf(gameCategoryId)));

        assertThatThrownBy(() -> gameCategoryServiceImpl.findById(gameCategoryId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(EntityNotFoundException.ENTITY_GAME_CATEGORY_NOT_FOUND_BY_ID, gameCategoryId);

    }


    @Test
    public void whenFindById_shouldThrowsException() {
        long id = 2;

        assertThrowsExactly(EntityNotFoundException.class, () -> gameCategoryServiceImpl.findById(id));

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
        GameCategoryDto result = gameCategoryServiceImpl.findByName(name);

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
        when(gameCategoryMapper.fromGameCategoryUpdateDtoToGameCategory(gameCategoryUpdateDto))
                .thenReturn(gameCategory);

        when(gameCategoryRepository.save(gameCategory)).thenReturn(gameCategory);

        when(gameCategoryMapper.fromGameCategoryToGameCategoryDto(gameCategory))
                .thenReturn(gameCategoryDto);

        GameCategoryDto result = gameCategoryServiceImpl.createGameCategory(gameCategoryUpdateDto);

        assertThat(result).isNotNull();

    }
    @Test
    public void whenCreateGameCategory_shouldThrowEntityAlreadyExistException() {
        long gameCategoryId = 1L;
        String gameCategoryName = "logical";
        GameCategoryUpdateDto gameCategoryUpdateDto = GameCategoryUpdateDto.builder()
                .name(gameCategoryName)
                .build();

        GameCategory gameCategory = GameCategory.builder()
                .id(gameCategoryId)
                .name(gameCategoryName).build();


        when(gameCategoryRepository.findByName(gameCategoryName))
                .thenThrow(new EntityAlreadyExistsException(EntityAlreadyExistsException.GAME_CATEGORY_AlREADY_EXISTS_MESSAGE,gameCategoryName));

        assertThatThrownBy(() -> gameCategoryServiceImpl.createGameCategory(gameCategoryUpdateDto))
                .isInstanceOf(EntityAlreadyExistsException.class)
                .hasMessage(EntityAlreadyExistsException.GAME_CATEGORY_AlREADY_EXISTS_MESSAGE, gameCategoryName);

    }



    @Test
    void whenUpdateGameCategory_shouldReturnGameCategory() {
        String name = "cooperative";
        long gameCategoryId = 2;
        GameCategory gameCategory = GameCategory.builder()
                .id(gameCategoryId)
                .name(name).build();

        GameCategoryDto gameCategoryDto = GameCategoryDto.builder()
                .name(name)
                .id(gameCategoryId)
                .build();

        when(gameCategoryRepository.findById(gameCategoryId)).thenReturn(Optional.of(gameCategory));
        when(gameCategoryMapper.fromGameCategoryToGameCategoryDto(gameCategory))
                .thenReturn(gameCategoryDto);

        // When
        GameCategoryDto result = gameCategoryServiceImpl.findById(gameCategoryId);

        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(gameCategoryDto);

    }

}