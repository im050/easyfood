package com.im050.easyfood.common.entity.view;

import com.im050.easyfood.common.entity.Menu;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class MenuVO extends Menu {

    public List<FoodVO> foods;

}
