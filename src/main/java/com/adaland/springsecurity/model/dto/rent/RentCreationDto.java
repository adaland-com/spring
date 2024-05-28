package com.adaland.springsecurity.model.dto.rent;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class RentCreationDto {

    private LocalDate startDate;
    private String username;
    private List<Long> games;

}
