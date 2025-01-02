<?php
// Database connection details (不安全：敏感資訊硬編碼)
$servername = "localhost";
$username = "root";
$password = "password123";
$dbname = "testdb";

// 建立資料庫連線
$conn = new mysqli($servername, $username, $password, $dbname);

// 檢查連線
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// 用戶登入功能
function login($conn) {
    if ($_SERVER["REQUEST_METHOD"] === "POST" && isset($_POST["username"]) && isset($_POST["password"])) {
        $username = $_POST["username"];
        $password = $_POST["password"];

        // 不安全：使用直接拼接的 SQL 查詢，容易被注入攻擊
        $sql = "SELECT * FROM users WHERE username = '$username' AND password = '$password'";
        $result = $conn->query($sql);

        echo "<p>Executed SQL: $sql</p>"; // 暴露敏感 SQL 查詢給攻擊者

        if ($result && $result->num_rows > 0) {
            echo "<p>Login successful! Welcome, " . htmlspecialchars($username) . ".</p>";
        } else {
            echo "<p>Invalid username or password.</p>";
        }
    }
}

// 讀取檔案功能
function readFileContent() {
    if ($_SERVER["REQUEST_METHOD"] === "POST" && isset($_POST["filename"])) {
        $filename = $_POST["filename"];

        // 不安全：未對檔案名稱進行驗證，可能導致目錄遍歷攻擊
        if (file_exists($filename)) {
            echo "<pre>";
            readfile($filename);
            echo "</pre>";
        } else {
            echo "<p>Error: File not found!</p>";
        }
    }
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vulnerable PHP Application</title>
</head>
<body>
    <h1>Vulnerable PHP Application</h1>

    <h2>Login</h2>
    <form method="POST">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>
        <button type="submit">Login</button>
    </form>
    <?php login($conn); ?>

    <h2>Read a File</h2>
    <form method="POST">
        <label for="filename">Filename:</label>
        <input type="text" id="filename" name="filename" required><br>
        <button type="submit">Read File</button>
    </form>
    <?php readFileContent(); ?>

</body>
</html>

<?php
// 關閉資料庫連線
$conn->close();
?>