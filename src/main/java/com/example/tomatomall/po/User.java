package com.example.tomatomall.po;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    //现在让用户名和密码不为空
    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 50)
    private String name;

    @Column(length = 255)
    private String avatar;

    @Column(length =50 )
    private String role;

    @Column(length = 11)
    private String telephone;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String location;
}