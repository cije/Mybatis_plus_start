package com.ce.crud;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ce.crud.dao.UserMapper;
import com.ce.crud.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询
 *
 * @author c__e
 * @date Created in 2020/10/12 16:47
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RetrieveTest {

    @Autowired
    private UserMapper mapper;

    @Test
    public void selectById() {
        User user = mapper.selectById(1094590409767661570L);
        System.out.println(user);
    }

    @Test
    public void selectIds() {
        List<Long> ids = Arrays.asList(1087982257332887553L, 1088248166370832385L, 1088250446457389058L);
        List<User> users = mapper.selectBatchIds(ids);
        users.forEach(System.out::println);
    }

    @Test
    public void selectByMap() {
        // {"name":"王天风","age":25
        // where name="王天风" and age=25
        Map<String, Object> columnMap = new HashMap<>();

        // 键值 为 数据库中的 字段名，非 实体类中的 属性名

        columnMap.put("name", "王天风");
        columnMap.put("age", 25);
        List<User> users = mapper.selectByMap(columnMap);
        users.forEach(System.out::println);
    }

    /**
     * 名字包含 “雨” 且 年龄小于 40
     * name like "%雨%" and age <40
     */
    @Test
    public void selectByWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // QueryWrapper<User> query = Wrappers.<User>query();
        queryWrapper.like("name", "雨").lt("age", 40);
        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 名字包含 “雨” 且 年龄大于等于 20 且 小于等于 40 且 email 不为空
     * name like "%雨%" and age between 20 and 40 and email is not null
     */
    @Test
    public void selectByWrapper2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨").between("age", 20, 40).isNotNull("email");
        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 名字为王姓 或者 年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
     * name like "王%" or age >= 25 order by age desc,id asc
     */
    @Test
    public void selectByWrapper3() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王").or().ge("age", 25).orderByDesc("age").orderByAsc("id");
        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 创建日期为2019年2月14日并且直属上级为名字为王姓
     * date_format(create_time,'%Y-%m-%d')='2019-02-14'
     * and manager_id in (select id from user where name like '王%')
     */
    @Test
    public void selectByWrapper4() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d') = {0}", "2019-02-14")
                .inSql("manager_id", "select id from user where name like '王%'");
        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 名字为王姓并且（年龄小于40或邮箱不为空）
     * name like '王%' and (age<40 or email is not null)
     */
    @Test
    public void selectByWrapper5() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王%")
                .and(wq -> wq.eq("age", 40).or().isNotNull("email"));
        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
     * name like '王%' or (age<40 and age>20 and email is not null)
     */
    @Test
    public void selectByWrapper6() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王%")
                .or(qw -> qw.gt("age", 20).lt("age", 40).isNotNull("email"));
        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * （年龄小于40或邮箱不为空）并且名字为王姓
     * (age < 40 or email is not null) and name like "王%"
     */
    @Test
    public void selectByWrapper7() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.nested(qw -> qw.lt("age", 40).or().isNotNull("email"))
                .likeRight("name", "王");
        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 年龄为30、31、34、35
     * age in (30,31,34,35)
     */
    @Test
    public void selectByWrapper8() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", Arrays.asList(30, 31, 34, 35));
        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 只返回满足条件的其中一条语句即可
     * limit 1
     */
    @Test
    public void selectByWrapper9() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", Arrays.asList(30, 31, 34, 35)).last("limit 1");
        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 名字中包含雨并且年龄小于40(需求1加强版)
     * 第一种情况：select id,name
     * from user
     * where name like '%雨%' and age<40
     */
    @Test
    public void selectByWrapperSupper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name").like("name", "雨").lt("age", 40);
        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 第二种情况：select id,name,age,email
     * from user
     * where name like '%雨%' and age<40
     */
    @Test
    public void selectByWrapperSupper2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨").lt("age", 40)
                .select(User.class, info -> !info.getColumn().equals("create_time") && !info.getColumn().equals("manager_id"));
        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * condition 是否加入到sql中
     * false  不加入
     * true 默认加入
     */
    @Test
    public void testCondition() {
        String name = "王";
        String email = "";
        condition(name, email);
    }

    private void condition(String name, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // if (StringUtils.checkValNotNull(name)) {
        //     queryWrapper.like("name", name);
        // }
        // if (StringUtils.checkValNotNull(email)) {
        //     queryWrapper.like("email", email);
        // }
        queryWrapper.like(StringUtils.checkValNull(name), "name", name)
                .like(StringUtils.checkValNull(email), "email", email);
        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 实体作为条件构造器构造方法的参数
     */
    @Test
    public void selectByWrapperEntity() {
        User whereUser = new User()
                // .setName("刘红雨")
                .setAge(40);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(whereUser);
        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * allEq()
     */
    @Test
    public void selectByWrapperAllEq() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("name", "王天风");
        params.put("age", null);
        // false 忽略 null   true   is null
        // queryWrapper.allEq(params, false);

        // 过滤字段 “name"
        queryWrapper.allEq((k, v) -> !k.equals("name"), params);

        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * selectMaps 返回 map
     */
    @Test
    public void selectByWrapperMaps() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨").lt("age", 40)
                .select("id", "name");
        List<Map<String, Object>> users = mapper.selectMaps(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。
     * 并且只取年龄总和小于500的组。
     * select avg(age) avg_age,min(age) min_age,max(age) max_age
     * from user
     * group by manager_id
     * having sum(age) <500
     */
    @Test
    public void selectByWrapperMaps2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("avg(id) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id").having("sum(age) < {0}", 500);
        List<Map<String, Object>> users = mapper.selectMaps(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * selectObjs 只返回第一个字段
     */
    @Test
    public void selectByWrapperObjs() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name")
                .like("name", "雨").lt("age", 40);
        List<Object> users = mapper.selectObjs(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * selectCount  返回总记录数
     */
    @Test
    public void selectByWrapperCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨").lt("age", 40);
        Integer count = mapper.selectCount(queryWrapper);
        System.out.println(count);
    }

    /**
     * selectOne 返回一个
     * 注意：预计结果只能小于等于 1    超过1 报错
     */
    @Test
    public void selectByWrapperOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "刘红雨").lt("age", 40);
        User user = mapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    /**
     * lambda 条件构造器
     */
    @Test
    public void selectLambda() {
        // LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
        // LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.<User>lambdaQuery();
        userLambdaQueryWrapper.like(User::getName, "雨").lt(User::getAge, 40);
        List<User> users = mapper.selectList(userLambdaQueryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 名字为王姓 或者（年龄小于40并且年龄大于20并且邮箱不为空）
     * name like '王%' or (age<40 and age>20 and email is not null)
     */
    @Test
    public void selectLambda2() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.<User>lambdaQuery();

        lambdaQueryWrapper.likeRight(User::getName, "王")
                .or(qw -> qw.lt(User::getAge, 40).gt(User::getAge, 20).isNotNull(User::getEmail));

        List<User> users = mapper.selectList(lambdaQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void selectLambda3() {
        List<User> userList = new LambdaQueryChainWrapper<User>(mapper)
                .like(User::getName, "雨").ge(User::getAge, 20).list();
        userList.forEach(System.out::println);
    }

    /**
     * 自定义sql方法
     */
    @Test
    public void selectMy() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.<User>lambdaQuery();

        lambdaQueryWrapper.likeRight(User::getName, "王")
                .or(qw -> qw.lt(User::getAge, 40).gt(User::getAge, 20).isNotNull(User::getEmail));

        List<User> users = mapper.selectAll(lambdaQueryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 分页
     */
    @Test
    public void selectPage() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.gt(User::getAge, 26);

        // Page<User> page = new Page<>(1, 2);
        // IPage<User> iPage = mapper.selectPage(page, lambdaQueryWrapper);
        // System.out.println("总页数：" + iPage.getPages());
        // System.out.println("总记录数：" + iPage.getTotal());
        // List<User> userList = iPage.getRecords();

        // 默认 isSearchCount = true  表示查询 记录数    false 不查询记录数
        IPage<Map<String, Object>> page = new Page<>(1, 2);
        // IPage<Map<String, Object>> page = new Page<>(1, 2, false);

        IPage<Map<String, Object>> iPage = mapper.selectMapsPage(page, lambdaQueryWrapper);
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("总记录数：" + iPage.getTotal());
        List<Map<String, Object>> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }

    @Test
    public void selectMyPage() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.gt(User::getAge, 26);

        Page<User> page = new Page<>(1, 2);
        IPage<User> iPage = mapper.selectUserPage(page, lambdaQueryWrapper);
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("总记录数：" + iPage.getTotal());
        List<User> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }
}

