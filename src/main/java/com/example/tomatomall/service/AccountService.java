package com.example.tomatomall.service;

import com.example.tomatomall.po.Account;

public interface AccountService {
    //用户名查询用户
    Account findByUsername(String username);

    //完成注册
    void register(String username, String password);
}
