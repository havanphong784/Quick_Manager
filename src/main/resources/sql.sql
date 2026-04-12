USE master;
GO

IF DB_ID(N'QL_SIEU_THI') IS NOT NULL
BEGIN
    ALTER DATABASE QL_SIEU_THI SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE QL_SIEU_THI;
END
GO

CREATE DATABASE QL_SIEU_THI;
GO

USE QL_SIEU_THI;
GO

CREATE TABLE NHAN_VIEN (
    MaNhanVien INT IDENTITY(1,1) PRIMARY KEY,
    TenNhanVien NVARCHAR(100) NOT NULL,
    NgaySinh DATE,
    GioiTinh NVARCHAR(10),
    SoDienThoai VARCHAR(15),
    Email VARCHAR(100),
    DiaChi NVARCHAR(200),
    Luong DECIMAL(12,2),
    TrangThai NVARCHAR(50)
);
GO

CREATE TABLE DANH_MUC (
    MaDanhMuc INT IDENTITY(1,1) PRIMARY KEY,
    TenDanhMuc NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(200),
    TrangThai NVARCHAR(50)
);
GO

CREATE TABLE NHA_CUNG_CAP (
    MaNCC INT IDENTITY(1,1) PRIMARY KEY,
    TenNCC NVARCHAR(150) NOT NULL,
    SoDienThoai VARCHAR(15),
    Email VARCHAR(100),
    DiaChi NVARCHAR(200),
    TrangThai NVARCHAR(50)
);
GO

CREATE TABLE KHACH_HANG (
    MaKhachHang INT IDENTITY(1,1) PRIMARY KEY,
    TenKhachHang NVARCHAR(150) NOT NULL,
    SoDienThoai VARCHAR(15) UNIQUE,
    Email VARCHAR(100),
    DiaChi NVARCHAR(200),
    DiemTichLuy INT DEFAULT 0,
    TrangThai NVARCHAR(50)
);
GO

CREATE TABLE SAN_PHAM (
    MaSanPham INT IDENTITY(1,1) PRIMARY KEY,
    TenSanPham NVARCHAR(150) NOT NULL,
    MaDanhMuc INT,
    GiaNhap DECIMAL(12,2),
    GiaBan DECIMAL(12,2),
    SoLuongTon INT,
    DonViTinh NVARCHAR(50),
    NgaySanXuat DATE,
    HanSuDung DATE,
    Barcode NVARCHAR(100) NULL,
    MucToiThieu INT DEFAULT 0,
    TrangThai NVARCHAR(50) DEFAULT N'Đang bán',
    CONSTRAINT FK_SP_DM FOREIGN KEY (MaDanhMuc)
        REFERENCES DANH_MUC(MaDanhMuc)
);
GO

CREATE TABLE TAI_KHOAN (
    TenDangNhap VARCHAR(50) PRIMARY KEY,
    MatKhau VARCHAR(100) NOT NULL,
    MaNhanVien INT,
    VaiTro NVARCHAR(50),
    TrangThai NVARCHAR(50),
    CONSTRAINT FK_TK_NV FOREIGN KEY (MaNhanVien)
        REFERENCES NHAN_VIEN(MaNhanVien)
);
GO

CREATE TABLE PHIEU_NHAP (
    MaPhieuNhap INT IDENTITY(1,1) PRIMARY KEY,
    MaNhanVien INT,
    MaNCC INT,
    NgayNhap DATETIME DEFAULT GETDATE(),
    TongTien DECIMAL(14,2),
    TrangThai NVARCHAR(50),
    CONSTRAINT FK_PN_NV FOREIGN KEY (MaNhanVien)
        REFERENCES NHAN_VIEN(MaNhanVien),
    CONSTRAINT FK_PN_NCC FOREIGN KEY (MaNCC)
        REFERENCES NHA_CUNG_CAP(MaNCC)
);
GO

CREATE TABLE HOA_DON (
     MaHoaDon INT IDENTITY(1,1) PRIMARY KEY,
     MaNhanVien INT,
     MaKhachHang INT NULL,
     NgayLap DATETIME DEFAULT GETDATE(),
     TongTien DECIMAL(14,2) NOT NULL CONSTRAINT DF_HD_TongTien DEFAULT (0),
     GiamGia DECIMAL(14,2) NOT NULL CONSTRAINT DF_HD_GiamGia DEFAULT (0),
     TienKhachDua DECIMAL(14,2) NULL,
     TienThoi DECIMAL(14,2) NULL,
     TrangThai NVARCHAR(50),
     CONSTRAINT FK_HD_NV FOREIGN KEY (MaNhanVien)
         REFERENCES NHAN_VIEN(MaNhanVien),
     CONSTRAINT FK_HD_KH FOREIGN KEY (MaKhachHang)
         REFERENCES KHACH_HANG(MaKhachHang),
     CONSTRAINT CK_HD_TongTien_NonNegative CHECK (TongTien >= 0),
     CONSTRAINT CK_HD_GiamGia_NonNegative CHECK (GiamGia >= 0),
     CONSTRAINT CK_HD_TienKhachDua_NonNegative CHECK (TienKhachDua IS NULL OR TienKhachDua >= 0),
     CONSTRAINT CK_HD_TienThoi_NonNegative CHECK (TienThoi IS NULL OR TienThoi >= 0)
);
GO

CREATE TABLE CT_PHIEU_NHAP (
    MaPhieuNhap INT,
    MaSanPham INT,
    SoLuong INT,
    GiaNhap DECIMAL(12,2),
    ThanhTien DECIMAL(14,2),
    PRIMARY KEY (MaPhieuNhap, MaSanPham),
    CONSTRAINT FK_CTPN_PN FOREIGN KEY (MaPhieuNhap)
        REFERENCES PHIEU_NHAP(MaPhieuNhap),
    CONSTRAINT FK_CTPN_SP FOREIGN KEY (MaSanPham)
        REFERENCES SAN_PHAM(MaSanPham)
);
GO

CREATE TABLE CT_HOA_DON (
    MaHoaDon INT,
    MaSanPham INT,
    SoLuong INT,
    DonGia DECIMAL(12,2),
    ThanhTien DECIMAL(14,2),
    PRIMARY KEY (MaHoaDon, MaSanPham),
    CONSTRAINT FK_CTHD_HD FOREIGN KEY (MaHoaDon)
        REFERENCES HOA_DON(MaHoaDon),
    CONSTRAINT FK_CTHD_SP FOREIGN KEY (MaSanPham)
        REFERENCES SAN_PHAM(MaSanPham)
);
GO

-- ============================
-- SEED DATA
-- Order: NHAN_VIEN -> DANH_MUC -> NHA_CUNG_CAP -> KHACH_HANG -> SAN_PHAM -> TAI_KHOAN -> PHIEU_NHAP/CT -> HOA_DON/CT
-- ============================

BEGIN TRANSACTION;

-- Employees (NHAN_VIEN)
INSERT INTO NHAN_VIEN (TenNhanVien, NgaySinh, GioiTinh, SoDienThoai, Email, DiaChi, Luong, TrangThai) VALUES
 (N'Nguyễn Văn A', '1985-03-12', N'Nam', '0912345678', 'nguyenvana@example.com', N'123 Lê Lợi, Hà Nội', 12000000, N'Đang làm'),
 (N'Lê Thị Lan', '1990-06-25', N'Nữ', '0987654321', 'lethilan@example.com', N'45 Nguyễn Trãi, Hà Nội', 10000000, N'Đang làm'),
 (N'Trần Bảo', '1988-11-05', N'Nam', '0901122334', 'tranbao@example.com', N'78 Trần Phú, Hà Nội', 9000000, N'Đang làm'),
 (N'Phạm Thị Bích', '1993-02-14', N'Nữ', '0911223344', 'phamthibich@example.com', N'12 Hồ Tùng Mậu, Hà Nội', 8500000, N'Đang làm'),
 (N'Hoàng Minh', '1979-12-01', N'Nam', '0933111222', 'hoangminh@example.com', N'9 Phố Huế, Hà Nội', 15000000, N'Quản lý'),
 (N'Bùi Văn Huy', '1995-07-20', N'Nam', '0977888999', 'buivanhuy@example.com', N'56 Cầu Giấy, Hà Nội', 7000000, N'Đang làm'),
 (N'Ngô Thị Hằng', '1992-08-09', N'Nữ', '0965443322', 'ngothihang@example.com', N'89 Thanh Xuân, Hà Nội', 8000000, N'Đang làm'),
 (N'Vũ Thị Nga', '1987-09-30', N'Nữ', '0944778899', 'vuthinga@example.com', N'34 Hai Bà Trưng, Hà Nội', 9500000, N'Kế toán'),
 (N'Đỗ Quốc Anh', '1991-04-16', N'Nam', '0922334455', 'doquocanh@example.com', N'17 Láng Hạ, Hà Nội', 7800000, N'Nhân viên bán hàng'),
 (N'Nguyễn Thị Mai', '1994-10-05', N'Nữ', '0955667788', 'nguyenthimai@example.com', N'210 Hoàng Hoa Thám, Hà Nội', 7300000, N'Thủ kho'),
 (N'Nguyễn Văn Nam', '1998-01-18', N'Nam', '0909988776', 'nguyenvannam@example.com', N'3 Đống Đa, Hà Nội', 6800000, N'Nhân viên bán hàng'),
 (N'Phan Thanh Tú', '1986-05-03', N'Nam', '0917000123', 'phantanhtu@example.com', N'22 Yên Phụ, Hà Nội', 8200000, N'Giám sát');

-- Categories (DANH_MUC)
INSERT INTO DANH_MUC (TenDanhMuc, MoTa, TrangThai) VALUES
 (N'Đồ uống', N'Bao gồm nước ngọt, nước suối, nước trái cây', N'Đang bán'),
 (N'Thực phẩm tươi sống', N'Thịt, cá, rau củ tươi', N'Đang bán'),
 (N'Đồ khô', N'Gạo, mì, đồ hộp, gia vị', N'Đang bán'),
 (N'Mỹ phẩm & chăm sóc', N'Sản phẩm chăm sóc cá nhân', N'Đang bán'),
 (N'Đồ dùng gia đình', N'Dụng cụ, vải, đồ gia dụng nhỏ', N'Đang bán'),
 (N'Đồ ăn vặt', N'Bánh kẹo, snack', N'Đang bán'),
 (N'Sữa & bột', N'Sữa tươi, sữa bột, bột dinh dưỡng', N'Đang bán'),
 (N'Đồ uống nóng', N'Cà phê, trà hòa tan', N'Đang bán');

-- Suppliers (NHA_CUNG_CAP)
INSERT INTO NHA_CUNG_CAP (TenNCC, SoDienThoai, Email, DiaChi, TrangThai) VALUES
 (N'Công ty CP Thực phẩm A', '02488889999', 'supA@example.com', N'KCN Bắc Ninh', N'Hoạt động'),
 (N'Công ty TNHH Thực phẩm B', '02477778888', 'supB@example.com', N'KCN Hà Nội', N'Hoạt động'),
 (N'Công ty CP Sữa C', '02466667777', 'supC@example.com', N'KCN Hưng Yên', N'Hoạt động'),
 (N'Nhà cung cấp Đồ uống D', '02455556666', 'supD@example.com', N'Quận Long Biên', N'Hoạt động'),
 (N'Công ty TNHH Đồ khô E', '02444445555', 'supE@example.com', N'Quận Hà Đông', N'Ngưng cung cấp'),
 (N'Nhà sản xuất Mỹ phẩm F', '02433334444', 'supF@example.com', N'KCN Bắc Giang', N'Hoạt động'),
 (N'Nhà phân phối G', '02422223333', 'supG@example.com', N'Quận Ba Đình', N'Hoạt động'),
 (N'Nhà cung cấp H', '02411112222', 'supH@example.com', N'Quận Tây Hồ', N'Hoạt động');

-- Customers (KHACH_HANG)
INSERT INTO KHACH_HANG (TenKhachHang, SoDienThoai, Email, DiaChi, DiemTichLuy, TrangThai) VALUES
 (N'Nguyễn Văn Khách1', '0987000001', 'kh1@example.com', N'Hà Nội', 120, N'Hoạt động'),
 (N'Trần Thị Khách2', '0987000002', 'kh2@example.com', N'Hà Nội', 250, N'Hoạt động'),
 (N'Lê Văn Khách3', '0987000003', 'kh3@example.com', N'Hà Nội', 10, N'Hoạt động'),
 (N'Phạm Thị Khách4', '0987000004', 'kh4@example.com', N'Hà Nội', 0, N'Hoạt động'),
 (N'Đỗ Văn Khách5', '0987000005', 'kh5@example.com', N'Hà Nội', 500, N'Hoạt động'),
 (N'Khách VIP', '0987000006', 'vip@example.com', N'Hà Nội', 2000, N'Hoạt động'),
 (N'Khách Mới1', '0987000007', 'new1@example.com', N'Hà Nội', 5, N'Hoạt động'),
 (N'Khách Mới2', '0987000008', 'new2@example.com', N'Hà Nội', 0, N'Hoạt động'),
 (N'Khách ư', '0987000009', 'khex@example.com', N'Hà Nội', 30, N'Hoạt động'),
 (N'Khách 10', '0987000010', 'kh10@example.com', N'Hà Nội', 60, N'Hoạt động'),
 (N'Khách 11', '0987000011', 'kh11@example.com', N'Hà Nội', 15, N'Hoạt động'),
 (N'Khách 12', '0987000012', 'kh12@example.com', N'Hà Nội', 8, N'Hoạt động'),
 (N'Khách 13', '0987000013', 'kh13@example.com', N'Hà Nội', 3, N'Hoạt động'),
 (N'Khách 14', '0987000014', 'kh14@example.com', N'Hà Nội', 75, N'Hoạt động'),
 (N'Khách 15', '0987000015', 'kh15@example.com', N'Hà Nội', 9, N'Hoạt động');

-- Products (SAN_PHAM) - diverse data: with/without barcode, some low stock, some expired/future dates
INSERT INTO SAN_PHAM (TenSanPham, MaDanhMuc, GiaNhap, GiaBan, SoLuongTon, DonViTinh, NgaySanXuat, HanSuDung, Barcode, MucToiThieu, TrangThai) VALUES
 (N'Nước suối Lavie 500ml', 1, 3000, 6000, 150, N'chai', '2025-01-01', '2030-01-01', '8934567890123', 10, N'Đang bán'),
 (N'Nước ngọt Coca-Cola 330ml', 1, 5000, 12000, 80, N'lon', '2025-01-01', '2030-01-01', '8934567890124', 20, N'Đang bán'),
 (N'Trái cây đóng hộp', 3, 20000, 40000, 40, N'hộp', '2024-06-01', '2026-06-01', '8934567890125', 5, N'Đang bán'),
 (N'Gạo tám trắng 5kg', 3, 70000, 95000, 25, N'bao', '2024-03-10', '2026-03-10', '8934567890126', 5, N'Đang bán'),
 (N'Mì ăn liền Hảo Hảo', 3, 3200, 7000, 500, N'túi', '2025-01-01', '2027-01-01', '8934567890127', 50, N'Đang bán'),
 (N'Thịt heo ba chỉ 500g', 2, 45000, 90000, 0, N'kg', '2026-03-01', '2026-03-07', NULL, 5, N'Đang bán'),
 (N'Cá phi lê 1kg', 2, 80000, 140000, 5, N'kg', '2026-03-15', '2026-04-01', '8934567890130', 2, N'Đang bán'),
 (N'Bánh quy vị bơ 200g', 6, 15000, 30000, 120, N'gói', '2025-01-01', '2027-01-01', '8934567890131', 10, N'Đang bán'),
 (N'Sữa tươi Vinamilk 1L', 7, 15000, 30000, 200, N'lít', '2026-02-01', '2026-05-01', '8934567890132', 20, N'Đang bán'),
 (N'Cà phê hòa tan Trung Nguyên 100g', 8, 40000, 80000, 60, N'gói', '2024-08-01', '2027-08-01', '8934567890133', 5, N'Đang bán'),
 (N'Dầu ăn 1L', 5, 60000, 120000, 30, N'chai', '2024-05-01', '2027-05-01', '8934567890134', 5, N'Đang bán'),
 (N'Kem đánh răng 100g', 4, 12000, 25000, 300, N'ống', '2025-01-01', '2028-01-01', '8934567890135', 10, N'Đang bán'),
 (N'Tã em bé 48 miếng', 5, 80000, 160000, 45, N'cái', '2025-01-01', '2030-01-01', '8934567890136', 10, N'Đang bán'),
 (N'Bột giặt 3kg', 5, 90000, 190000, 70, N'bịch', '2024-01-01', '2029-01-01', '8934567890137', 10, N'Đang bán'),
 (N'Snack khoai tây 120g', 6, 12000, 25000, 160, N'gói', '2025-04-01', '2027-04-01', '8934567890138', 15, N'Đang bán'),
 (N'Bột chiên xù 500g', 3, 25000, 50000, 40, N'bao', '2024-09-01', '2026-09-01', '8934567890139', 8, N'Đang bán'),
 (N'Mỹ phẩm dưỡng da A', 4, 120000, 250000, 12, N'lọ', '2025-01-01', '2028-01-01', '8934567890140', 2, N'Đang bán'),
 (N'Sản phẩm test không barcode', 3, 10000, 20000, 3, N'cái', '2024-01-01', '2026-01-01', NULL, 1, N'Đang bán');

-- Add more products programmatically-like to increase dataset (repeated inserts for variety)
INSERT INTO SAN_PHAM (TenSanPham, MaDanhMuc, GiaNhap, GiaBan, SoLuongTon, DonViTinh, NgaySanXuat, HanSuDung, Barcode, MucToiThieu, TrangThai)
VALUES
 (N'Bánh mì sandwich', 6, 5000, 15000, 60, N'cái', '2026-03-20', '2026-03-25', '8934567890141', 5, N'Đang bán'),
 (N'Gà ta nguyên con 1.5kg', 2, 90000, 170000, 10, N'con', '2026-03-18', '2026-03-23', '8934567890142', 2, N'Đang bán'),
 (N'Xà phòng tắm 100g', 4, 8000, 20000, 90, N'cái', '2025-01-01', '2030-01-01', '8934567890143', 10, N'Đang bán'),
 (N'Nước mắm Phú Quốc 500ml', 3, 40000, 80000, 25, N'chai', '2024-07-01', '2028-07-01', '8934567890144', 5, N'Đang bán'),
 (N'Bánh trung thu', 6, 20000, 45000, 0, N'hộp', '2025-08-01', '2025-09-30', '8934567890145', 5, N'Ngừng bán');

-- Accounts (TAI_KHOAN) - use provided bcrypt hashes and link to employees by name
-- Provided hashes (do not re-hash here):
-- admin:$2a$10$afKR7Iaf1NGCK9aRO6MNGeskMCz6pP7754taROT9EGFEY3zkAIMEC
-- ketoan.lan:$2a$10$7B49LxBTz6o8Yv8PvVoLuONcwF/tIYPrfBDPakY0jJEJnHNSAKjFe
-- thukho.bao:$2a$10$44YXpuOqyb0iaQMCOeYsMeOZJGeoF2zAWlMsL/irZCNwuSgMX3e4S
-- thungan.ha:$2a$10$oEsC8O7bJEwyXgIHZRZFAOw.YzmtqK2LyH99JY856v7XglCv8nWGW
-- thungan.nam:$2a$10$HDeAvysgu1jO.hWx3mrYPe4K4Qq5uojcMfG29NlHENVshSgdAr.cS
-- banhang.anh:$2a$10$Gf1tq4bn3twAk/.ZkWFiT.7PscYP9QfxVtW37JEHvX3xCz/Q2uYw.
-- banhang.tung:$2a$10$sfQG6Y1ZB/uYtqf1uy6Oaul2HvQojH7rAqh9zeYTAodiuQ1dt0iHq
-- cskh.dung:$2a$10$my9Vime9n6NQ6bh3ejCjX.hZVWvzWm5z5HgepLMCsEpl0hVAlf3Oa
-- giamsat.huy:$2a$10$XKTA/F612W3PN7Y3sPTmzunVnLXmfFw7imbGJMywqTj5MSeQM8pgy
-- linh.old:$2a$10$NIAQF.EYmGdlMAC71QGKm.6ERKsmNJAs8KjHTXzBw/b3Xkh7bhqzG

-- Map accounts to existing employees by name
INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, MaNhanVien, VaiTro, TrangThai)
SELECT 'admin', '$2a$10$afKR7Iaf1NGCK9aRO6MNGeskMCz6pP7754taROT9EGFEY3zkAIMEC', MaNhanVien, N'Quản trị', N'Kích hoạt' FROM NHAN_VIEN WHERE TenNhanVien = N'Nguyễn Văn A';

INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, MaNhanVien, VaiTro, TrangThai)
SELECT 'ketoan.lan', '$2a$10$7B49LxBTz6o8Yv8PvVoLuONcwF/tIYPrfBDPakY0jJEJnHNSAKjFe', MaNhanVien, N'Kế toán', N'Kích hoạt' FROM NHAN_VIEN WHERE TenNhanVien = N'Vũ Thị Nga';

INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, MaNhanVien, VaiTro, TrangThai)
SELECT 'thukho.bao', '$2a$10$44YXpuOqyb0iaQMCOeYsMeOZJGeoF2zAWlMsL/irZCNwuSgMX3e4S', MaNhanVien, N'Thủ kho', N'Kích hoạt' FROM NHAN_VIEN WHERE TenNhanVien = N'Nguyễn Thị Mai';

INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, MaNhanVien, VaiTro, TrangThai)
SELECT 'thungan.ha', '$2a$10$oEsC8O7bJEwyXgIHZRZFAOw.YzmtqK2LyH99JY856v7XglCv8nWGW', MaNhanVien, N'Thu ngân', N'Kích hoạt' FROM NHAN_VIEN WHERE TenNhanVien = N'Nguyễn Văn Nam';

INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, MaNhanVien, VaiTro, TrangThai)
SELECT 'thungan.nam', '$2a$10$HDeAvysgu1jO.hWx3mrYPe4K4Qq5uojcMfG29NlHENVshSgdAr.cS', MaNhanVien, N'Thu ngân', N'Kích hoạt' FROM NHAN_VIEN WHERE TenNhanVien = N'Phan Thanh Tú';

INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, MaNhanVien, VaiTro, TrangThai)
SELECT 'banhang.anh', '$2a$10$Gf1tq4bn3twAk/.ZkWFiT.7PscYP9QfxVtW37JEHvX3xCz/Q2uYw.', MaNhanVien, N'Bán hàng', N'Kích hoạt' FROM NHAN_VIEN WHERE TenNhanVien = N'Đỗ Quốc Anh';

INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, MaNhanVien, VaiTro, TrangThai)
SELECT 'banhang.tung', '$2a$10$sfQG6Y1ZB/uYtqf1uy6Oaul2HvQojH7rAqh9zeYTAodiuQ1dt0iHq', MaNhanVien, N'Bán hàng', N'Kích hoạt' FROM NHAN_VIEN WHERE TenNhanVien = N'Nguyễn Văn Nam';

INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, MaNhanVien, VaiTro, TrangThai)
SELECT 'cskh.dung', '$2a$10$my9Vime9n6NQ6bh3ejCjX.hZVWvzWm5z5HgepLMCsEpl0hVAlf3Oa', MaNhanVien, N'CSKH', N'Kích hoạt' FROM NHAN_VIEN WHERE TenNhanVien = N'Lê Thị Lan';

INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, MaNhanVien, VaiTro, TrangThai)
SELECT 'giamsat.huy', '$2a$10$XKTA/F612W3PN7Y3sPTmzunVnLXmfFw7imbGJMywqTj5MSeQM8pgy', MaNhanVien, N'Giám sát', N'Kích hoạt' FROM NHAN_VIEN WHERE TenNhanVien = N'Hoàng Minh';

INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, MaNhanVien, VaiTro, TrangThai)
SELECT 'linh.old', '$2a$10$NIAQF.EYmGdlMAC71QGKm.6ERKsmNJAs8KjHTXzBw/b3Xkh7bhqzG', MaNhanVien, N'Nhân viên', N'Kích hoạt' FROM NHAN_VIEN WHERE TenNhanVien = N'Bùi Văn Huy';

-- Receipts (PHIEU_NHAP) and details
INSERT INTO PHIEU_NHAP (MaNhanVien, MaNCC, NgayNhap, TongTien, TrangThai)
VALUES
 ((SELECT TOP 1 MaNhanVien FROM NHAN_VIEN WHERE TenNhanVien = N'Nguyễn Văn A'), (SELECT TOP 1 MaNCC FROM NHA_CUNG_CAP WHERE TenNCC LIKE N'%Thực phẩm A%'), '2026-03-01', 5000000, N'Hoàn tất'),
 ((SELECT TOP 1 MaNhanVien FROM NHAN_VIEN WHERE TenNhanVien = N'Ngô Thị Hằng'), (SELECT TOP 1 MaNCC FROM NHA_CUNG_CAP WHERE TenNCC LIKE N'%Thực phẩm B%'), '2026-03-05', 3200000, N'Hoàn tất');

-- Link CT_PHIEU_NHAP to the above receipts
INSERT INTO CT_PHIEU_NHAP (MaPhieuNhap, MaSanPham, SoLuong, GiaNhap, ThanhTien)
SELECT p.MaPhieuNhap, s.MaSanPham, 100, s.GiaNhap, 100 * s.GiaNhap FROM PHIEU_NHAP p CROSS JOIN SAN_PHAM s
WHERE p.TongTien >= 3000000 AND s.TenSanPham LIKE N'%Mì%';

-- Invoices (HOA_DON) and details
INSERT INTO HOA_DON (MaNhanVien, MaKhachHang, NgayLap, TongTien, GiamGia, TienKhachDua, TienThoi, TrangThai)
VALUES
 ((SELECT TOP 1 MaNhanVien FROM NHAN_VIEN WHERE TenNhanVien = N'Đỗ Quốc Anh'), (SELECT TOP 1 MaKhachHang FROM KHACH_HANG WHERE TenKhachHang LIKE N'%VIP%'), '2026-03-10', 200000, 0, 200000, 0, N'Hoàn tất'),
 ((SELECT TOP 1 MaNhanVien FROM NHAN_VIEN WHERE TenNhanVien = N'Nguyễn Văn Nam'), (SELECT TOP 1 MaKhachHang FROM KHACH_HANG WHERE TenKhachHang LIKE N'%Khách Mới1%'), '2026-03-11', 150000, 10000, 160000, 10000, N'Hoàn tất');

INSERT INTO CT_HOA_DON (MaHoaDon, MaSanPham, SoLuong, DonGia, ThanhTien)
SELECT h.MaHoaDon, s.MaSanPham, 2, s.GiaBan, 2 * s.GiaBan FROM HOA_DON h
JOIN SAN_PHAM s ON s.TenSanPham LIKE N'%Bánh mì%' OR s.TenSanPham LIKE N'%Mì%'
WHERE h.TongTien >= 100000;

COMMIT TRANSACTION;

-- Create some helpful indexes to speed up queries in the app (non-essential but realistic)
CREATE INDEX IX_SANPHAM_BARCODE ON SAN_PHAM(Barcode);
CREATE INDEX IX_NV_Ten ON NHAN_VIEN(TenNhanVien);


