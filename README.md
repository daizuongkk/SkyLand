# SkyLand — Quản lý và kinh doanh bất động sản

Ứng dụng web hỗ trợ giới thiệu tòa nhà, tiếp nhận nhu cầu khách hàng và quản lý hoạt động kinh doanh bất động sản. Dự án gồm giao diện dành cho khách hàng và khu vực quản trị cho nhân viên, quản lý.

## Giao diện

### Trang khách hàng

Trang chủ giới thiệu dự án và điều hướng đến danh sách tòa nhà, giỏ hàng, trang liên hệ.

![Trang chủ SkyLand](src/main/resources/static/img/readme/Screenshot%202026-11-15%20104318.png)

Khách hàng có thể gửi thông tin và nhu cầu qua biểu mẫu liên hệ.

![Biểu mẫu liên hệ](src/main/resources/static/img/readme/Screenshot%202026-11-15%20104337.png)

### Trang quản trị

Tìm kiếm và quản lý tòa nhà theo thông tin như địa chỉ, diện tích, giá thuê và người quản lý.

![Danh sách và bộ lọc tòa nhà](src/main/resources/static/img/readme/Screenshot%202026-11-15%20104221.png)

![Tìm kiếm tòa nhà](src/main/resources/static/img/readme/Screenshot%202026-11-15%20104239.png)

Theo dõi thông tin và trạng thái khách hàng.

![Quản lý khách hàng](src/main/resources/static/img/readme/Screenshot%202026-11-15%20104255.png)

Quản lý tài khoản và thông tin nhân viên.

![Quản lý tài khoản](src/main/resources/static/img/readme/Screenshot%202026-11-15%20104359.png)

## Chức năng chính

- Hiển thị và tìm kiếm tòa nhà theo các tiêu chí như quận, diện tích, giá thuê, hạng và tiện ích.
- Quản lý tòa nhà: thêm, cập nhật, xóa và phân công nhân viên phụ trách.
- Quản lý khách hàng, trạng thái xử lý và phân công nhân viên.
- Quản lý tài khoản người dùng và vai trò `USER`, `STAFF`, `MANAGER`.
- Ghi nhận giao dịch và quản lý giỏ hàng/đơn hàng.
- Đăng nhập bằng tài khoản ứng dụng hoặc OAuth2 Google/Facebook.
- Bảo vệ các trang và API theo vai trò; hỗ trợ JWT cho xác thực API.

## Công nghệ

- Java 21, Spring Boot 3.4.12
- Spring MVC, Spring Data JPA, Spring Security, Thymeleaf
- MySQL
- Maven
- OAuth2 Client, JWT, ModelMapper

## Yêu cầu

- JDK 21
- Maven 3.8 trở lên (hoặc Maven Wrapper nếu được bổ sung vào dự án)
- MySQL 8.x

## Cài đặt và chạy

1. Tạo database MySQL:

   ```sql
   CREATE DATABASE estateadvance CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. Cấu hình kết nối database và thông tin OAuth2 trong `src/main/resources/application.properties`. Có thể thay đổi URL, tên đăng nhập, mật khẩu, khóa JWT và thông tin OAuth2 cho phù hợp với môi trường của bạn.

   OAuth2 Google/Facebook là tùy chọn; cấu hình thông tin client hợp lệ nếu muốn sử dụng đăng nhập qua các nhà cung cấp này.

3. Chạy ứng dụng từ thư mục dự án:

   ```bash
   mvn spring-boot:run
   ```

   Hoặc đóng gói và chạy file JAR:

   ```bash
   mvn clean package
   java -jar target/BuildingSales-0.0.1-SNAPSHOT.jar
   ```

4. Mở [http://localhost:8080](http://localhost:8080).

Hibernate hiện được cấu hình `ddl-auto=update`, do đó sẽ cập nhật schema khi ứng dụng khởi động. Hãy tạo database trước khi chạy lần đầu.

## Kiểm thử

Chạy bộ kiểm thử Maven:

```bash
mvn test
```

Một số kiểm thử tích hợp sử dụng Testcontainers với MySQL; cần có Docker chạy được để thực thi các kiểm thử này.

## Cấu trúc mã nguồn

```text
src/main/java/com/daizuongkk/building/
├── api/             # REST API quản trị
├── config/          # Cấu hình Spring, JPA và bảo mật
├── controller/      # Điều khiển trang web
├── converter/       # Chuyển đổi entity và DTO
├── entity/          # Mô hình dữ liệu
├── repository/      # Truy cập dữ liệu
└── service/         # Nghiệp vụ ứng dụng

src/main/resources/
├── static/          # CSS, JavaScript, hình ảnh
├── templates/       # Giao diện Thymeleaf
└── application.properties
```
