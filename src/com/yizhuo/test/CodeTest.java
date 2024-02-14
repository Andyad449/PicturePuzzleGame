package com.yizhuo.test;

import com.yizhuo.utility.CheckCode;

import java.util.Random;

public class CodeTest {
    public static void main(String[] args) {
//        for(int i = 48;i<= 122;i++){
//            System.out.print("'"+(char) i + "'" +",");
//        }
        Random r = new Random();
        int a = r.nextInt(10);
        int b = r.nextInt(10);
        System.out.println("a = "+a+",b = "+b);

        String str1 = "Hello world";
        System.out.println(str1.substring(8,11));
        System.out.println("********************");
        String checkCode = CheckCode.generator();
        System.out.println(checkCode);
    }
}
