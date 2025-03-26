package com.example.tomatomall.controller;

import com.example.tomatomall.po.User;
import com.example.tomatomall.service.AccountService;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {


    /**
     * 获取用户详情
     */
    @Autowired
    private AccountService accountService;


    @GetMapping("/{username}")
    public Response getUser(@PathVariable String username) {
        User user = accountService.findByUsername(username);
        if (user != null) {
            return Response.buildSuccess(user);//返回user结构体
        }
        return Response.buildFailure("User not found", "404");
    }

    /**
     * 创建新的用户
     */
    @PostMapping()
    public Response createUser(@RequestParam String username, @RequestParam String password) {//参数并没有设定邮箱之类的，后面看要不要加上

        if (accountService.findByUsername(username) != null) {//保证用户名唯一
            return Response.buildFailure("Username already exists", "409");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User createdUser = accountService.createUser(user);
        return Response.buildSuccess(createdUser);
    }

    /**
     * 更新用户信息
     */
    @PutMapping()
    public Response updateUser(@RequestBody User user) {
        if (user == null || user.getUsername() == null) {
            return Response.buildFailure("Invalid user data", "400");
        }

        User updatedUser = accountService.updateUser(user);
        if (updatedUser != null) {
            return Response.buildSuccess(updatedUser);
        } else {
            return Response.buildFailure("User not found or update failed", "404");
        }
    }
    /**
     * 登录
     */
    @PostMapping("/login")
    public Response login(@RequestParam String username, @RequestParam String password) {
        if (accountService.authenticate(username, password)) {
            User user = accountService.findByUsername(username);
            // 创建一个不包含敏感信息的用户对象
            User safeUser = new User();
            safeUser.setUsername(user.getUsername());
            //下面的尚未实现，应该是默认为空，后面也可以修改user类，强制其不为空
            safeUser.setName(user.getName());
            safeUser.setAvatar(user.getAvatar());
            safeUser.setEmail(user.getEmail());
            safeUser.setLocation(user.getLocation());
            // 修改密码功能暂时不实现

            return Response.buildSuccess(safeUser);
        } else {
            return Response.buildFailure("Invalid username or password", "401");
        }
    }

}
