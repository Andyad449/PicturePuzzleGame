package com.yizhuo.ui;

import com.yizhuo.utility.CheckCode;
import com.yizhuo.utility.Database;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginJFrame extends JFrame implements MouseListener {

    private static LoginJFrame mainFrame;

    String path = "image\\login\\";

    //登录按钮
   ImageIcon loginIcon =  new ImageIcon(path + "login.png");
    JLabel login = new JLabel(loginIcon);
    //注册按钮
    ImageIcon registerIcon = new ImageIcon(path + "register.png");
    JLabel register = new JLabel(registerIcon);
    //显示密码按钮（图片）
    ImageIcon revealIcon = new ImageIcon(path + "reveal.png");
    JLabel reveal = new JLabel(revealIcon);
    //用户名的文本输入框
    JTextField userInput = new JTextField(20);
    //获取用户名
    public static String username;
    //密码的密文输入框
    JPasswordField passInput = new JPasswordField(20);
    //验证码的文本输入框
    JTextField codeInput = new JTextField(10);
    //管理随机生成的验证码
    String generatedCode = CheckCode.generator();
    JLabel code = new JLabel(generatedCode);
    //登录成功后的JDialog
    JDialog checkPassed = new JDialog();

    public LoginJFrame() {
        Database.createTableIfNotExists();
        mainFrame= this;
        initJFrame();
        initImage();
        this.setVisible(true);

    }

    private void initImage() {
        //用户名
        JLabel username = new JLabel(new ImageIcon(path + "username.png"));
        username.setBounds(116,135,51,19);
        this.getContentPane().add(username);

        userInput.setBounds(195,135,200,25);
        this.getContentPane().add(userInput);
        //密码
        JLabel password = new JLabel(new ImageIcon(path + "password.png"));
        password.setBounds(130,190,35,18);
        this.getContentPane().add(password);

        passInput.setBounds(195,185,200,25);
        this.getContentPane().add(passInput);

        //验证码
        JLabel checkCode = new JLabel(new ImageIcon(path + "checkCode.png"));
        checkCode.setBounds(115,238,56,21);
        this.getContentPane().add(checkCode);

        codeInput.setBounds(195,235,200,25);
        this.getContentPane().add(codeInput);

        login.setBounds(133,300,90,40);
        login.addMouseListener(this);
        this.getContentPane().add(login);

        reveal.setBounds(400,180,18,29);
        reveal.addMouseListener(this);
        this.getContentPane().add(reveal);

        register.setBounds(256,300,90,40);
        register.addMouseListener(this);
        this.getContentPane().add(register);

        code.setBounds(400,235,100,25);
        this.getContentPane().add(code);
        //背景图片
        JLabel background = new JLabel(new ImageIcon(path + "background.png"));
        background.setBounds(0,0,470,390);
        this.getContentPane().add(background);
    }

    private void initJFrame() {
        //宽、高
        this.setSize(488, 430);
        //标题
        this.setTitle("拼图 登录");
        //居中
        this.setLocationRelativeTo(null);
        //置顶
        this.setAlwaysOnTop(true);
        //关闭一个窗口，整个程序就退出
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();
        if (source == login){
            ImageIcon loginPress = new ImageIcon(path +"loginPress.png");
            login.setIcon(loginPress);
        }else if (source == register){
            ImageIcon registerPress = new ImageIcon(path + "registerPress.png");
            register.setIcon(registerPress);
        }else if (source == reveal){
            ImageIcon revealing = new ImageIcon(path + "revealing.png");
            reveal.setIcon(revealing);
            passInput.setEchoChar((char) 0);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object source = e.getSource();
        if (source == login){
            login.setIcon(loginIcon);
            username = userInput.getText();
            String password = new String(passInput.getPassword());
            String checkCode = codeInput.getText();
            if(Database.checkUsernameExists(username)){
                if(Database.checkPasswordCorrect(username,password)){
                    if (!checkCode.equalsIgnoreCase(generatedCode)){
                        codeInput.setText(null);
                        JDialog loginInvalid = new JDialog();
                        JLabel codeError = new JLabel("验证码错误");
                        initDialog(codeError, loginInvalid);
                        loginInvalid.setVisible(true);
                        generatedCode = CheckCode.generator();
                        code.setText(generatedCode);
                    }else{
                        JLabel loginSuccess = new JLabel("登录成功");
                        initDialog(loginSuccess, checkPassed);
                        checkPassed.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                mainFrame.dispose();
                                super.windowClosing(e);
                            }
                        });
                        checkPassed.setVisible(true);
                        new GameJFrame();
                    }
                }else {
                    inputClear();
                    JDialog loginInvalid = new JDialog();
                    JLabel passwordIncorrect = new JLabel("用户名和密码不匹配");
                    initDialog(passwordIncorrect, loginInvalid);
                    loginInvalid.setVisible(true);
                }
            }else{
                inputClear();
                JDialog loginInvalid = new JDialog();
                JLabel usernameNotExist = new JLabel("用户名不存在");
                initDialog(usernameNotExist, loginInvalid);
                loginInvalid.setVisible(true);
            }
        }else if (source == register){
            register.setIcon(registerIcon);
            mainFrame.dispose();
            new RegisterJFrame();
        }else if (source == reveal){
            reveal.setIcon(revealIcon);
            passInput.setEchoChar('•');
        }
    }

    private static void initDialog(JLabel usernameNotExist, JDialog loginInvalid) {
        usernameNotExist.setBounds(0,0,250,250);
        usernameNotExist.setHorizontalAlignment(JLabel.CENTER);
        usernameNotExist.setVerticalAlignment(JLabel.CENTER);
        loginInvalid.add(usernameNotExist);
        loginInvalid.setTitle("提示");
        loginInvalid.setSize(600,250);
        loginInvalid.setLocationRelativeTo(null);
        loginInvalid.setAlwaysOnTop(true);
        loginInvalid.setModal(true);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    private void inputClear(){
        userInput.setText(null);
        passInput.setText(null);
        codeInput.setText(null);
    }
}

