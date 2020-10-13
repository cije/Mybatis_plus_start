package com.ce.crud;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.ce.crud.dao.UserMapper;
import com.ce.crud.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

/**
 * 更新
 *
 * @author c__e
 * @date Created in 2020/10/12 16:47
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UpdateTest {

    @Autowired
    private UserMapper mapper;

    /**
     * updateById
     */
    @Test
    public void updateById() {
        User user = new User().setId(1088248166370832385L).setEmail("wtf2@baomidou.com");
        int rows = mapper.updateById(user);
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void updateByWrapper() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", "李艺伟").eq("age", 28);
        User user = new User().setEmail("lyw2020@baomidou.com").setAge(29);
        int rows = mapper.update(user, updateWrapper);
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void updateByWrapper2() {
        User whereUser = new User().setName("李艺伟");
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>(whereUser);
        updateWrapper.eq("age", 29);
        User user = new User().setEmail("lyw2019@baomidou.com").setAge(28);
        int rows = mapper.update(user, updateWrapper);
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void updateByWrapper3() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", "李艺伟").eq("age", 28).set("age", 30);

        int rows = mapper.update(null, updateWrapper);
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void updateByWrapperLambda() {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper.eq(User::getName, "李艺伟").eq(User::getAge, 30).set(User::getAge, 31);
        int rows = mapper.update(null, lambdaUpdateWrapper);
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void updateByWrapperLambdaChain() {
        boolean update = new LambdaUpdateChainWrapper<User>(mapper)
                .eq(User::getName, "李艺伟").eq(User::getAge, 31).set(User::getAge, 32).update();
        System.out.println("更新是否成功：" + update);
    }
}

