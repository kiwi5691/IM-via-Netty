package com.IM.netty.utils;

import com.IM.netty.enums.MsgSignFlagEnum;
import com.IM.netty.enums.MsgTypeEnum;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class TypeChecksUtils {

    public  static  Set<String> imgs = new HashSet<>();
    static {
        imgs.add(".image");
        imgs.add(".png");
        imgs.add(".jpg");
        imgs.add(".tif");
        imgs.add(".bmp");
        imgs.add(".jpeg");
    }

    public static boolean isImage(String file){
        for (String img: imgs) {
            if(file.contains(img)){
                return true;
            }
        }
        return false;
    }

    public static String returnType(String file){
        //这里未包含音频。检测需要更明确，对media类型进行下载，然后再用File判断
        if(isImage(file)){
            return MsgTypeEnum.images.content;
        }else{
            return MsgTypeEnum.text.content;
        }

    }
    public static boolean isImage(File file){
        MimetypesFileTypeMap mtftp;
        mtftp = new MimetypesFileTypeMap();
        mtftp.addMimeTypes("image png tif jpg jpeg bmp");
        String mimetype= mtftp.getContentType(file);
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }

}
