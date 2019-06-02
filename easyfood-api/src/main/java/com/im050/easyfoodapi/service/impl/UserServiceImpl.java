package com.im050.easyfoodapi.service.impl;

import com.im050.easyfoodcommon.entity.User;
import com.im050.easyfoodapi.dao.UserDao;
import com.im050.easyfoodapi.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
