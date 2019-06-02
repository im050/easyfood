package com.im050.easyfoodapi.entity;

import com.im050.easyfoodcommon.entity.Attr;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class AttrVO extends Attr {
    public List<AttrVO> attrs;

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

    public Boolean getSelect() {
        return super.getParentId() <= 0 ? null : false;
    }
}
