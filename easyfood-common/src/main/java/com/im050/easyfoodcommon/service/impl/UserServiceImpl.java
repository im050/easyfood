package com.im050.easyfoodcommon.service.impl;

import com.im050.easyfoodcommon.entity.User;
import com.im050.easyfoodcommon.dao.UserDao;
import com.im050.easyfoodcommon.service.UserService;
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
