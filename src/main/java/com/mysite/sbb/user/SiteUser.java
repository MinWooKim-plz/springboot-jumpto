package com.mysite.sbb.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SiteUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true) // 유의 : 만들어져있던 DB에 대해서 적용 안되니 DROP 후 다시 CREATE
    private String username;

    private String password;

    private String myPassword;

    @Column(unique=true) // 유의 : 만들어져있던 DB에 대해서 적용 안되니 DROP 후 다시 CREATE
    private String email;
}
