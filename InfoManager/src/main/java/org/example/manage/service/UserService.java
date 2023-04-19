package org.example.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import org.example.manage.dao.UserDao;
import org.example.manage.entity.User;
import org.example.manage.utils.Email;
import org.example.manage.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service //表示服务层
public class UserService {

    @Autowired
    UserDao dao;
    @Autowired
    Email email;

    @Autowired
    RedisTemplate redisTemplate;
    public Result list() {
        List<User> users = dao.selectList(null);
        return Result.ok(200, "查询成功", users);
    }

    //发送邮件
    public Result sendCode(String userEmail) {
        //判断邮箱是否注册
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_email", userEmail);
        User user = dao.selectOne(wrapper);
        if (!ObjectUtils.isNull(user)) {
            return Result.error("该邮箱已被注册");
        }
        //生成验证码
        String code = "";
        for (int i = 1; i <= 6; i++) {
            code += new Random().nextInt(10);//[0,10)
        }
        boolean result = email.sendEmail(userEmail, "验证码", "你的验证码是:" + code);
        //将验证码存储到Redis中
        redisTemplate.opsForValue().set("code", code, 10, TimeUnit.SECONDS);

        return result ? Result.ok("发送成功") : Result.error("发送失败");
    }
        //注册
        @Transactional(rollbackFor = Exception.class)   //表示发生异常时，回滚事务。
        public Result register(User user) {

            //1.判断验证码是否正确       转类型方法1：拼接.get("code")+
            String code = redisTemplate.opsForValue().get("code").toString();//从redis中取出验证码
            if (user.getCheckCode().equals(code)) {
                return Result.error("验证码输入错误！");
            }

            //2.验证码正确，进行注册
            dao.insert(user);
            return Result.ok("注册成功！");

    }


}
