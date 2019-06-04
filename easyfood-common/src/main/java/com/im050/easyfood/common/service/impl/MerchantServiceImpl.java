package com.im050.easyfood.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.im050.easyfood.common.constant.ColumnConstants;
import com.im050.easyfood.common.entity.Merchant;
import com.im050.easyfood.common.dao.MerchantDao;
import com.im050.easyfood.common.service.MerchantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linyulin
 * @since 2019-06-01
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantDao, Merchant> implements MerchantService {
    @Override
    public Merchant findByUsername(String username) {
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ColumnConstants.USERNAME, username);
        return getOne(queryWrapper);
    }
}
