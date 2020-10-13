package com.ce.crud;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 删除
 *
 * @author c__e
 * @date Created in 2020/10/12 16:47
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DeleteTest {

    @Autowired
    private UserMapper mapper;

    @Test
    public void deleteById() {
        int rows = mapper.deleteById(1315610749141196801L);
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void deleteByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "向南");
        map.put("age", 28);
        int rows = mapper.deleteByMap(map);
        System.out.println("影响记录数：" + rows);
    }

    /**
     * 根据id批量删除
     */
    @Test
    public void deleteBatchIds() {
        int rows = mapper.deleteBatchIds(Arrays.asList(1315597544419409922L, 1315606255221272577L));
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void deleteByWrapper() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.<User>lambdaQuery();
        lambdaQueryWrapper.eq(User::getAge, 32);
        int rows = mapper.delete(lambdaQueryWrapper);
        System.out.println("影响记录数：" + rows);
    }
}

