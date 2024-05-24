package com.adaland.springsecurity.model.dto.game;


import com.adaland.springsecurity.model.dao.GameStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameUpdateDto {

    private String title;
    private GameStatus status;
    private String category;
}
