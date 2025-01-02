import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class VulnerableApp {

    private static Connection connectToDatabase() throws SQLException {
        // 模擬資料庫連線 (實際應用中不應將憑據硬編碼)
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String password = "password123"; // 不安全：敏感資訊硬編碼
        return DriverManager.getConnection(url, user, password);
    }

    public static void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try (Connection conn = connectToDatabase()) {
            // 不安全：使用字串拼接的方式組合 SQL 查詢，容易被注入攻擊
            String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            System.out.println("Executing query: " + query);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                System.out.println("Login successful! Welcome, " + username + ".");
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void readFile() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter file name to read: ");
        String filename = scanner.nextLine();

        // 不安全：未驗證輸入的檔案名稱，可能導致目錄遍歷
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println("File contents:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");
        } catch (IOException e) {
            System.out.println("Error: An IO exception occurred!");
        }
    }

    public static void mainMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Login");
            System.out.println("2. Read a file");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 清除換行符

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    readFile();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the vulnerable application!");
        mainMenu();
    }
}