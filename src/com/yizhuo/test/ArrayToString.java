package com.yizhuo.test;

public class ArrayToString {
    private ArrayToString(){

    }
    public static void main(String[] args) {
        char[] charArr = new char[]{'1','2','3'};
        String strCharArr = new String(charArr);
        System.out.println(strCharArr);
    }
}
