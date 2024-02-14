package com.yizhuo.ui;

import com.yizhuo.utility.CheckRegister;
import com.yizhuo.utility.Database;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class RegisterJFrame extends JFrame implements MouseListener {

    private static RegisterJFrame mainJFrame;

    String path = "image\\register\\";

    JTextArea userPrompt = new JTextArea("3~10个字符，不能以数字开头，只能是字母和数字的组合");
    JTextArea passPrompt = new JTextArea("6~20位，不能是纯数字");
    JTextArea rePassPrompt = new JTextArea("需与上面输入的密码一致");
    //注册用户名的文本输入框
    JTextField userInput = new JTextField(20);
    //注册密码的密文输入框
    JPasswordField passInput = new JPasswordField(20);
    //再次输入密码的密文输入框
    JPasswordField rePassInput = new JPasswordField(20);

    //注册按钮
    ImageIcon registerIcon = new ImageIcon(path + "register.png");
    JLabel register = new JLabel(registerIcon);
    //重置按钮
    ImageIcon resetIcon = new ImageIcon(path + "reset.png");
    JLabel reset = new JLabel(resetIcon);

    public RegisterJFrame() {
        mainJFrame = this;
        InitJFrame();
        // 初始化提示标签并添加到内容面板中，但设置为不可见
        userPrompt.setBounds(300, 180, 180, 40);
        userPrompt.setLineWrap(true);
        this.getContentPane().add(userPrompt);
        userPrompt.setVisible(false);
        passPrompt.setBounds(300, 220, 180, 40);
        passPrompt.setLineWrap(true);
        this.getContentPane().add(passPrompt);
        passPrompt.setVisible(false);
        rePassPrompt.setBounds(300, 260, 180, 40);
        rePassPrompt.setLineWrap(true);
        this.getContentPane().add(rePassPrompt);
        rePassPrompt.setVisible(false);
        InitImage();// 确保在添加背景图片之前已经添加了提示标签
        this.setVisible(true);
    }

    private void InitImage() {
        //注册用户名
        JLabel username = new JLabel(new ImageIcon(path + "registerUser.png"));
        username.setBounds(60, 180, 79, 17);
        this.getContentPane().add(username);

        userInput.setBounds(150, 180, 130, 20); // 设置合适的宽度和高度
        userInput.addMouseListener(this);
        this.getContentPane().add(userInput);
        //注册密码
        JLabel password = new JLabel(new ImageIcon(path + "registerPass.png"));
        password.setBounds(60, 220, 64, 16);
        this.getContentPane().add(password);

        passInput.setBounds(150, 220, 130, 20);
        passInput.addMouseListener(this);
        this.getContentPane().add(passInput);
        //再次输入密码
        JLabel rePassword = new JLabel(new ImageIcon(path + "rePassword.png"));
        rePassword.setBounds(40, 260, 96, 17);
        this.getContentPane().add(rePassword);

        rePassInput.setBounds(150, 260, 130, 20);
        rePassInput.addMouseListener(this);
        this.getContentPane().add(rePassInput);

        register.setBounds(100, 320, 128, 47);
        register.addMouseListener(this);
        this.getContentPane().add(register);

        reset.setBounds(248, 320, 128, 47);
        reset.addMouseListener(this);
        this.getContentPane().add(reset);
        //背景图片（最后）
        JLabel background = new JLabel(new ImageIcon(path + "background.png"));
        background.setBounds(0, 0, 470, 390);
        this.getContentPane().add(background);

    }

    private void InitJFrame() {
        this.setSize(488, 500);
        this.setTitle("拼图 注册");
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == register) {
            ImageIcon registerPressIcon = new ImageIcon(path + "registerPress.png");
            register.setIcon(registerPressIcon);
        } else if (e.getSource() == reset) {
            ImageIcon resetPressIcon = new ImageIcon(path + "resetPress.png");
            reset.setIcon(resetPressIcon);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == register) {
            register.setIcon(registerIcon);
            String username = userInput.getText();
            char[] password = passInput.getPassword();
            char[] rePass = rePassInput.getPassword();
            if (Database.checkUsernameExists(username)) {
                JDialog usernameExist = new JDialog();
                JLabel usernameExistPrompt = new JLabel("用户名已存在");
                initDialog(usernameExistPrompt, usernameExist);
                usernameExist.setVisible(true);
            } else {
                if (CheckRegister.checkUsername(username)) {
                    if (CheckRegister.checkPassword(password)) {
                        if (Arrays.equals(password, rePass)) {
                            String strPassword = new String(password);
                            Database.insertRegisterInfo(username, strPassword);
                            JDialog registerSuccess = new JDialog();
                            JLabel registerSuccessPrompt = new JLabel("注册成功");
                            initDialog(registerSuccessPrompt, registerSuccess);
                            // 添加窗口关闭事件，关闭主窗口
                            registerSuccess.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    super.windowClosing(e);
                                    mainJFrame.dispose(); // 关闭主窗口
                                }
                            });
                            registerSuccess.setVisible(true);
                            new LoginJFrame();
                        } else {
                            rePassInput.setText(null);
                            JDialog passwordNotSame = new JDialog();
                            JLabel passwordNotSamePrompt = new JLabel("两次输入的密码不一致");
                            initDialog(passwordNotSamePrompt, passwordNotSame);
                            passwordNotSame.setVisible(true);
                        }
                    } else {
                        passInput.setText(null);
                        rePassInput.setText(null);
                        JDialog passwordInvalid = new JDialog();
                        JLabel passwordInvalidPrompt = new JLabel("密码不符合要求");
                        initDialog(passwordInvalidPrompt, passwordInvalid);
                        passwordInvalid.setVisible(true);
                    }
                } else {
                    JDialog usernameInvalid = new JDialog();
                    JLabel usernameInvalidPrompt = new JLabel("用户名不符合要求");
                    initDialog(usernameInvalidPrompt, usernameInvalid);
                    usernameInvalid.setVisible(true);
                }
            }
        } else if (e.getSource() == reset) {
            reset.setIcon(resetIcon);
            userInput.setText(null);
            passInput.setText(null);
            rePassInput.setText(null);
        }
    }

    private void initDialog(JLabel usernameExistPrompt, JDialog usernameExist) {
        usernameExistPrompt.setBounds(0, 0, 250, 250);
        usernameExistPrompt.setHorizontalAlignment(JLabel.CENTER);
        usernameExistPrompt.setVerticalAlignment(JLabel.CENTER);
        usernameExist.getContentPane().add(usernameExistPrompt);
        usernameExist.setTitle("提示");
        usernameExist.setSize(600, 250);
        usernameExist.setLocationRelativeTo(null);
        usernameExist.setAlwaysOnTop(true);
        usernameExist.setModal(true);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == userInput) {
            userPrompt.setVisible(true);
        } else if (e.getSource() == passInput) {
            passPrompt.setVisible(true);
        } else if (e.getSource() == rePassInput) {
            rePassPrompt.setVisible(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == userInput) {
            userPrompt.setVisible(false);
        } else if (e.getSource() == passInput) {
            passPrompt.setVisible(false);
        } else if (e.getSource() == rePassInput) {
            rePassPrompt.setVisible(false);
        }
    }
}
