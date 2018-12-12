package com.company.immutable;

public class ImmutableArray {
    private  final char [] value = new char[3];


    public void test(){
        value [0] = 3;
        System.out.println(3);
        return;
    }

    public static void main(String[] args) {
        new ImmutableArray().test();
    }

}
