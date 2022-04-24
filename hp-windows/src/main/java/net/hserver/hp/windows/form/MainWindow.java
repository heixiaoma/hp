package net.hserver.hp.windows.form;

import net.hserver.hp.client.CallMsg;
import net.hserver.hp.client.HpClient;
import net.hserver.hp.windows.config.Config;
import net.hserver.hp.windows.config.ConstConfig;
import net.hserver.hp.windows.vo.UserVo;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class MainWindow {

    private final UserVo userVo;
    private JButton btn1;
    private final Timer timer = new Timer(1000, new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (btn1 != null) {
                btn1.setEnabled(true);
                if (client.getStatus()) {
                    btn1.setText("关闭穿透");
                } else {
                    btn1.setText("开启穿透");
                }
            }
        }
    });

    private final JTextArea area = new JTextArea();
    private final JScrollPane jsp = new JScrollPane(area);


    private final HpClient client = new HpClient(new CallMsg() {
        @Override
        public void message(String msg) {
            addLog(msg);
        }
    });

    public MainWindow(UserVo userVo) {
        this.userVo = userVo;
    }

    public void show() {
        JFrame windows1 = new JFrame("HP穿透客服端");
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
        JLabel sp = new JLabel("外网端口：");
        JLabel ip = new JLabel("内  网  IP ：");
        JLabel port = new JLabel("内网端口：");
        //为输入框设置大小
        Dimension dim = new Dimension(400, 35);
        JComboBox jcombo = new JComboBox(userVo.getPorts().toArray(new Integer[0]));
        JTextField ipInput = new JTextField();
        JTextField portInput = new JTextField();
        jcombo.setPreferredSize(dim);
        ipInput.setPreferredSize(dim);
        portInput.setPreferredSize(dim);
        btn1 = new JButton("开启穿透");
        windows1.add(sp);
        windows1.add(jcombo);
        windows1.add(ip);
        windows1.add(ipInput);
        windows1.add(port);
        windows1.add(portInput);
        windows1.add(btn1);

        //日志
        area.setEditable(false);
        jsp.setPreferredSize(new Dimension(480, 300));
        windows1.add(jsp);
        windows1.setVisible(true);
        windows1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btn1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = "80";
                try {
                    id = jcombo.getSelectedItem().toString();
                } catch (Exception ea) {
                }
                String i = ipInput.getText();
                String p = portInput.getText();
                if (i.trim().length() > 0 && p.trim().length() > 0) {
                    try {
                        boolean status = client.getStatus();
                        if (!status) {
                            client.connect(ConstConfig.IP, ConstConfig.PORT, userVo.getUsername(), userVo.getPassword(), Integer.parseInt(id), i, Integer.parseInt(p));
                        } else {
                            client.close();
                        }
                    } catch (Exception ioException) {
                        addLog("连接失败：" + ioException.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ip或者端口不能为null", "", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        //创建菜单
        JMenuBar menuBar = new JMenuBar();
        windows1.setJMenuBar(menuBar);

        JMenu menu = new JMenu("菜单");
        menuBar.add(menu);
        JMenuItem exitLogin = new JMenuItem("重新登录");
        JMenuItem exit = new JMenuItem("退出");
        menu.add(exitLogin);
        menu.add(exit);

        exitLogin.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                client.close();
                windows1.dispose();
                new LoginWindow().show();
            }
        });

        exit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                client.close();
                windows1.dispose();
                System.exit(-1);
            }
        });



        timer.start();
    }

    public void addLog(String data) {
        if (area.getText().length() > 1000) {
            area.setText("");
        }
        area.append(data + "\r\n");
        JScrollBar vertical = jsp.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }


}
