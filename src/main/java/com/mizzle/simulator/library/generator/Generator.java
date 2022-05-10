package com.mizzle.simulator.library.generator;

import java.util.Random;

public class Generator {
    public static String randomString(int len) {
        String charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        
        StringBuilder stringBuilder = new StringBuilder(len);
        
        for (int i = 0; i < len; i++){
            stringBuilder.append(charset.charAt(random.nextInt(charset.length())));
        }
        
        return stringBuilder.toString();
    }
}
