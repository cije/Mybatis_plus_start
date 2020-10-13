package com.ce.first;

import com.ce.first.dao.UserMapper;
import com.ce.first.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FirstApplicationTests {
    @Autowired
    private UserMapper mapper;

    @Test
    public void select() {
        List<User> users = mapper.selectList(null);
        Assertions.assertEquals(5, users.size());
        users.forEach(System.out::println);
    }
}
