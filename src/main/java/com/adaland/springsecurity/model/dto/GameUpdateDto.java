package com.adaland.springsecurity.model.dto;


import com.adaland.springsecurity.model.GameCategory;
import com.adaland.springsecurity.model.GameStatus;
import lombok.Data;

@Data
public class GameUpdateDto {

    private String title;
    private GameStatus status;
    private GameCategory gameCategory;
}
