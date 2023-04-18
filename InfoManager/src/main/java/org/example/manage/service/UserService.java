package org.example.manage.service;

import org.example.manage.dao.UserDao;
import org.example.manage.entity.User;
import org.example.manage.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //表示服务层
public class UserService {

    @Autowired
    UserDao dao;
    public Result list() {
        List<User> users = dao.selectList(null);
        return Result.ok(200, "查询成功", users);
    }
}
