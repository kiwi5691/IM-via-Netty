package com.IM.netty.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class RandomUtils {
//    public static void main(String[] args) {
//
//        System.out.println(randomString());
//        System.out.println(RandomString.getRandomChar());
//    }

    // use apache common library
    private static String randomString() {
        return RandomStringUtils.random(2, 0x4e00, 0x9fa5, false, false);
    }

    // custom method
    private static class RandomString {
        private static final int BASE_RANDOM = 0x9fa5 - 0x4e00 + 1;
        private static Random random = new Random();

        private static char getRandomChar() {
            return (char) (0x4e00 + random.nextInt(BASE_RANDOM));
        }
    }
}
