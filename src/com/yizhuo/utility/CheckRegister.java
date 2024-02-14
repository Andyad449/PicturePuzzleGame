package com.yizhuo.utility;

public class CheckRegister {
    private CheckRegister() {

    }

    //判断注册输入的用户名是否符合要求：3~10个字符，不能以数字开头，只能是字母和数字的组合
    public static boolean checkUsername(String username) {
        char[] temp = username.toCharArray();
        if (username.length() > 10 || username.length() < 3) {
            return false;
        }
        if (temp[0] >= '0' && temp[0] <= '9') {
            return false;
        }
        for (int i = 0; i < temp.length; i++) {
            if (!(temp[i] >= 48 && temp[i] <= 57) && !(temp[i] >= 65 && temp[i] <= 90) && !(temp[i] >= 97 && temp[i] <= 122)) {
                return false;
            }
        }
        return true;
    }
    //判断用户输入的密码是否合法：6~20位，不能是纯数字
    public static boolean checkPassword(char[] password){
        int length = password.length;
        int numCount = 0;
        if(length<6||length>20){
            return false;
        }
        for (int i = 0; i < password.length; i++) {
            if(password[i]>='0'&&password[i]<='9'){
                numCount++;
            }
        }
        return numCount != length;
    }
}
