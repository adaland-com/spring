package com.adaland.springsecurity.mapper;

import com.adaland.springsecurity.model.dao.Game;
import com.adaland.springsecurity.model.dao.Rent;
import com.adaland.springsecurity.model.dto.game.GameCreationDto;
import com.adaland.springsecurity.model.dto.game.GameDto;
import com.adaland.springsecurity.model.dto.game.GameUpdateDto;
import com.adaland.springsecurity.model.dto.rent.RentCreationDto;
import com.adaland.springsecurity.model.dto.rent.RentDto;
import com.adaland.springsecurity.model.dto.rent.RentUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface RentMapper {
//    @Mappings({
//            @Mapping(target = "games", source = "games")
//    })
    RentDto fromRentToRentDto(Rent source);

    Rent fromRentCreationDtoToRent(RentCreationDto source);

    Rent fromRentDtoToRent(RentDto destination);

    Rent fromRentUpdateDtoToRent(@MappingTarget Rent entity, RentUpdateDto update);
    Rent fromRentCreationDtoToRent(@MappingTarget Rent entity, RentCreationDto creationDto);
}