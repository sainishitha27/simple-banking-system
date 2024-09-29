import java.sql.*;
import java.util.Scanner;

public class Bank {
    static Scanner sc = new Scanner(System.in);
    static Connection connection;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB", "root", "");
            System.out.println("Enter your name:");
            String name = sc.nextLine();

            int opt;
            while (true) {
                System.out.println("Welcome");
                System.out.println("1. Check balance");
                System.out.println("2. Open Account");
                System.out.println("3. Deposit");
                System.out.println("4. Withdraw");
                System.out.println("5. Exit");
                System.out.println("Enter any option:");
                opt = sc.nextInt();
                sc.nextLine(); 
                switch (opt) {
                    case 1:
                        checkBalance(name);
                        break;
                    case 2:
                        openAccount();
                        break;
                    case 3:
                        deposit(name);
                        break;
                    case 4:
                        withdraw(name);
                        break;
                    case 5:
                        exit();
                        return; 
                    default:
                        System.out.println("Entered invalid option. Try again next time.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void checkBalance(String name) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement("SELECT balance FROM Accounts_ WHERE name = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                double bal = rs.getDouble("balance");
                System.out.println("Your current balance is " + bal);
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void openAccount() {
        System.out.println("Enter your name:");
        String name = sc.nextLine();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("INSERT INTO Accounts_ (name, balance) VALUES (?, 0.0)");
            ps.setString(1, name);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account opened successfully for " + name);
                checkBalance(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deposit(String name) {
        System.out.println("Enter the amount to deposit:");
        double amt = sc.nextDouble();
        sc.nextLine(); 

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("UPDATE Accounts_ SET balance = balance + ? WHERE name = ?");
            ps.setDouble(1, amt);
            ps.setString(2, name);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("The amount deposited into your account is " + amt);
                checkBalance(name);
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void withdraw(String name) {
        System.out.println("Enter the amount to withdraw:");
        double amt = sc.nextDouble();
        sc.nextLine(); 

        PreparedStatement psCheck = null;
        ResultSet rs = null;
        PreparedStatement psWithdraw = null;
        try {
          
            psCheck = connection.prepareStatement("SELECT balance FROM Accounts_ WHERE name = ?");
            psCheck.setString(1, name);
            rs = psCheck.executeQuery();
            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                if (amt > currentBalance) {
                    System.out.println("Insufficient balance. Cannot withdraw.");
                } else {
                    psWithdraw = connection.prepareStatement("UPDATE Accounts_ SET balance = balance - ? WHERE name = ?");
                    psWithdraw.setDouble(1, amt);
                    psWithdraw.setString(2, name);
                    int rowsAffected = psWithdraw.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("The amount withdrawn from your account is " + amt);
                        checkBalance(name);
                    }
                }
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (psCheck != null)
                    psCheck.close();
                if (psWithdraw != null)
                    psWithdraw.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void exit() {
        System.out.println("Thank you. Have a nice day!!");
    }
}
