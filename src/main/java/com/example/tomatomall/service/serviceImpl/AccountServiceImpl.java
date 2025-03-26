
package com.example.tomatomall.service.serviceImpl;

import com.example.tomatomall.po.User;
import com.example.tomatomall.repository.AccountRepository;
import com.example.tomatomall.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;//加密密码


    @Override
    public User findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));//加密密码
        return accountRepository.save(user);//储存到数据库表中
    }

    @Override
    public User updateUser(User user) {
        User existingUser = accountRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            // 更新用户信息，这个里面写了邮箱之类的更改，前端先确定能够实现用户和密码吧
            existingUser.setName(user.getName());
            existingUser.setAvatar(user.getAvatar());
            existingUser.setTelephone(user.getTelephone());
            existingUser.setEmail(user.getEmail());
            existingUser.setLocation(user.getLocation());
            return accountRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public boolean authenticate(String username, String password) {

        User user = accountRepository.findByUsername(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
}