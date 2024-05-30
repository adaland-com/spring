package com.adaland.springsecurity.model.dto.gameCategory;

import com.adaland.springsecurity.model.dao.GameCategory;
import lombok.Data;


@Data
public class GameCategoryDto {

    private long id;
    private final String name;


}
