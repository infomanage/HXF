package org.example.manage.service;

import org.example.manage.controller.UserController;
import org.example.manage.dao.UserDao;
import org.example.manage.entity.User;
import org.example.manage.utils.Email;
import org.example.manage.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service //表示服务层
public class UserService {

    @Autowired
    UserDao dao;
    @Autowired
    Email email;
    public Result list() {
        List<User> users = dao.selectList(null);
        return Result.ok(200, "查询成功", users);
    }

    //发送邮件
    public Result sendCode(String userEmail) {
        //生成验证码
        String code = "";
        for (int i = 1; i <= 6; i++) {
            code += new Random().nextInt(10);//[0,10)
        }
        boolean result = email.sendEmail(userEmail, "验证码", "你的验证码是:" + code);
        return result ? Result.ok("发送成功") : Result.error("发送失败");
    }
}
