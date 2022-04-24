package net.hserver.hp.windows.form;

import net.hserver.hp.windows.config.Config;
import net.hserver.hp.windows.service.UserService;
import net.hserver.hp.windows.vo.UserVo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginWindow {

    JFrame windows1 =null;

    public void show() {
        windows1=new JFrame("HP穿透客服端");
        windows1.setResizable(false);
        Toolkit kit = Toolkit.getDefaultToolkit();
        //获取屏幕大小（int类型）
        Dimension screenSize = kit.getScreenSize();
        //通过屏幕大小获取宽度和长度
        int screenw = screenSize.width;
        int screenh = screenSize.height;
        windows1.setBounds((screenw / 2) - 250, (screenh / 2) - 250, 500, 500);
        FlowLayout flow = new FlowLayout();
        windows1.setLayout(flow);
        JLabel nameJla = new JLabel("账  号：");
        JLabel pwdJla = new JLabel("密  码：");
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
        windows1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btn1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String n = nameInput.getText();
                String p = pwdInput.getText();
                if (n.trim().length() > 0 && p.trim().length() > 0) {
                    login(n.trim(), p.trim());
                } else {
                    JOptionPane.showMessageDialog(null, "账号或者密码不能为空", "", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btn2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windows1.dispose();
                RegWindow.show();
            }
        });
    }


    public void showLogin() {
        Config instance = Config.getInstance();
        UserVo user = instance.getUser();
        if (user != null) {
            login(user.getUsername(), user.getPassword());
        } else {
            show();
        }
    }

    public void login(String username, String password) {
        Config instance = Config.getInstance();
        instance.setUSer(username, password);
        UserVo login = UserService.login(username, password);
        if (login != null) {
            if (windows1!=null){
                windows1.dispose();
            }
            new MainWindow(login).show();
        }else {
            if (windows1==null){
                show();
            }
            JOptionPane.showMessageDialog(null, "登录失败", "", JOptionPane.ERROR_MESSAGE);
        }
    }
}
