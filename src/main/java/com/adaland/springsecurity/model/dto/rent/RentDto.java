package com.adaland.springsecurity.model.dto.rent;


import com.adaland.springsecurity.model.dto.UserDto;
import com.adaland.springsecurity.model.dto.game.GameDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RentDto {


    private final UserDto user;
    private final List<GameDto> games;
    private BigDecimal cost;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive = true;
    private boolean isSettled = false;

}
