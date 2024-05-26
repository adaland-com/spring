package com.adaland.springsecurity.mapper;

import com.adaland.springsecurity.model.dao.Game;
import com.adaland.springsecurity.model.dto.game.GameCreationDto;
import com.adaland.springsecurity.model.dto.game.GameDto;
import com.adaland.springsecurity.model.dto.game.GameUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface GameMapper {

    GameDto fromGameToGameDto(Game source);

    Game fromGameCreationDtoToGame(GameCreationDto source);

    Game fromGameDtoToGame(GameDto destination);

    Game fromGameUpdateDtoToGame(@MappingTarget Game entity, GameUpdateDto update);
}