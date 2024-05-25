package com.adaland.springsecurity.model.dto.rent;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class RentUpdateDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isSettled;
    private boolean isActive;

}
