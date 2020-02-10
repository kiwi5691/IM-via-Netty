package com.IM.netty.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString
@Entity
@Table(name = "user_groups")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGroups implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;


    @Basic
    @Column(name = "user_id")
    private Integer userId;

    @Basic
    @Column(name = "friend_id")
    private Integer friendId;


}