package com.im050.easyfoodapi.service.impl;

import com.im050.easyfoodcommon.entity.Menu;
import com.im050.easyfoodapi.dao.MenuDao;
import com.im050.easyfoodapi.service.MenuService;
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
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {
	
}
