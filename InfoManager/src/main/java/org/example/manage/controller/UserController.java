package org.example.manage.controller;



import org.example.manage.service.UserService;
import org.example.manage.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")

public class UserController {
    @Autowired
    UserService service;
    @GetMapping("/list")
    public Result list() {
        try {
            return service.list();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(500, "运行异常",e.getMessage());
        }
    }

    @GetMapping("/sendCode")
    public Result sendEmail(String userEmail) {
        try {
            return service.sendCode(userEmail);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(500, "发生异常", e.getMessage());
        }
    }
}
