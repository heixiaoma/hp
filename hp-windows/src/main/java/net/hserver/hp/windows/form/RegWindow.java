package net.hserver.hp.windows.form;

import net.hserver.hp.windows.config.Config;
import net.hserver.hp.windows.service.UserService;
import net.hserver.hp.windows.vo.UserVo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegWindow {
    private static JFrame windows1 = new JFrame("HP穿透客服端");

    public static void show() {
        windows1.setResizable(false);
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
        Dimension dim = new Dimension(400, 35);
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
        windows1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btn2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String n = nameInput.getText();
                String p = pwdInput.getText();
                String p1 = pwdInput1.getText();
                if (n.trim().length() > 0 && p.trim().length() > 0 && p1.trim().length() > 0) {
                    if (p.trim().equals(p1.trim())) {
                        try {
                            boolean reg = UserService.reg(n.trim(), p.trim());
                            if (reg) {
                                login(n, p);
                            }
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, "注册失败:" + exception.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "两次输入的密码不一致", "", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "账号或者密码不能为空", "", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    public static void login(String username, String password) {
        Config instance = Config.getInstance();
        instance.setUSer(username, password);
        UserVo login = UserService.login(username, password);
        if (login == null) {
            show();
        } else {
            windows1.dispose();
            new MainWindow(login).show();
        }
    }
}
