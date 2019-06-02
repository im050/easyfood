package com.im050.easyfoodapi.entity;

import com.im050.easyfoodcommon.entity.Menu;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class MenuVO extends Menu {

    public List<FoodVO> foods;

}
