package com.IM.netty.utils;

import com.IM.netty.entity.UserMsg;
import com.IM.netty.model.dto.WeChatMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 76905
 */
@Slf4j
public class DtoUtils {
    /**
     * 对象属性拷贝 <br>
     * 将源对象的属性拷贝到目标对象
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        try {
            BeanUtils.copyProperties(source,target);
        } catch (Exception e) {
            log.info("BeanUtil property copy failed:Exception");
        }
    }

    /**
     * @param input 输入集合
     * @param clzz  输出集合类型
     * @param <E>   输入集合类型
     * @param <T>   输出集合类型
     * @return 返回集合
     */
    public static <E, T> List<T> convertList2List(List<E> input, Class<T> clzz) {
        List<T> output = new ArrayList<>();
        if (!CollectionUtils.isEmpty(input)) {
            for (E source : input) {
                T target = BeanUtils.instantiateClass(clzz);
                BeanUtils.copyProperties(source,target);
                output.add(target);
            }
        }
        return output;
    }

    public static List<WeChatMsg> copyUseMsg(Optional<List<UserMsg>> userMsgList){
        List<WeChatMsg> weChatMsgs = new ArrayList<>();
        if(userMsgList.isPresent()){
            for (UserMsg userMsg: userMsgList.get()) {
                WeChatMsg weChatMsg = new WeChatMsg(userMsg);
                weChatMsgs.add(weChatMsg);
            }
        }
        return weChatMsgs;
    }
}
