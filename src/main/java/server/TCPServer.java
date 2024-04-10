package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TCPServer {
    private static final int PORT = 2525;
    private static databaseconnect db = new databaseconnect();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) { // 创建ServerSocket并监听指定端口
            System.out.println("Server is listening on port " + PORT);

            while (true) { // 无限循环等待客户端连接
                Socket clientSocket = serverSocket.accept(); // 接受新的客户端连接
                processClient(clientSocket, db); // 处理客户端连接
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void processClient(Socket clientSocket, databaseconnect db) {
        String userId = null;
        String password = null;
        double balance = 0;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             OutputStreamWriter txt = new OutputStreamWriter(new FileOutputStream(
                     new File("./logs", "ServerLog" + NowDateTime.formatTime(System.currentTimeMillis()).replace(" ", "_").replace(":", "_") + ".txt"),
                     true))) {

            long timestamp = System.currentTimeMillis();
            String formattedDate = NowDateTime.formatTime(timestamp);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {// 读取客户端发送的命令不为空的时候开始工作
                timestamp = System.currentTimeMillis();
                formattedDate = NowDateTime.formatTime(timestamp);//更新时间
                txt.write(formattedDate + " the massage from Client is " + inputLine + "\n");//客户端报文记录
                if (inputLine.startsWith("HELO")) { // 客户端发送HELO命令
                    String[] parts = inputLine.split(" ");
                    userId = parts[1];
                    if (db.validUserId(userId)) { // 检查用户ID是否匹配
                        System.out.println("successful HELO");
                        out.println("500 AUTH REQUIRED"); // 用户ID存在
                        txt.write(formattedDate + " User:" + userId + " the usrId exists in the database.\n");
                    } else {
                        System.out.println("unsuccessful HELO");
                        out.println("401 ERROR!"); // 用户ID不存在
                        txt.write(formattedDate + " User:" + userId + " the usrId does not exist in the database.\n");
                        userId=null;
                    }
                } else if (inputLine.startsWith("PASS")) { // 客户端发送PASS命令
                    String[] parts = inputLine.split(" ");
                    password = parts[1]; // 读取密码
                    if (db.isPasswordMatch(userId, password)) {
                        System.out.println("successful PASS");
                        out.println("525 OK!"); // 密码正确
                        balance = db.getBalance(userId); // 获取用户余额
                        txt.write(formattedDate + " User:" + userId + " the password is correct.\n");
                    } else {
                        System.out.println("unsuccessful PASS");
                        out.println("401 ERROR!"); // 密码错误
                        txt.write(formattedDate + " User:" + userId + " the password is incorrect.\n");
                        password=null;
                    }
                } else if (inputLine.startsWith("BALA")) { // 客户端发送BALA命令
                    if (userId != null) { // 确保userId
                        System.out.println("BALA");
                        balance = db.getBalance(userId);
                        out.println("AMNT: " + balance); // 返回用户余额
                        txt.write(formattedDate + " User:" + userId + " get balance.\n");
                    }
                } else if (inputLine.startsWith("WDRA")) { // 客户端发送WDRA命令
                    if (userId != null && password != null) { // 确保userId和password
                        String[] parts = inputLine.split(" ");
                        double requestedAmount = Double.parseDouble(parts[1]);
                        if (requestedAmount <= balance) {
                            System.out.println("successful WDRA");
                            out.println("525 OK!");
                            db.updateBalance(userId, balance - requestedAmount); // 更新余额
                            balance = db.getBalance(userId);
                            txt.write(formattedDate + " User:" + userId + " withdrew " + requestedAmount + " dollars.\n");
                        } else {
                            System.out.println("unsuccessful WDRA");
                            out.println("401 ERROR!"); // 余额不足
                            txt.write(formattedDate + " User:" + userId + " does not have sufficient balance.\n");
                        }
                    }
                } else if (inputLine.equals("BYE")) { // 客户端发送BYE命令
                    System.out.println("BYE.");
                    out.println("BYE"); // 确认结束连接
                    txt.write(formattedDate + " BYE. from Server\n");
                    break; // 退出循环，关闭当前客户端连接
                }

            }
        } catch (IOException e) {
            System.out.println("Exception caught in processClient: " + e.getMessage());
            e.printStackTrace();
        }
    }
}