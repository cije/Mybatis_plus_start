package com.ce.crud;

import com.ce.crud.dao.UserMapper;
import com.ce.crud.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

/**
 * 插入
 *
 * @author c__e
 * @date Created in 2020/10/12 16:47
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class InsertTest {

    @Autowired
    private UserMapper mapper;

    @Test
    public void insert() {
        User user = new User()
                .setName("向北")
                .setAge(28)
                .setManagerId(1088248166370832385L)
                .setCreateTime(LocalDateTime.now());
        int rows = mapper.insert(user);
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void insert2() {
        User user = new User()
                .setName("向南")
                .setAge(28)
                .setEmail("xn@baogutou.com")
                .setManagerId(1088248166370832385L)
                .setCreateTime(LocalDateTime.now());
        int rows = mapper.insert(user);
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void insert3() {
        User user = new User()
                .setName("向东")
                .setAge(20)
                .setEmail("xd@baogutou.com")
                .setManagerId(1088248166370832385L)
                .setCreateTime(LocalDateTime.now());
        int rows = mapper.insert(user);
        System.out.println("影响记录数：" + rows);
        System.out.println(mapper.selectList(null).get(0));
    }

}

