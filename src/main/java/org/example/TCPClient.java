package org.example;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class TCPClient extends JFrame
{
    String ip;
    int port;
    String account;
    String password;
    String balance;

    Socket clientSocket;
    DataOutputStream writer;
    BufferedReader reader;

    JLabel label_of_account = new JLabel("账号");
    JTextField text_of_account = new JTextField();

    JLabel label_of_password = new JLabel("密码");
    JTextField text_of_password = new JTextField();

    JLabel label_of_check = new JLabel("您的余额");
    JButton button_of_check = new JButton("返回");

    Box col1 = Box.createVerticalBox();

    Box row1 = Box.createHorizontalBox();


    JPanel panel1 = new JPanel(new BorderLayout());

    Box col2 = Box.createVerticalBox();

    Box row2 = Box.createHorizontalBox();

    Box col3 = Box.createVerticalBox();
    Box row3 = Box.createHorizontalBox();
    Box row4 = Box.createHorizontalBox();
    Box row5 = Box.createHorizontalBox();

    Box col4 = Box.createVerticalBox();
    Box col5 = Box.createVerticalBox();

    JPanel panel2 = new JPanel(new BorderLayout());
    JPanel panel3 = new JPanel(new BorderLayout());
    JPanel panel4 = new JPanel(new BorderLayout());
    JPanel panel5 = new JPanel(new BorderLayout());

    Font font = new Font("宋体",Font.BOLD,18);
    Font font2 = new Font("宋体",Font.BOLD,22);
    JButton button1 = new JButton("提交");

    JButton button2 = new JButton("登录");

    JButton button3 = new JButton("查询余额");
    JButton button4 = new JButton("取款");
    JButton button5 = new JButton("退出");

    JTextField text_of_balance = new JTextField();
    JButton button1_of_balance = new JButton("确认取款");
    JButton button2_of_balance = new JButton("返回");
    public TCPClient(String ip,int port)
    {
        this.ip = ip;
        this.port = port;
        init();

        setFont();
        setLayout1();
        setLayout2();
        setLayout3();
        setLayout4();
        setLayout5();

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    account = text_of_account.getText();
                    System.out.println(account);

                    try
                    {
                        clientSocket = new Socket(ip, port);
                        writer = new DataOutputStream(clientSocket.getOutputStream());
                        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    } catch (IOException x)
                    {
                        throw new RuntimeException(x);
                    }
                    // 发送账户信息
                    writer.writeBytes("HELO "+account+'\n');

                    // 读取服务器响应状态

                    String status = reader.readLine();

                    System.out.println("Server Response: " + status);

                    writer.close();
                    reader.close();
                    clientSocket.close();

                    if(Objects.equals(status, "500 AUTH REQUIRED"))
                    {
                        panel1.setVisible(false);
                        panel2.setVisible(true);
                    }
                    else
                        JOptionPane.showMessageDialog(null,"您输入的账号不合法！","登录失败",JOptionPane.ERROR_MESSAGE);

                } catch (IOException ex) {
                    // 捕获并处理异常
                    ex.printStackTrace();
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    password = text_of_password.getText();
                    System.out.println(password);
                    try
                    {
                        clientSocket = new Socket(ip, port);
                        writer = new DataOutputStream(clientSocket.getOutputStream());
                        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    } catch (IOException x)
                    {
                        throw new RuntimeException(x);
                    }
                    // 发送账户信息
                    writer.writeBytes("PASS "+password+'\n');

                    // 读取服务器响应状态

                    String status = reader.readLine();

                    System.out.println("Server Response: " + status);

                    writer.close();
                    reader.close();
                    clientSocket.close();

                    if(Objects.equals(status, "525 OK!"))
                    {
                        panel2.setVisible(false);
                        panel3.setVisible(true);
                    }
                    else
                        JOptionPane.showMessageDialog(null,"您输入的密码不正确！","登录失败",JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    // 捕获并处理异常
                    ex.printStackTrace();
                }
            }
        });

        button3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    try
                    {
                        clientSocket = new Socket(ip, port);
                        writer = new DataOutputStream(clientSocket.getOutputStream());
                        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    } catch (IOException x)
                    {
                        throw new RuntimeException(x);
                    }
                    // 发送账户信息
                    writer.writeBytes("BALA"+'\n');

                    // 读取服务器响应状态

                    String balance = reader.readLine();
                    System.out.println(balance);
                    setTitle("余额查询");
                    label_of_check.setText(balance);

                    writer.close();
                    reader.close();
                    clientSocket.close();
                    setTitle("操作界面");
                    panel3.setVisible(false);
                    panel4.setVisible(true);

                } catch (IOException ex) {
                    // 捕获并处理异常
                    ex.printStackTrace();
                }
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setTitle("取款界面");
                panel3.setVisible(false);
                panel5.setVisible(true);

            }

        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        button_of_check.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setTitle("操作界面");
                panel3.setVisible(true);
                panel4.setVisible(false);

            }
        });


        button1_of_balance.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    balance = text_of_balance.getText();
                    System.out.println(balance);
                    try
                    {
                        clientSocket = new Socket(ip, port);
                        writer = new DataOutputStream(clientSocket.getOutputStream());
                        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    } catch (IOException x)
                    {
                        throw new RuntimeException(x);
                    }
                    // 发送账户信息
                    writer.writeBytes("WDRA "+balance+'\n');

                    // 读取服务器响应状态

                    String status = reader.readLine();

                    System.out.println("Server Response: " + status);

                    writer.close();
                    reader.close();
                    clientSocket.close();

                    if(Objects.equals(status, "525 OK"))
                        JOptionPane.showMessageDialog(null,"您已成功取款"+balance+"元","取款成功",JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null,"您的余额不足！","取款失败",JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    // 捕获并处理异常
                    ex.printStackTrace();
                }
            }
        });

        button2_of_balance.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("操作界面");
                panel3.setVisible(true);
                panel5.setVisible(false);
            }
        });


        this.setVisible(true);
    }

    public void init()
    {


        
        this.setSize(400,300);
        this.setTitle("登录界面");
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    }

    public void setFont()
    {

        label_of_account.setFont(font);
        text_of_account.setFont(font);
        button1.setFont(font);

        label_of_password.setFont(font);
        text_of_password.setFont(font);
        button2.setFont(font);

        button3.setFont(font2);
        button4.setFont(font2);
        button5.setFont(font2);

        label_of_check.setFont(font2);
        button_of_check.setFont(font2);

        text_of_balance.setFont(font);
        button1_of_balance.setFont(font);
        button2_of_balance.setFont(font);
    }
    public void setLayout1()
    {
        row1.add(label_of_account);
        row1.add(Box.createHorizontalStrut(10));
        row1.add(text_of_account);

        col1.add(Box.createVerticalStrut(10));
        col1.add(row1);

        col1.add(Box.createVerticalStrut(10));
        col1.add(button1);

        panel1.setBounds(100,75,200,75);

        panel1.add(col1,BorderLayout.CENTER);
        panel1.setVisible(true);

        this.add(panel1);
    }

    public void setLayout2()
    {
        row2.add(label_of_password);
        row2.add(Box.createHorizontalStrut(10));
        row2.add(text_of_password);

        col2.add(Box.createVerticalStrut(10));
        col2.add(row2);

        col2.add(Box.createVerticalStrut(10));
        col2.add(button2);

        panel2.setBounds(100,75,200,75);

        panel2.add(col2,BorderLayout.CENTER);
        panel2.setVisible(false);

        this.add(panel2);
    }

    public void setLayout3()
    {
        row3.add(button3);

        row4.add(button4);

        row5.add(button5);

        col3.add(row3);
        col3.add(Box.createVerticalStrut(30));

        col3.add(row4);

        col3.add(Box.createVerticalStrut(30));
        col3.add(row5);

        panel3.setBounds(100,25,200,200);

        panel3.add(col3,BorderLayout.CENTER);
        panel3.setVisible(false);

        this.add(panel3);
    }

    public void setLayout4()
    {
        Box x = Box.createHorizontalBox();
        Box y = Box.createHorizontalBox();

        x.add(label_of_check);
        y.add(button_of_check);

        col4.add(Box.createVerticalStrut(30));
        col4.add(x);
        col4.add(Box.createVerticalStrut(30));
        col4.add(y);

        panel4.setBounds(100,25,200,200);
        panel4.add(col4,BorderLayout.CENTER);

        panel4.setVisible(false);

        this.add(panel4);
    }

    public void setLayout5()
    {
        Box x = Box.createHorizontalBox();
        Box y = Box.createHorizontalBox();
        Box z = Box.createHorizontalBox();

        x.add(text_of_balance);
        y.add(button1_of_balance);
        z.add(button2_of_balance);

        col5.add(Box.createVerticalStrut(30));
        col5.add(x);
        col5.add(Box.createVerticalStrut(30));
        col5.add(y);
        col5.add(Box.createVerticalStrut(30));
        col5.add(z);

        panel5.setBounds(100,25,200,200);
        panel5.add(col5,BorderLayout.CENTER);

        panel5.setVisible(false);

        this.add(panel5);
    }
}
