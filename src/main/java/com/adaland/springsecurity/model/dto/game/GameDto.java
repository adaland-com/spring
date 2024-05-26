package com.adaland.springsecurity.model.dto.game;


import com.adaland.springsecurity.model.dao.GameCategory;
import com.adaland.springsecurity.model.dao.GameStatus;
import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryDto;
import lombok.Data;

@Data
public class GameDto {


    private long id;
    private String title;
    private GameStatus status;
    private GameCategoryDto gameCategory;
}
