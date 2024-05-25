package com.adaland.springsecurity.mapper;

import com.adaland.springsecurity.model.dao.Rent;
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
    @Mappings({
            @Mapping(target = "games", source = "games")
    })
    RentDto fromRentToRentDto(Rent source);

    @Mapping(target = "games", source = "games")
    Rent fromRentDtoToRent(RentDto source);

    Rent updateFromRentUpdateDtoToRent(@MappingTarget Rent entity, RentUpdateDto update);


}