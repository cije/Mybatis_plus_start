# Mybatis Plus入门

>[Mybatis Plus](https://mybatis.plus/) 
>
>[慕课@老猿_MyBatis-Plus入门_](https://www.imooc.com/learn/1130)

## 快速入门

### 简介

​	[MyBatis-Plus](https://github.com/baomidou/mybatis-plus)（简称 MP）是一个 [MyBatis](http://www.mybatis.org/mybatis-3/) 的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

### 快速开始

导入依赖：

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.0</version>
</dependency>
```

在 Spring Boot 启动类中添加 `@MapperScan` 注解，扫描 Mapper 文件夹。

```java
@SpringBootApplication
@MapperScan(basePackages = "com.ce.first.dao")
public class FirstApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstApplication.class, args);
    }

}
```

数据库准备

 [SQL文件](https://github.com/cije/Mybatis_plus_start/blob/master/sql.sql)

实体类 [User.java](https://github.com/cije/Mybatis_plus_start/blob/master/crud/src/main/java/com/ce/crud/entity/User.java)



## CRUD接口

### Mapper CRUD接口

编写Mapper类 [UserMapper.java](https://github.com/cije/Mybatis_plus_start/blob/master/crud/src/main/java/com/ce/crud/dao/UserMapper.java) 继承 `BaseMapper<>`

```
// import 省略

/**
 * @author c__e
 * @date Created in 2020/10/12 16:08
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
	// 自定义Mapper
    List<User> selectAll(@Param(Constants.WRAPPER) Wrapper<User> wrapper);

    IPage<User> selectUserPage(IPage<User> page, @Param(Constants.WRAPPER) Wrapper<User> wrapper);
}
```

对应 [UserMapper.xml](https://github.com/cije/Mybatis_plus_start/blob/master/crud/src/main/resources/mapper/UserMapper.xml)

-   测试
    -   insert

        [InsertTest.java](https://github.com/cije/Mybatis_plus_start/blob/master/crud/src/test/java/com/ce/crud/InsertTest.java)

    -   update

        [UpdateTest.java](https://github.com/cije/Mybatis_plus_start/blob/master/crud/src/test/java/com/ce/crud/UpdateTest.java)

    -   delete

        [DeleteTest.java](https://github.com/cije/Mybatis_plus_start/blob/master/crud/src/test/java/com/ce/crud/DeleteTest.java)

    -   select

        [RetrieveTest.java](https://github.com/cije/Mybatis_plus_start/blob/master/crud/src/test/java/com/ce/crud/RetrieveTest.java)

### Service CRUD接口

[UserService.java](https://github.com/cije/Mybatis_plus_start/blob/master/crud/src/main/java/com/ce/crud/service/UserService.java):

```java
public interface UserService extends IService<User> {
}
```

[UserServiceImpl.java](https://github.com/cije/Mybatis_plus_start/blob/master/crud/src/main/java/com/ce/crud/service/impl/UserServiceImpl.java):

```java
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
```

-   测试
    -    [ServiceTest.java](https://github.com/cije/Mybatis_plus_start/blob/master/crud/src/test/java/com/ce/crud/ServiceTest.java)

### AR(ActiveRecord)模式

-   实体类 User继承 Model类

    -   ```java
        public class User extends Model<User>
        ```

-   测试：

    -   [ARTest.java](https://github.com/cije/Mybatis_plus_start/blob/master/crud/src/test/java/com/ce/crud/ARTest.java)

## 其他

### 配置分页插件

-   [MybatisPlusConfig.java](https://github.com/cije/Mybatis_plus_start/blob/master/crud/src/main/java/com/ce/crud/config/MybatisPlusConfig.java)

    ```java
    @Configuration
    public class MybatisPlusConfig {
    
        @Bean
        public MybatisPlusInterceptor mybatisPlusInterceptor() {
            MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
            // 添加分页插件
            mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
            return mybatisPlusInterceptor;
        }
    
        @Bean
        public ConfigurationCustomizer configurationCustomizer() {
            return configuration -> configuration.setUseDeprecatedExecutor(false);
        }
    }
    ```

### 注解

-   `@TableName("user")` ：实体类对应数据库表名
-   `@TableId(value,type)`：主键
    -   `value`：字段名
    -   `type`：主键策略
-   `@TableField("name")`
    -   `condition`：字段 where 实体查询比较条件
    -   `value`：数据库字段值

### 配置

[Application.yml](https://github.com/cije/Mybatis_plus_start/blob/master/crud/src/main/resources/application.yml)

```yml
mybatis-plus:
  mapper-locations:
    - mapper/*
  global-config:
    db-config:
	  table-prefix: mp_ 表名前缀
	  # 全局 主键策略
      id-type: none
  config-location: classpath:mybatis-plus.xml
  type-aliases-package: com.ce.crud.entity
  configuration:
    map-underscore-to-camel-case: true  # 默认 true
```

