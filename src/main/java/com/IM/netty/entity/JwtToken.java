package com.IM.netty.entity;

import com.IM.netty.entity.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author kiwi
 * @date 2019/11/24 11:24
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "jwt_token")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtToken extends BaseModel {

    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "user_id")
    private Integer userId;

    @Basic
    @Column(name = "token")
    private String token;

    @Basic
    @Column(name = "extend")
    private String extend;

}
