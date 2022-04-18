package net.hserver.hp.windows.form;

import javax.swing.*;
import java.awt.*;

public class RegWindow {

    public static void show() {
        JFrame windows1 = new JFrame("HP穿透客服端");
        Toolkit kit = Toolkit.getDefaultToolkit();
        //获取屏幕大小（int类型）
        Dimension screenSize = kit.getScreenSize();
        //通过屏幕大小获取宽度和长度
        int screenw = screenSize.width;
        int screenh = screenSize.height;
        windows1.setBounds((screenw / 2) - 250, (screenh / 2) - 250, 500, 500);
        FlowLayout flow = new FlowLayout(); //使用流式布局
        windows1.setLayout(flow);
        JLabel nameJla = new JLabel("账        号：");
        JLabel pwdJla = new JLabel("密         码：");
        JLabel pwdJla1 = new JLabel("重复密码：");
        //为输入框设置大小
        Dimension dim = new Dimension(400,35);
        JTextField nameInput = new JTextField();
        JPasswordField pwdInput = new JPasswordField();
        JPasswordField pwdInput1 = new JPasswordField();
        nameInput.setPreferredSize(dim);
        pwdInput.setPreferredSize(dim);
        pwdInput1.setPreferredSize(dim);
        JButton btn2 = new JButton("注册");
        windows1.add(nameJla);
        windows1.add(nameInput);

        windows1.add(pwdJla);
        windows1.add(pwdInput);

        windows1.add(pwdJla1);
        windows1.add(pwdInput1);

        windows1.add(btn2);
        windows1.setVisible(true);

    }

}
