package com.example.tomatomall.controller;

import com.example.tomatomall.po.User;
import com.example.tomatomall.service.AccountService;
import com.example.tomatomall.util.JwtUtil;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
            user.setPassword(null); // 不返回密码
            return Response.buildSuccess(user);
        }
        return Response.buildFailure("User not found", "404");
    }

    /**
     * 创建新的用户
     */
    @PostMapping()
    public Response createUser(@RequestParam String username, @RequestParam String password) {

        //参数并没有设定邮箱之类的，后面看要不要加上
        if (accountService.findByUsername(username) != null) {
            return Response.buildFailure("Username already exists", "409");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User createdUser = accountService.createUser(user);
        createdUser.setPassword(null); // 不返回密码
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
            updatedUser.setPassword(null); // 不返回密码
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
            safeUser.setName(user.getName());
            safeUser.setAvatar(user.getAvatar());
            safeUser.setEmail(user.getEmail());
            safeUser.setLocation(user.getLocation());
            // 不设置密码字段

            return Response.buildSuccess(safeUser);
        } else {
            return Response.buildFailure("Invalid username or password", "401");
        }
    }

}
