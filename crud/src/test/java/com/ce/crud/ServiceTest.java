package com.ce.crud;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ce.crud.entity.User;
import com.ce.crud.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

/**
 * @author c__e
 * @date Created in 2020/10/13 19:12
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getOne() {
        // throwEx = false 添加 limit 1 超过一条记录时 只警告 不报错
        User one = userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 25), false);
        System.out.println(one);
    }

    @Test
    public void Batch() {
        User user1 = new User().setName("徐丽3").setAge(28);
        User user2 = new User().setName("徐力2").setAge(29).setId(1315977361685966849L);
        boolean saveBatch = userService.saveOrUpdateBatch(Arrays.asList(user1, user2));
        System.out.println(saveBatch);
    }

    @Test
    public void batch() {
        List<User> userList = userService.lambdaQuery().gt(User::getAge, 25).like(User::getName, "雨").list();
        userList.forEach(System.out::println);
    }

    @Test
    public void chain1() {
        boolean update = userService.lambdaUpdate().eq(User::getAge, 25).set(User::getAge, 26).update();
        System.out.println(update);
    }

    @Test
    public void chain2() {
        boolean remove = userService.lambdaUpdate().eq(User::getAge, 24).remove();
        System.out.println(remove);
    }

}
