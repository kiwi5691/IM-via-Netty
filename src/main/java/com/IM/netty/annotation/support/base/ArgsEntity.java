package com.IM.netty.annotation.support.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArgsEntity {
    private Integer userId;
    private Integer fid;
}
