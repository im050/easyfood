package com.im050.easyfoodcommon.service.impl;

import com.im050.easyfoodcommon.entity.Food;
import com.im050.easyfoodcommon.dao.FoodDao;
import com.im050.easyfoodcommon.service.FoodService;
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
public class FoodServiceImpl extends ServiceImpl<FoodDao, Food> implements FoodService {
	
}
