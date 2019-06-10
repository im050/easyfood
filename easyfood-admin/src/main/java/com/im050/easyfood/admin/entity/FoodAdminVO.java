package com.im050.easyfood.admin.entity;

import com.im050.easyfood.common.entity.Food;
import com.im050.easyfood.common.entity.view.AttrVO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class FoodAdminVO extends Food {
    private List<AttrVO> attrs;
}
