package com.ce.crud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ce.crud.dao.UserMapper;
import com.ce.crud.entity.User;
import com.ce.crud.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author c__e
 * @date Created in 2020/10/13 19:10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
