package com.adaland.springsecurity.model.dto.rent;


import com.adaland.springsecurity.model.dao.Game;
import com.adaland.springsecurity.model.dto.game.GameDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class RentDto {


    private BigDecimal cost;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<GameDto> games;
    private boolean isActive = true;
    private boolean isSettled = false;

}
