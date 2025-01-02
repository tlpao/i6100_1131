<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vulnerable JavaScript Example</title>
    <script>
        // 模擬用戶資料庫
        const users = [
            { username: "admin", password: "password123" },
            { username: "user", password: "user123" }
        ];

        // 用戶驗證功能
        function login() {
            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;

            // 不安全：用戶輸入未經過濾直接使用
            const user = users.find(user => user.username === username && user.password === password);

            if (user) {
                // 不安全：直接插入 HTML，存在 XSS 風險
                document.getElementById("output").innerHTML = `<p>Welcome, ${username}!</p>`;
            } else {
                document.getElementById("output").innerHTML = "<p>Invalid username or password.</p>";
            }
        }

        // 顯示用戶提交的內容（模擬評論功能）
        function submitComment() {
            const comment = document.getElementById("comment").value;

            // 不安全：用戶輸入未經消毒，可能導致 XSS 攻擊
            const commentHtml = `<p>${comment}</p>`;
            document.getElementById("comments").innerHTML += commentHtml;
        }
    </script>
</head>
<body>
    <h1>Vulnerable JavaScript Application</h1>

    <h2>Login</h2>
    <form onsubmit="login(); return false;">
        <label for="username">Username:</label>
        <input type="text" id="username" required>
        <br>
        <label for="password">Password:</label>
        <input type="password" id="password" required>
        <br>
        <button type="submit">Login</button>
    </form>
    <div id="output"></div>

    <h2>Submit a Comment</h2>
    <form onsubmit="submitComment(); return false;">
        <textarea id="comment" placeholder="Write your comment here..." required></textarea>
        <br>
        <button type="submit">Submit Comment</button>
    </form>
    <div id="comments"></div>
</body>
</html>
