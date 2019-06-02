package com.im050.easyfoodcommon.entity.view;

import com.im050.easyfoodcommon.entity.Food;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class FoodVO extends Food {
    public List<AttrVO> attrs;
   // private BigDecimal realPrice;

    public BigDecimal getRealPrice() {
        return this.getPrice();
    }

    @Override
    public Integer getMaxStock() {
        return null;
    }
}
