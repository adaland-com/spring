package com.adaland.springsecurity.model.dto;


import com.adaland.springsecurity.model.GameStatus;
import lombok.Data;

@Data
public class GameDto {


    private long id;
    private String title;
    private GameStatus status;
}
