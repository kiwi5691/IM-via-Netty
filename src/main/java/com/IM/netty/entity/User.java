package com.IM.netty.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Entity
@Table(name = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;


    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "gender")
    private Byte gender;

    @Basic
    @Column(name = "birthday")
    private Date birthday;

    @Basic
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Basic
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Basic
    @Column(name = "nickname")
    private String nickname;

    @Basic
    @Column(name = "mobile")
    private String mobile;

    @Basic
    @Column(name = "avatar")
    private String avatar;

    @Basic
    @Column(name = "weixin_openid")
    private String weixinOpenid;

    @Basic
    @Column(name = "session_key")
    private String sessionKey;

    @Basic
    @Column(name = "status")
    private Byte status;

    @Basic
    @Column(name = "add_time")
    private Date addTime;

    @Basic
    @Column(name = "update_time")
    private Date updateTime;

    @Basic
    @Column(name = "deleted")
    private Boolean deleted;

}