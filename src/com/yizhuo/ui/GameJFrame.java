package com.yizhuo.ui;

import com.yizhuo.utility.Database;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    // 创建二维数组，加载图片时会根据二维数组中的数据进行加载
    int[][] data = new int[4][4];
    // 记录空白方块在二维数组中的位置
    int x = 0, y = 0;
    // 记录图片的路径
    String path = "image\\animal\\animal3\\";
    // 定义一个二维数组存储正确的数据
    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0},
    };
    // 定义一个变量用来记录当前移动了多少步
    int count = 0;
    //判断此次胜利是否作弊
    boolean flag = false;

    // 创建选项下面的条目对象
    JMenuItem fastestItem = new JMenuItem("最快记录");
    JMenuItem rePlayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem buttonDescriptionItem = new JMenuItem("按键说明");

    JMenuItem accountItem = new JMenuItem("github");

    // 创建更换图片的三个子选项
    JMenuItem girlItem = new JMenuItem("美女");
    JMenuItem sportsItem = new JMenuItem("运动");
    JMenuItem animalItem = new JMenuItem("动物");

    public GameJFrame() {
        // 初始化界面
        initJFrame();
        // 初始化菜单
        initJMenuBar();
        // 初始化数据（打乱）
        initData();
        // 初始化图片：把图片加载到界面中
        initImage();
        // setVisible()一定要放在最后写
        this.setVisible(true);
    }

    private void initData() {
        // 二维数组中的数据对应图片位置
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Random r = new Random();
        // 打乱一维数组
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }
        // 将数据添加到二维数组中
        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            // 不管是不是0，都交给二维数组
            data[i / 4][i % 4] = tempArr[i];
        }
    }

    private void initImage() {
        // 清空已经加载的图片
        this.getContentPane().removeAll();
        if (victory()) {
            JLabel winJLabel = new JLabel(new ImageIcon("image\\win.png"));
            winJLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winJLabel);
            if (!flag) {
                Date now = new Date();
                Database.updateRecord(count,now);
            }else{
                JDialog isCheat = new JDialog();
                isCheat.setTitle("oops!");
                isCheat.setSize(300,300);
                isCheat.setAlwaysOnTop(true);
                isCheat.setLocationRelativeTo(null);
                isCheat.setModal(true);
                JTextArea prompt = new JTextArea("\n\n\n\n\n你使用了魔法通关，本次成绩不会记录");
                prompt.setColumns(12);
                prompt.setLineWrap(true);
                prompt.setEditable(false);
                Font font = new Font("黑体", Font.BOLD,16);
                prompt.setFont(font);
                isCheat.add(prompt);
                isCheat.setVisible(true);
            }
        }

        JLabel counter = new JLabel("步数：" + count);
        counter.setBounds(50, 30, 100, 20);
        this.getContentPane().add(counter);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // 获取当前要加载图片的序号
                int pictureNum = data[i][j];
                // 创建图片ImageIcon的对象
                ImageIcon icon = new ImageIcon(path + pictureNum + ".jpg");
                // 创建一个JLabel的对象（管理容器）
                JLabel jLabel = new JLabel(icon);
                // 指定图片位置（要在添加之前）
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                // 把JLabel的对象添加到界面
                this.getContentPane().add(jLabel);
            }
        }
        JLabel background = new JLabel(new ImageIcon("image\\background.png"));
        background.setBounds(40, 40, 508, 560);
        this.getContentPane().add(background);
        // 加载完过后刷新
        this.getContentPane().repaint();
    }

    private void initJMenuBar() {
        // 初始化菜单 JMenuBar->JMenu->JMenuItem
        // 最上面的大长条
        JMenuBar jMenuBar = new JMenuBar();

        // 创建三个选项的对象：功能、游戏帮助、关于我们
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");
        JMenu helpJMenu = new JMenu("游戏帮助");
        // 创建“功能”下的更换图片JMenu
        JMenu changeImageJMenu = new JMenu("更换图片");

        // 给条目JMenuBar绑定事件
        rePlayItem.addActionListener(this);

        // 将每一个选项下面的条目添加到选项中
        functionJMenu.add(changeImageJMenu);
        functionJMenu.add(fastestItem);
        functionJMenu.add(rePlayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);
        aboutJMenu.add(accountItem);
        helpJMenu.add(buttonDescriptionItem);

        // 将三个子选项添加到更换图片的选项中
        changeImageJMenu.add(girlItem);
        changeImageJMenu.add(sportsItem);
        changeImageJMenu.add(animalItem);

        // 给条目绑定事件
        girlItem.addActionListener(this);
        sportsItem.addActionListener(this);
        animalItem.addActionListener(this);
        fastestItem.addActionListener(this);
        rePlayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        buttonDescriptionItem.addActionListener(this);
        // 将两个选项添加到上面的大长条
        jMenuBar.add(functionJMenu);
        jMenuBar.add(helpJMenu);
        jMenuBar.add(aboutJMenu);

        // 给整个界面设置菜单，对象名写this代表当前创建的这个GameJFrame对象（继承自JFrame）
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        // 宽、高
        this.setSize(603, 680);
        // 标题
        this.setTitle("拼图游戏v1.1");
        // 居中
        this.setLocationRelativeTo(null);
        // 置顶
        this.setAlwaysOnTop(true);
        // 如果用3，不容易记住，用常量可以见名知意。表示关闭一个窗口，整个程序就退出
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 取消默认的居中放置
        this.setLayout(null);

        // 给整个界面添加一个键盘监听事件，触发时，执行本类中的代码(this)
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // 按下不松时会调用这个方法，按住A不松可以查看完整图片
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 65) {
            this.getContentPane().removeAll();
            // 加载完整图片和背景图片
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);

            JLabel background = new JLabel(new ImageIcon("image\\background.png"));
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);
            // 加载完过后刷新
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) throws ArrayIndexOutOfBoundsException {
        // 判断游戏是否胜利，如果胜利，应更新完数据库后直接结束，不能再执行下面的移动代码了
        if (victory()) {
            return;
        }

        // 对上下左右进行判断。左：37，上：38，右：39，下：40
        try {
            int code = e.getKeyCode();
            if (code == 37) {
                data[x][y] = data[x][y + 1];
                data[x][y + 1] = 0;
                y++;
                count++;
                initImage();
            } else if (code == 38) {
                data[x][y] = data[x + 1][y];
                data[x + 1][y] = 0;
                x++;
                count++;
                initImage();
            } else if (code == 39) {
                data[x][y] = data[x][y - 1];
                data[x][y - 1] = 0;
                y--;
                count++;
                initImage();
            } else if (code == 40) {
                data[x][y] = data[x - 1][y];
                data[x - 1][y] = 0;
                x--;
                count++;
                initImage();
            } else if (code == 65) {
                initImage();
            } else if (code == 86) {
                flag = true;
                data = new int[][]{
                        {1, 2, 3, 4},
                        {5, 6, 7, 8},
                        {9, 10, 11, 12},
                        {13, 14, 15, 0},
                };
                initImage();
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("数组下标越界");

        }
    }

    // 判断data数组中的数据与win数组中的数据是否相同，如果相同则提示胜利
    private boolean victory() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == fastestItem) {
            JDialog fastest = new JDialog();
            fastest.setTitle("最快步数记录");
            fastest.setSize(500, 150);
            fastest.setLocationRelativeTo(null);
            JTable table = GameJFrame.initTable();
            table.setEnabled(false);
            fastest.getContentPane().add(new JScrollPane(table));
            fastest.setAlwaysOnTop(true);
            fastest.setModal(true);
            fastest.setVisible(true);
        } else if (source == rePlayItem) {
            // 重新游戏：重新打乱二维数组中的数据；重新加载图片；计步器清零
            count = 0;
            initData();
            initImage();// 先置0再加载图片
        } else if (source == reLoginItem) {
            // 重新登录：关闭当前的游戏界面，打开登录界面
            this.setVisible(false);
            new LoginJFrame();
        } else if (source == accountItem) {
            // 弹出二维码
            JDialog jDialog = new JDialog();
            JLabel jLabel = new JLabel(new ImageIcon("image\\qrcode.png"));
            jDialog.setTitle("我的github账户");
            jLabel.setBounds(0, 0, 258, 258);
            jDialog.getContentPane().add(jLabel);
            jDialog.setSize(344, 344);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            // 弹窗不关闭则无法操作下面的界面
            jDialog.setModal(true);
            jDialog.setVisible(true);
        } else if (source == closeItem) {
            // 关闭游戏：直接退出虚拟机即可
            System.exit(0);
        } else if (source == girlItem) {
            int randNum = new Random().nextInt(13) + 1;
            path = "image\\girl\\girl" + randNum + "\\";
            count = 0;
            initData();
            initImage();
        } else if (source == sportsItem) {
            int randNum = new Random().nextInt(10) + 1;
            path = "image\\sport\\sport" + randNum + "\\";
            count = 0;
            initData();
            initImage();
        } else if (source == animalItem) {
            int randNum = new Random().nextInt(8) + 1;
            path = "image\\animal\\animal" + randNum + "\\";
            count = 0;
            initData();
            initImage();
        } else if (source == buttonDescriptionItem) {
            JDialog prompt = new JDialog();
            JTextArea words = new JTextArea("按方向键移动图片，按住A键不松可查看完整图片，努力用更少的步数来还原图片吧！");
            words.setLineWrap(true);
            words.setEditable(false);
            Font font = new Font("黑体", Font.BOLD, 14);
            words.setFont(font);
            prompt.getContentPane().add(words);
            prompt.setTitle("操作说明");
            prompt.setSize(356, 220);
            prompt.setLocationRelativeTo(null);
            prompt.setAlwaysOnTop(true);
            prompt.setModal(true);
            prompt.setVisible(true);
        }
    }

    public static JTable initTable() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "ranking", "username", "steps", "date"
                }
        );
        ArrayList<String> arrayList = Database.searchTOP5();
        Object[] temp = arrayList.toArray();
        Object[][] result = new Object[5][4];
        for (int i = 0; i < temp.length; i++) {
            int rowindex = i / 4;
            int colindex = i % 4;
            result[rowindex][colindex] = temp[i];
        }
        for (Object[] objects : result) {
            model.addRow(objects);
        }

        return new JTable(model);
    }
}
