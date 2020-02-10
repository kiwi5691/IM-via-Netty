package com.IM.netty.entity;

import com.IM.netty.entity.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_msg")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserMsg extends BaseModel implements Serializable  {

    private static final long serialVersionUID = 4133316147283239759L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowf-id")
    @GenericGenerator(name = "snowf-id", strategy = "com.IM.netty.utils.SnowflakeIDGenerator")
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "send_id")
    private Integer sendId;

    @Basic
    @Column(name = "accept_id")
    private Integer acceptId;

    @Basic
    @Column(name = "msg")
    private Integer msg;

    @Basic
    @Column(name = "is_sign")
    private Integer isSign;






}