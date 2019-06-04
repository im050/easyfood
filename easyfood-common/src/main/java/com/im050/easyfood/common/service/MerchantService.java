package com.im050.easyfood.common.service;

import com.im050.easyfood.common.entity.Merchant;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linyulin
 * @since 2019-06-01
 */
public interface MerchantService extends IService<Merchant> {
	Merchant findByUsername(String username);
}
