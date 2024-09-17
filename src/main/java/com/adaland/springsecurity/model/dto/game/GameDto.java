package com.adaland.springsecurity.model.dto.game;


import com.adaland.springsecurity.model.dao.GameCategory;
import com.adaland.springsecurity.model.dao.GameStatus;
import com.adaland.springsecurity.model.dto.gameCategory.GameCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GameDto {


    private long id;
    private String title;
    private GameStatus status;
    private GameCategoryDto gameCategory;
}
