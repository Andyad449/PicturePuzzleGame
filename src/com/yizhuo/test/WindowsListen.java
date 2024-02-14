package com.yizhuo.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowsListen {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("主窗口");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            // 创建一个JDialog
            JDialog dialog = new JDialog(frame, "对话框", Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setSize(200, 150);

            // 为JDialog添加WindowListener以处理窗口关闭事件
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // 当对话框关闭时，隐藏主窗口
                    frame.setVisible(false);
                }
            });

            // 添加一些组件到JDialog（可选）
            JLabel label = new JLabel("这是一个对话框", SwingConstants.CENTER);
            dialog.getContentPane().add(label, BorderLayout.CENTER);

            // 显示对话框
            dialog.setLocationRelativeTo(frame); // 对话框相对于主窗口居中
            dialog.setVisible(true);

            // 显示主窗口
            frame.setVisible(true);
        });
    }
}