package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//数据库的连接，定义了检查userid是否存在，userid和password是否匹配，getbalance和updatebalance
public class databaseconnect {
    private String url = "jdbc:mysql://localhost:3306/bank";
    private String user = "root";
    private String databasepassword = "password";

    public boolean validUserId(String userId) {
        // 检查用户ID是否存在于users表中
        String sql = "SELECT COUNT(*) FROM users WHERE UserID = ?";
        try (Connection connection = DriverManager.getConnection(url, user, databasepassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPasswordMatch(String userId, String password) {
        // 检查用户ID和密码是否匹配
        String sql = "SELECT COUNT(*) FROM users WHERE UserID = ? AND Password = ?";
        try (Connection connection = DriverManager.getConnection(url, user, databasepassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public double getBalance(String userId) {
        // 获取用户余额
        String sql = "SELECT Balance FROM users WHERE UserID = ?";
        double balance = 0;
        try (Connection connection = DriverManager.getConnection(url, user, databasepassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    balance = resultSet.getDouble("Balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }
   /* public String getPassword(String userId) {
        // 获取用户密码
        String sql = "SELECT Password FROM users WHERE UserID = ?";
        String password = null;
        try (Connection connection = DriverManager.getConnection(url, user, databasepassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    password = resultSet.getString("Password"); // 正确获取密码
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password; // 返回密码字符串
    }*/
    public void updateBalance(String userId, double balance) {
        // 更新用户余额
        String sql = "UPDATE users SET Balance = ? WHERE UserID = ?";
        try (Connection connection = DriverManager.getConnection(url, user, databasepassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, balance);
            preparedStatement.setString(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}