# QuickManager

QuickManager là ứng dụng desktop quản lý bán hàng siêu thị/minimart được xây dựng bằng JavaFX, Maven và SQL Server. Ứng dụng tập trung vào các nghiệp vụ vận hành cửa hàng như bán hàng, nhập hàng, quản lý sản phẩm, quản lý nhân viên, tra cứu hóa đơn, thống kê doanh thu, quét mã vạch và gửi email hóa đơn.

## Mục tiêu dự án

- Quản lý dữ liệu bán hàng tập trung trên SQL Server.
- Hỗ trợ thao tác bán hàng nhanh qua giỏ hàng, tìm kiếm sản phẩm, chọn khách hàng và tính tiền thừa.
- Theo dõi tồn kho, nhập hàng từ nhà cung cấp và tạo sản phẩm mới.
- Quản lý sản phẩm, danh mục, nhân viên, tài khoản và quyền truy cập.
- Tra cứu hóa đơn, xem chi tiết hóa đơn và xuất phiếu hóa đơn dạng PDF.
- Thống kê doanh thu, số hóa đơn, số lượng sản phẩm bán ra, top sản phẩm và cảnh báo tồn kho thấp.
- Hỗ trợ quét barcode bằng webcam.
- Hỗ trợ gửi email thông báo hóa đơn đã thanh toán.

## Công nghệ sử dụng

| Nhóm | Công nghệ |
| --- | --- |
| Ngôn ngữ | Java 26 |
| Giao diện | JavaFX 26.0.1, FXML, CSS |
| Build tool | Maven |
| Cơ sở dữ liệu | Microsoft SQL Server |
| JDBC driver | `mssql-jdbc` |
| Bảo mật mật khẩu | Spring Security Crypto, BCrypt |
| Quét mã vạch | `webcam-capture`, ZXing |
| Email | Jakarta Mail |
| Kiến trúc UI | JavaFX Application + FXML Controller |

## Cấu trúc thư mục

```text
Quick_Manager/
+-- pom.xml
+-- README.md
+-- src/
|   +-- main/
|       +-- java/com/quickmanager/
|       |   +-- Main.java
|       |   +-- config/
|       |   +-- controller/
|       |   +-- debug/
|       |   +-- model/
|       |   +-- service/
|       |   +-- ui/
|       +-- resources/
|           +-- view/
|           +-- images/
|           +-- sql.sql
|           +-- seed.sql
|           +-- update_dates.sql
|           +-- mail.properties
+-- target/
```

### Các package chính

| Package | Vai trò |
| --- | --- |
| `com.quickmanager` | Entry point của ứng dụng JavaFX. |
| `com.quickmanager.config` | Cấu hình kết nối SQL Server và cấu hình email. |
| `com.quickmanager.controller` | Controller cho các màn hình FXML. |
| `com.quickmanager.model` | Model ánh xạ dữ liệu nghiệp vụ. |
| `com.quickmanager.service` | Lớp xử lý nghiệp vụ và truy vấn database. |
| `com.quickmanager.debug` | Tiện ích hỗ trợ log, alert, hash mật khẩu, quét barcode. |
| `com.quickmanager.ui` | Hiệu ứng giao diện JavaFX. |

## Kiến trúc tổng quan

Ứng dụng đi theo mô hình gần với MVC:

- `FXML/CSS`: định nghĩa giao diện và style.
- `Controller`: nhận sự kiện từ UI, validate input cơ bản, gọi service và cập nhật giao diện.
- `Service`: chứa logic nghiệp vụ, truy vấn SQL, tạo hóa đơn, thống kê, gửi email, xuất PDF.
- `Model`: biểu diễn dữ liệu như sản phẩm, hóa đơn, nhân viên, khách hàng, phiếu nhập.
- `DBConnection`: tạo kết nối JDBC đến SQL Server.
- `SessionService`: lưu tài khoản đang đăng nhập trong phiên chạy hiện tại.

Luồng khởi động:

1. `Main` mở màn hình `/view/login.fxml`.
2. Người dùng đăng nhập qua `LoginController`.
3. `AuthService` kiểm tra tài khoản trong bảng `TAI_KHOAN` và xác thực mật khẩu bằng BCrypt.
4. Nếu đăng nhập thành công, tài khoản được lưu trong `SessionService`.
5. Ứng dụng chuyển sang `/view/dashboard.fxml`.
6. `DashBoardController` hiển thị menu theo vai trò và tải trang chủ.

## Chức năng chính

### 1. Đăng nhập và đăng ký

File liên quan:

- `LoginController`
- `RegisterController`
- `AuthService`
- `PasswordUtils`
- `SessionService`

Chức năng:

- Đăng nhập bằng tên đăng nhập và mật khẩu.
- Mật khẩu lưu trong database ở dạng BCrypt hash.
- Đăng ký tài khoản mới cho nhân viên đang có trong hệ thống.
- Tài khoản đăng ký mới mặc định có vai trò `NHANVIEN`.
- Lưu phiên đăng nhập bằng `SessionService`.

### 2. Dashboard và phân quyền menu

File liên quan:

- `DashBoardController`
- `ViewManager`
- `dashboard.fxml`

Chức năng:

- Hiển thị tài khoản và vai trò đang đăng nhập.
- Điều hướng các màn hình trong vùng nội dung chính.
- Cache FXML view bằng `ViewManager` để giảm tải lại màn hình.
- Hỗ trợ đổi theme tối/sáng bằng `index.css` và `light.css`.
- Hỗ trợ kéo cửa sổ, thu nhỏ, phóng to và đóng ứng dụng do stage dùng kiểu `TRANSPARENT`.

Phân quyền hiện tại:

| Vai trò | Menu hiển thị |
| --- | --- |
| `ADMIN` | Trang chủ, Bán hàng, Nhập hàng, Hóa đơn, Quản lý sản phẩm, Quản lý nhân viên, Thống kê |
| Vai trò khác | Trang chủ, Bán hàng, Nhập hàng |

### 3. Trang chủ

File liên quan:

- `HomeController`
- `home.fxml`
- `InvoiceService`
- `ProductService`

Chức năng:

- Hiển thị doanh thu hôm nay.
- Hiển thị số đơn hàng hôm nay.
- Hiển thị số sản phẩm bán ra.
- Hiển thị số sản phẩm sắp hết hàng.
- Biểu đồ doanh thu 7 ngày.
- Biểu đồ số hóa đơn 7 ngày.
- Biểu đồ sản phẩm bán ra 7 ngày.
- Biểu đồ top sản phẩm trong 30 ngày.

### 4. Bán hàng

File liên quan:

- `SellController`
- `sell.fxml`
- `ProductService`
- `CustomerService`
- `InvoiceService`
- `EmailService`
- `BarcodeScanner`

Chức năng:

- Tìm kiếm sản phẩm theo tên/mã.
- Lọc sản phẩm theo danh mục.
- Quét barcode bằng webcam để tìm sản phẩm.
- Thêm sản phẩm vào giỏ hàng với số lượng nhập nhanh.
- Xóa từng dòng hoặc xóa toàn bộ giỏ hàng.
- Chọn khách hàng có sẵn hoặc nhập thông tin khách mới.
- Nhập Gmail để gửi hóa đơn sau khi thanh toán.
- Tính tạm tính, giảm giá, tổng tiền và tiền thối.
- Tạo hóa đơn và chi tiết hóa đơn trong database.
- Trừ tồn kho sau khi bán.
- Gửi email hóa đơn bất đồng bộ nếu cấu hình mail được bật.

### 5. Nhập hàng

File liên quan:

- `ImportController`
- `import.fxml`
- `SupplierService`
- `ProductService`
- `BarcodeScanner`

Chức năng:

- Tìm sản phẩm để nhập thêm.
- Quét barcode khi nhập hàng.
- Thêm sản phẩm vào danh sách phiếu nhập.
- Tính tổng tiền nhập hàng.
- Chọn nhà cung cấp có sẵn.
- Tạo nhà cung cấp mới nếu cần.
- Tạo sản phẩm mới ngay trên màn hình nhập hàng.
- Ghi phiếu nhập và chi tiết phiếu nhập vào database.
- Cập nhật tồn kho sau khi nhập.

### 6. Quản lý sản phẩm

File liên quan:

- `ProductController`
- `product.fxml`
- `ProductService`

Chức năng:

- Danh sách sản phẩm.
- Tìm kiếm theo tên, mã hoặc barcode.
- Lọc theo danh mục và trạng thái.
- Xem thông tin sản phẩm khi chọn trên bảng.
- Cập nhật tên, danh mục, đơn vị tính, tồn kho, giá nhập, giá bán và trạng thái.
- Hiển thị tổng số sản phẩm theo kết quả lọc.

### 7. Quản lý nhân viên

File liên quan:

- `EmployeeController`
- `employee.fxml`
- `EmployeeService`

Chức năng:

- Danh sách nhân viên.
- Tìm kiếm theo tên, số điện thoại hoặc email.
- Lọc theo trạng thái.
- Thêm nhân viên mới.
- Cập nhật thông tin nhân viên.
- Làm mới form nhập liệu.
- Hiển thị tổng số nhân viên theo kết quả lọc.

### 8. Hóa đơn

File liên quan:

- `InvoiceController`
- `invoice.fxml`
- `InvoiceService`
- `InvoicePdfService`

Chức năng:

- Tìm hóa đơn theo mã hóa đơn.
- Tìm hóa đơn theo tên khách hàng.
- Lọc hóa đơn theo khoảng ngày.
- Xem danh sách hóa đơn.
- Xem chi tiết từng hóa đơn.
- Hiển thị thông tin khách hàng của hóa đơn.
- Hiển thị tạm tính, giảm giá và tổng cộng.
- Xuất/in phiếu hóa đơn dạng PDF.

### 9. Thống kê

File liên quan:

- `StatisticsController`
- `statistics.fxml`
- `InvoiceService`
- `ProductService`

Chức năng:

- Chọn khoảng thời gian thống kê.
- Xem tổng doanh thu.
- Xem số hóa đơn.
- Xem số sản phẩm bán ra.
- Bảng doanh thu theo ngày.
- Bảng top sản phẩm bán chạy.
- Bảng cảnh báo tồn kho thấp.

### 10. Email hóa đơn

File liên quan:

- `MailConfig`
- `EmailService`
- `mail.properties`

Chức năng:

- Gửi email thông báo hóa đơn đã thanh toán.
- Có thể cấu hình qua `src/main/resources/mail.properties`.
- Có thể override bằng biến môi trường.
- Hỗ trợ cấu hình `mail.auth.code` dạng Base64 của chuỗi `username:password`.

Các biến môi trường được hỗ trợ:

| Biến môi trường | Ý nghĩa |
| --- | --- |
| `MAIL_ENABLED` | Bật/tắt gửi email. |
| `MAIL_SMTP_HOST` | SMTP host. |
| `MAIL_SMTP_PORT` | SMTP port. |
| `MAIL_SMTP_AUTH` | Bật/tắt SMTP auth. |
| `MAIL_SMTP_STARTTLS` | Bật/tắt STARTTLS. |
| `MAIL_AUTH_CODE` | Base64 của `username:password`. |
| `MAIL_USERNAME` | Tài khoản SMTP. |
| `MAIL_PASSWORD` | Mật khẩu/app password SMTP. |
| `MAIL_FROM` | Địa chỉ gửi. |
| `MAIL_FROM_NAME` | Tên người gửi. |

## Cơ sở dữ liệu

Database mặc định: `QL_SIEU_THI`

File tạo schema:

- `src/main/resources/sql.sql`

File seed dữ liệu:

- `src/main/resources/seed.sql`

File cập nhật ngày dữ liệu:

- `src/main/resources/update_dates.sql`

### Các bảng chính

| Bảng | Mục đích |
| --- | --- |
| `NHAN_VIEN` | Lưu thông tin nhân viên. |
| `TAI_KHOAN` | Lưu tài khoản đăng nhập, mật khẩu hash, vai trò. |
| `DANH_MUC` | Lưu danh mục sản phẩm. |
| `SAN_PHAM` | Lưu thông tin sản phẩm, giá, tồn kho, barcode, mức tồn tối thiểu. |
| `NHA_CUNG_CAP` | Lưu thông tin nhà cung cấp. |
| `PHIEU_NHAP` | Lưu phiếu nhập hàng. |
| `CT_PHIEU_NHAP` | Lưu chi tiết phiếu nhập. |
| `KHACH_HANG` | Lưu thông tin khách hàng và điểm tích lũy. |
| `HOA_DON` | Lưu hóa đơn bán hàng. |
| `CT_HOA_DON` | Lưu chi tiết hóa đơn. |

### Quan hệ dữ liệu chính

- `SAN_PHAM.MaDanhMuc` tham chiếu `DANH_MUC.MaDanhMuc`.
- `TAI_KHOAN.MaNhanVien` tham chiếu `NHAN_VIEN.MaNhanVien`.
- `PHIEU_NHAP.MaNhanVien` tham chiếu `NHAN_VIEN.MaNhanVien`.
- `PHIEU_NHAP.MaNCC` tham chiếu `NHA_CUNG_CAP.MaNCC`.
- `CT_PHIEU_NHAP.MaPhieuNhap` tham chiếu `PHIEU_NHAP.MaPhieuNhap`.
- `CT_PHIEU_NHAP.MaSanPham` tham chiếu `SAN_PHAM.MaSanPham`.
- `HOA_DON.MaNhanVien` tham chiếu `NHAN_VIEN.MaNhanVien`.
- `HOA_DON.MaKhachHang` tham chiếu `KHACH_HANG.MaKhachHang`.
- `CT_HOA_DON.MaHoaDon` tham chiếu `HOA_DON.MaHoaDon`.
- `CT_HOA_DON.MaSanPham` tham chiếu `SAN_PHAM.MaSanPham`.

## Cấu hình kết nối database

Kết nối hiện được cấu hình trong `DBConnection`:

```text
jdbc:sqlserver://localhost:1433;databaseName=QL_SIEU_THI;encrypt=true;trustServerCertificate=true
username: sa
password: 123456
```

Yêu cầu local:

- SQL Server đang chạy ở `localhost:1433`.
- Đã bật SQL Server Authentication.
- Tài khoản `sa` có mật khẩu khớp cấu hình.
- Database `QL_SIEU_THI` đã được tạo bằng `sql.sql`.

Nếu môi trường khác mật khẩu hoặc host, cần sửa `DBConnection.java` hoặc refactor sang đọc từ biến môi trường/file cấu hình.

## Cài đặt và chạy dự án

### 1. Yêu cầu

- JDK 26.
- Maven.
- SQL Server.
- Webcam nếu muốn dùng chức năng quét barcode.
- Tài khoản SMTP hoặc Gmail App Password nếu muốn gửi email hóa đơn.

### 2. Tạo database

Chạy script theo thứ tự trong SQL Server Management Studio hoặc công cụ SQL tương đương:

```sql
-- Tạo database và bảng
src/main/resources/sql.sql

-- Nạp dữ liệu mẫu
src/main/resources/seed.sql
```

Lưu ý: `sql.sql` có đoạn drop database nếu `QL_SIEU_THI` đã tồn tại. Không chạy script này trên database thật nếu chưa sao lưu.

### 3. Cài dependency

```bash
mvn clean compile
```

### 4. Chạy ứng dụng

```bash
mvn javafx:run
```

Ứng dụng sẽ mở màn hình đăng nhập JavaFX.

## Tài khoản dữ liệu mẫu

Trong `seed.sql`, các tài khoản mẫu được seed với mật khẩu đã hash bằng BCrypt. Comment trong file seed cho biết mật khẩu mẫu là:

```text
123456
```

Một số tài khoản mẫu:

| Tài khoản | Vai trò |
| --- | --- |
| `admin` | `ADMIN` |
| `ketoan.lan` | `KETOAN` |
| `thukho.bao` | `THUKHO` |
| `thungan.ha` | `THUNGAN` |
| `banhang.anh` | `BANHANG` |
| `cskh.dung` | `CSKH` |
| `giamsat.huy` | `GIAMSAT` |

## Các model nghiệp vụ

| Model | Mô tả |
| --- | --- |
| `TaiKhoan` | Tên đăng nhập, mật khẩu hash, mã nhân viên, vai trò, trạng thái. |
| `NhanVien` | Thông tin nhân viên, ngày sinh, giới tính, liên hệ, lương, trạng thái. |
| `SanPham` | Thông tin sản phẩm, danh mục, giá nhập/bán, tồn kho, barcode, hạn dùng. |
| `DanhMuc` | Danh mục sản phẩm. |
| `KhachHang` | Thông tin khách hàng, số điện thoại, email, điểm tích lũy, trạng thái. |
| `NhaCungCap` | Thông tin nhà cung cấp. |
| `PhieuNhap` | Phiếu nhập hàng. |
| `CT_PhieuNhap` | Chi tiết phiếu nhập. |
| `HoaDon` | Hóa đơn bán hàng, tổng tiền, giảm giá, tiền khách đưa, tiền thối. |
| `CT_HoaDon` | Chi tiết hóa đơn. |
| `GioHangItem` | Dòng sản phẩm trong giỏ hàng. |
| `DoanhThuNgay` | Dữ liệu thống kê doanh thu theo ngày. |
| `TopSanPham` | Dữ liệu top sản phẩm bán chạy. |
| `CanhBaoTonKho` | Dữ liệu cảnh báo sản phẩm tồn kho thấp. |

## Các service chính

| Service | Trách nhiệm |
| --- | --- |
| `AuthService` | Đăng nhập, kiểm tra trùng username, đăng ký tài khoản. |
| `PasswordUtils` | Hash và kiểm tra mật khẩu BCrypt. |
| `SessionService` | Lưu/xóa tài khoản đang đăng nhập. |
| `ProductService` | Truy vấn, tìm kiếm, cập nhật, tạo sản phẩm, tìm barcode, cảnh báo tồn kho. |
| `EmployeeService` | Truy vấn, thêm và cập nhật nhân viên. |
| `CustomerService` | Load và tạo khách hàng. |
| `SupplierService` | Load/tạo nhà cung cấp, tạo phiếu nhập, cập nhật tồn kho nhập. |
| `InvoiceService` | Tạo hóa đơn, tạo chi tiết hóa đơn, trừ tồn kho, truy vấn hóa đơn, thống kê. |
| `InvoicePdfService` | Render nội dung hóa đơn và xuất PDF. |
| `EmailService` | Tạo nội dung và gửi email hóa đơn. |

## Giao diện và tài nguyên

Các màn hình FXML nằm trong `src/main/resources/view`:

| File | Màn hình |
| --- | --- |
| `login.fxml` | Đăng nhập. |
| `register.fxml` | Đăng ký tài khoản. |
| `dashboard.fxml` | Layout chính sau đăng nhập. |
| `home.fxml` | Trang chủ và biểu đồ tổng quan. |
| `sell.fxml` | Bán hàng. |
| `import.fxml` | Nhập hàng. |
| `invoice.fxml` | Tra cứu và in hóa đơn. |
| `product.fxml` | Quản lý sản phẩm. |
| `employee.fxml` | Quản lý nhân viên. |
| `statistics.fxml` | Thống kê. |

Style:

- `index.css`: theme tối.
- `light.css`: theme sáng.

Ảnh/icon nằm trong `src/main/resources/images`.

## Luồng nghiệp vụ tiêu biểu

### Luồng bán hàng

1. Nhân viên đăng nhập.
2. Mở màn hình Bán hàng.
3. Tìm sản phẩm hoặc quét barcode.
4. Thêm sản phẩm vào giỏ.
5. Chọn hoặc nhập thông tin khách hàng.
6. Nhập giảm giá và tiền khách đưa.
7. Xác nhận thanh toán.
8. Hệ thống tạo hóa đơn, tạo chi tiết hóa đơn và trừ tồn kho.
9. Nếu có email hợp lệ và mail được bật, hệ thống gửi email hóa đơn.

### Luồng nhập hàng

1. Nhân viên mở màn hình Nhập hàng.
2. Chọn sản phẩm có sẵn hoặc tạo sản phẩm mới.
3. Nhập số lượng nhập.
4. Chọn hoặc tạo nhà cung cấp.
5. Xác nhận nhập.
6. Hệ thống tạo phiếu nhập, tạo chi tiết phiếu nhập và tăng tồn kho sản phẩm.

### Luồng thống kê

1. Người dùng chọn khoảng ngày.
2. Chọn kiểu thống kê nếu cần.
3. Hệ thống truy vấn hóa đơn và chi tiết hóa đơn.
4. Giao diện hiển thị tổng doanh thu, số hóa đơn, sản phẩm bán ra, top sản phẩm và cảnh báo tồn kho.

## Lưu ý kỹ thuật

- Dự án hiện chưa có test tự động trong `src/test`.
- Kết nối database đang hardcode trong `DBConnection`.
- Một số dữ liệu seed/comment có thể bị lỗi encoding khi xem bằng terminal không đúng UTF-8, nhưng file nguồn vẫn là tài nguyên chính của dự án.
- `target/` là thư mục build output và đã được ignore.
- `mail.properties` nên tránh commit thông tin thật nếu có SMTP credential.
- Chức năng quét barcode mở cửa sổ Swing riêng để lấy hình ảnh webcam, sau đó trả barcode về JavaFX controller.
- `ViewManager` cache view theo title; nếu view giữ state cũ, cần gọi `ViewManager.clearCache()` khi muốn reset.

## Hướng phát triển đề xuất

- Chuyển cấu hình database sang biến môi trường hoặc file cấu hình không commit.
- Bổ sung unit/integration test cho service quan trọng như `InvoiceService`, `ProductService`, `AuthService`.
- Chuẩn hóa phân quyền theo từng vai trò thay vì chỉ tách `ADMIN` và non-admin.
- Tách lớp repository/DAO khỏi service để dễ test và bảo trì.
- Bổ sung migration tool như Flyway hoặc Liquibase.
- Thêm logging rõ hơn cho các luồng thanh toán, nhập hàng và gửi email.
- Bổ sung validate dữ liệu nhất quán ở service layer, không chỉ ở controller.
- Đóng gói ứng dụng desktop bằng `jlink`, `jpackage` hoặc installer riêng.
