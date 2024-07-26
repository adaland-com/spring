package com.adaland.springsecurity.model.dto.gameCategory;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GameCategoryDto {

    private long id;
    private final String name;


}
