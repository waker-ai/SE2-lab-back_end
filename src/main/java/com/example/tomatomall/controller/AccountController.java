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
    public Response createUser(@RequestBody User user) {//参数并没有设定邮箱之类的，后面看要不要加上

        if (accountService.findByUsername(user.getUsername()) != null) {//保证用户名唯一
            return Response.buildFailure("Username already exists", "409");
        }

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

            User safeUser = new User();
            safeUser.setUsername(user.getUsername());

            safeUser.setUsername(user.getUsername());
            safeUser.setName(user.getName());
            safeUser.setAvatar(user.getAvatar());
            safeUser.setEmail(user.getEmail());
            safeUser.setLocation(user.getLocation());
            safeUser.setTelephone(user.getTelephone());
            safeUser.setRole(user.getRole());


            return Response.buildSuccess(safeUser);
        } else {
            return Response.buildFailure("Invalid username or password", "401");
        }
    }

}
