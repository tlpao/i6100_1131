#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void login() {
    char username[20];
    char password[20];
    char query[100]; // 用來模擬 SQL 查詢字串

    printf("Enter username: ");
    gets(username);  // 不安全：可能導致緩衝區溢出

    printf("Enter password: ");
    gets(password);  // 不安全：可能導致緩衝區溢出

    // 模擬 SQL 查詢（實際上不會執行）
    snprintf(query, sizeof(query), "SELECT * FROM users WHERE username='%s' AND password='%s'", username, password);

    printf("Generated Query: %s\n", query);

    // 如果這是一個真實的 SQL 執行語句，這裡存在 SQL 注入的風險！
    if (strcmp(username, "admin") == 0 && strcmp(password, "password123") == 0) {
        printf("Login successful!\n");
    } else {
        printf("Invalid username or password.\n");
    }
}

void readFile() {
    char filename[50];
    FILE *file;

    printf("Enter the file name to read: ");
    gets(filename); // 不安全：可能導致緩衝區溢出

    // 不檢查檔案名稱，可能導致目錄遍歷攻擊
    file = fopen(filename, "r");
    if (file == NULL) {
        printf("Error: Could not open file %s\n", filename);
        return;
    }

    printf("Contents of %s:\n", filename);
    char line[256];
    while (fgets(line, sizeof(line), file)) {
        printf("%s", line);
    }

    fclose(file);
}

void menu() {
    int choice;
    while (1) {
        printf("\n=== Main Menu ===\n");
        printf("1. Login\n");
        printf("2. Read a file\n");
        printf("3. Exit\n");
        printf("Enter your choice: ");
        scanf("%d", &choice);
        getchar(); // 清除緩衝區中的換行符號

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                readFile();
                break;
            case 3:
                printf("Exiting...\n");
                exit(0);
                break;
            default:
                printf("Invalid choice! Please try again.\n");
        }
    }
}

int main() {
    printf("Welcome to the vulnerable program!\n");
    menu();
    return 0;
}
