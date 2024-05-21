package com.adaland.springsecurity.mapper;

import com.adaland.springsecurity.model.GameCategory;
import com.adaland.springsecurity.model.dto.GameCategoryDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface GameCategoryMapper {
    GameCategoryDto fromGameCategoryToGameCategoryDto(GameCategory source);

    GameCategory fromGameCategoryDtoToGameCategory(GameCategoryDto source);

    ;


}