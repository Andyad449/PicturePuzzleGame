package com.yizhuo.test;

import javax.swing.*;


public class AddJButton {
    public static void main(String[] args) {
        JDialog dialog = new JDialog();
        dialog.setTitle("按钮对话框");
        dialog.setLayout(null); // 关闭布局管理器

        JButton button = new JButton("确定");
        // 设置按钮的位置和大小（x, y, width, height）
        button.setBounds(100, 100, 100, 30);

        dialog.add(button);
        dialog.setSize(300, 200); // 设置对话框的大小
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dialog.setVisible(true);
    }
}