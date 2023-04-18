package org.example.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.manage.entity.User;
import org.springframework.stereotype.Repository;

@Repository//表示dao层
public interface UserDao extends BaseMapper<User> {//使用API实现增删查改（仅限单表查询）
}
