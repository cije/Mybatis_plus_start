package com.ce.crud;

import com.ce.crud.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;


/**
 * ActiveRecord模式
 * 实体类 继承 Model<>
 *
 * @author c__e
 * @date Created in 2020/10/12 16:47
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ARTest {

    @Test
    public void insert() {
        User user = new User()
                .setName("刘华")
                .setAge(29)
                .setEmail("lh@baomidou.com")
                .setManagerId(1088248166370832385L)
                .setCreateTime(LocalDateTime.now());
        boolean insert = user.insert();
        System.out.println(insert);
    }

    @Test
    public void selectById() {
        User user = new User();
        User userSelect = user.selectById(1315916435779686402L);
        System.out.println(userSelect == user);
        System.out.println(userSelect);
    }

    @Test
    public void selectById2() {
        User user = new User().setId(1315916435779686402L);
        User userSelect = user.selectById();
        System.out.println(userSelect == user);
        System.out.println(userSelect);
    }

    @Test
    public void updateById() {
        User user = new User().setId(1315916435779686402L).setName("刘❀");
        boolean update = user.updateById();
        System.out.println(update);
    }

    @Test
    public void deleteById() {
        User user = new User().setId(1315916435779686402L);
        boolean delete = user.deleteById();
        System.out.println(delete);
    }

    @Test
    public void insertOrUpdate() {
        User user = new User()
                .setId(1315919718044897282L)
                .setName("张强强")
                .setAge(24)
                .setEmail("zq@baomidou.com")
                .setManagerId(1088248166370832385L)
                .setCreateTime(LocalDateTime.now());
        boolean insertOrUpdate = user.insertOrUpdate();
        System.out.println(insertOrUpdate);
    }
}

