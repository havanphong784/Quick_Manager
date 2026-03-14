-- ========================================
-- TẠO DATABASE
-- ========================================
CREATE DATABASE QL_SIEU_THI;
GO

USE QL_SIEU_THI;
GO

-- ========================================
-- BẢNG NHÂN VIÊN
-- ========================================
CREATE TABLE NHAN_VIEN (
                           MaNhanVien INT IDENTITY(1,1) PRIMARY KEY,
                           TenNhanVien NVARCHAR(100) NOT NULL,
                           NgaySinh DATE,
                           GioiTinh NVARCHAR(10),
                           SoDienThoai VARCHAR(15),
                           Email VARCHAR(100),
                           DiaChi NVARCHAR(200),
                           ChucVu NVARCHAR(50),
                           Luong DECIMAL(12,2),
                           TrangThai NVARCHAR(50)
);

-- ========================================
-- BẢNG TÀI KHOẢN
-- ========================================
CREATE TABLE TAI_KHOAN (
                           TenDangNhap VARCHAR(50) PRIMARY KEY,
                           MatKhau VARCHAR(100) NOT NULL,
                           MaNhanVien INT UNIQUE,
                           VaiTro NVARCHAR(50),
                           TrangThai NVARCHAR(50),
                           CONSTRAINT FK_TK_NV FOREIGN KEY (MaNhanVien)
                               REFERENCES NHAN_VIEN(MaNhanVien)
);

-- ========================================
-- BẢNG DANH MỤC
-- ========================================
CREATE TABLE DANH_MUC (
                          MaDanhMuc INT IDENTITY(1,1) PRIMARY KEY,
                          TenDanhMuc NVARCHAR(100) NOT NULL,
                          MoTa NVARCHAR(200),
                          TrangThai NVARCHAR(50)
);

-- ========================================
-- BẢNG SẢN PHẨM
-- ========================================
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
                          TrangThai NVARCHAR(50),
                          CONSTRAINT FK_SP_DM FOREIGN KEY (MaDanhMuc)
                              REFERENCES DANH_MUC(MaDanhMuc)
);

-- ========================================
-- BẢNG NHÀ CUNG CẤP
-- ========================================
CREATE TABLE NHA_CUNG_CAP (
                              MaNCC INT IDENTITY(1,1) PRIMARY KEY,
                              TenNCC NVARCHAR(150) NOT NULL,
                              SoDienThoai VARCHAR(15),
                              Email VARCHAR(100),
                              DiaChi NVARCHAR(200),
                              TrangThai NVARCHAR(50)
);

-- ========================================
-- BẢNG PHIẾU NHẬP
-- ========================================
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

-- ========================================
-- CHI TIẾT PHIẾU NHẬP
-- ========================================
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

-- ========================================
-- HÓA ĐƠN
-- ========================================
CREATE TABLE HOA_DON (
                         MaHoaDon INT IDENTITY(1,1) PRIMARY KEY,
                         MaNhanVien INT,
                         NgayLap DATETIME DEFAULT GETDATE(),
                         TongTien DECIMAL(14,2),
                         TienKhachDua DECIMAL(14,2),
                         TienThoi DECIMAL(14,2),
                         TrangThai NVARCHAR(50),
                         CONSTRAINT FK_HD_NV FOREIGN KEY (MaNhanVien)
                             REFERENCES NHAN_VIEN(MaNhanVien)
);

-- ========================================
-- CHI TIẾT HÓA ĐƠN
-- ========================================
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