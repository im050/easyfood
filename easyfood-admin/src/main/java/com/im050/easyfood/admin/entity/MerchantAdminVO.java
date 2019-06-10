package com.im050.easyfood.admin.entity;

import com.im050.easyfood.common.entity.Merchant;
import com.im050.easyfood.common.entity.Shop;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class MerchantAdminVO extends Merchant {
    private List<Shop> shops;

    @Override
    public String getPassword() {
        return null;
    }
}
