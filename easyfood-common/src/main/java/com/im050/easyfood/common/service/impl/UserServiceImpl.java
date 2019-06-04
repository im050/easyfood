package com.im050.easyfood.common.service.impl;

import com.im050.easyfood.common.entity.User;
import com.im050.easyfood.common.dao.UserDao;
import com.im050.easyfood.common.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linyulin
 * @since 2019-05-31
 */
@Service
public class UserServiceImpl extends SuperServiceImpl<UserDao, User> implements UserService {
	
}
