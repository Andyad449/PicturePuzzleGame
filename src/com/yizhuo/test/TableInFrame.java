package com.yizhuo.test;

import com.yizhuo.ui.GameJFrame;

import javax.swing.*;

public class TableInFrame {

    public static void main(String[] args) {
        // 创建一个JFrame实例
        JDialog jDialog = new JDialog();
        jDialog.setSize(500, 150);
        jDialog.setLocationRelativeTo(null);
        JTable table = GameJFrame.initTable();
        table.setEnabled(false);
        jDialog.getContentPane().add(new JScrollPane(table));

        jDialog.setVisible(true);
    }
}