package com.ce.crud.entity;


import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author c__e
 * @date Created in 2020/10/12 16:05
 */
@TableName("user")
public class User {
    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 姓名
     */
    @TableField("name")
    private String name;
    /**
     * 年龄
     */
    // @TableField(condition = SqlCondition.EQUAL)
    // @TableField(condition = "%s &lt; #{%s} ")
    private Integer age;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 直属上级
     */
    private Long managerId;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    /**
     * 备注
     * 1、用transient给属性做声明，该方式无序列化
     * <p>
     * eg:private transient int flag;
     * <p>
     * 2、用static给属性做声明，默认不给静态变量生成get/set方法，所以这时需要手动写get/set方法，全类唯一一个的属性。
     * <p>
     * 3、@TableField(exist=false)
     * <p>
     * 给属性添加该注解，表示该字段在数据库中不存在。默认exist=true。
     */
    private transient String remark;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public Long getManagerId() {
        return managerId;
    }

    public User setManagerId(Long managerId) {
        this.managerId = managerId;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public User setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public User setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", managerId=" + managerId +
                ", createTime=" + createTime +
                '}';
    }
}


