package com.im050.easyfood.common.entity.view;

import com.im050.easyfood.common.entity.Attr;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class AttrVO extends Attr {

    @Override
    public BigDecimal getMarkup() {
        return super.getParentId() > 0 ? super.getMarkup() : null;
    }

    @Override
    public Boolean getRequired() {
        return super.getParentId() <= 0 ? super.getRequired() : null;
    }

    @Override
    public Boolean getMultiple() {
        return super.getParentId() <= 0 ? super.getMultiple() : null;
    }

    /**
     * 给前端判断用的参数
     * @return
     */
    public Boolean getSelected() {
        return super.getParentId() <= 0 ? null : false;
    }
}
