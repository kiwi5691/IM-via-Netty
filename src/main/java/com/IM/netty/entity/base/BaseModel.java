package com.IM.netty.entity.base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author kiwi
 * @date 2019/11/24 15:54
 */

@MappedSuperclass
@Data
public class BaseModel implements Serializable {
    @Column(name = "create_time")
    private Date createTime = new Date();

    /**
     * 最近修改时间
     */
    @Column(name = "update_time")
    private Date updateTime = new Date();


}
