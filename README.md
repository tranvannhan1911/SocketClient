## Socket Client

1. Lấy địa chỉ IP của máy server bằng cách mở CMD trên máy server và chạy lệnh sau
    ```
    ipconfig
    ```
    ![](https://i.imgur.com/NCKUQ80.png)

2. Sao chép IP dán vào file Client.java
    ```
    socket = new Socket("192.168.1.124", 9999);
    ```

3. Khởi chạy chương trình phía server, sau đó chạy chương trình phía Client