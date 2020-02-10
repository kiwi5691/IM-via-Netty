package com.IM.netty.utils;

import java.util.Random;

public class RandomNameUtil {

    private static Random ran = new Random();
    private final static int delta = 0x9fa5 - 0x4e00 + 1;

    public static char getName(){
        return (char)(0x4e00 + ran.nextInt(delta));
    }
}