package com.company.cache;

public class LongCache {
    public static void main(String[] args) {
        long instanceStart = System.nanoTime();
        for(int i = -128  ; i < 127; i++){
            new Long(300);
        }
        long instanceEnd = System.nanoTime();
        System.out.println("instance long 消耗时间" + ( instanceEnd - instanceStart ) + "ms");


        long cacheStart = System.nanoTime();
        for(int i = -128  ; i < 127; i++){
            Long.valueOf(i);
        }
        long cacheEnd = System.nanoTime();
        System.out.println("cache long 消耗时间" + ( cacheEnd - cacheStart ) + "ms");
    }
}
