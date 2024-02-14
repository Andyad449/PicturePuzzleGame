package com.yizhuo.utility;
import java.util.Random;
public class CheckCode {
    private CheckCode() {

    }

    static char[] code = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
            'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z'};
    //生成一个验证码，格式：共五位，可以是大写、小写字母或数字。各不相同
    public static String generator() {
        char temp;
        for (int i = 0; i < code.length; i++) {
            Random r = new Random();
            int randNum = r.nextInt(62);
            temp = code[i];
            code[i] = code[randNum];
            code[randNum] = temp;
        }
        Random r =new Random();
        int begin = r.nextInt(58);
        String str = new String(code);
        return str.substring(begin,begin +5);
    }
}