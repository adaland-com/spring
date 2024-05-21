package com.adaland.springsecurity.mapper;

import com.adaland.springsecurity.model.Game;
import com.adaland.springsecurity.model.dto.GameCreationDto;
import com.adaland.springsecurity.model.dto.GameDto;
import com.adaland.springsecurity.model.dto.GameUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface GameMapper {
    GameDto fromGameToGameDto(Game source);

    Game fromGameCreationDtoToGame(GameCreationDto source);

    Game fromGameDtoToGame(GameDto destination);

    Game fromGameUpdateDtoToGame(@MappingTarget Game entity, GameUpdateDto update);
}