package com.adaland.springsecurity.model.dto.rent;


import com.adaland.springsecurity.model.dao.Game;
import com.adaland.springsecurity.model.dto.game.GameDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class RentUpdateDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private List<GameDto> games;

}
