package com.adaland.springsecurity.mapper;

import com.adaland.springsecurity.model.dao.GameCategory;
import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryDto;
import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryUpdateDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface GameCategoryMapper {
    GameCategoryDto fromGameCategoryToGameCategoryDto(GameCategory source);

    GameCategory fromGameCategoryDtoToGameCategory(GameCategoryDto source);

    GameCategory fromGameCategoryDtoToGameCategory(GameCategoryUpdateDto source);


}