package net.hserver.hp.windows.form;

import net.hserver.hp.windows.config.Config;
import net.hserver.hp.windows.vo.UserVo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginWindow {

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
        JLabel nameJla = new JLabel("账号：");
        JLabel pwdJla = new JLabel("密码：");
        //为输入框设置大小
        Dimension dim = new Dimension(400, 35);
        JTextField nameInput = new JTextField();
        JPasswordField pwdInput = new JPasswordField();
        nameInput.setPreferredSize(dim);
        pwdInput.setPreferredSize(dim);
        JButton btn1 = new JButton("登录");
        JButton btn2 = new JButton("注册");

        windows1.add(nameJla);
        windows1.add(nameInput);
        windows1.add(pwdJla);
        windows1.add(pwdInput);
        windows1.add(btn1);
        windows1.add(btn2);
        windows1.setVisible(true);

        btn1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String n = nameInput.getText();
                String p = pwdInput.getText();
                if (n.trim().length() > 0 && p.trim().length() > 0) {
                    login(n.trim(), p.trim());
                }
            }
        });

        btn2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegWindow.show();
            }
        });
    }


    public static void login() {
        Config instance = Config.getInstance();
        UserVo user = instance.getUser();
        if (user != null) {
            MainWindow.login();
        } else {
            show();
        }
    }

    public static void login(String username, String password) {
        Config instance = Config.getInstance();
        instance.setUSer(username, password);


    }
}
