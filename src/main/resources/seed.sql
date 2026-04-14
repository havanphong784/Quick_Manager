GO
SET NOCOUNT ON;
GO

BEGIN TRY
BEGIN TRAN;

DELETE FROM CT_HOA_DON;
DELETE FROM HOA_DON;
DELETE FROM KHACH_HANG;
DELETE FROM CT_PHIEU_NHAP;
DELETE FROM PHIEU_NHAP;
DELETE FROM TAI_KHOAN;
DELETE FROM SAN_PHAM;
DELETE FROM DANH_MUC;
DELETE FROM NHA_CUNG_CAP;
DELETE FROM NHAN_VIEN;

SET IDENTITY_INSERT NHAN_VIEN ON;
INSERT INTO NHAN_VIEN (MaNhanVien, TenNhanVien, NgaySinh, GioiTinh, SoDienThoai, Email, DiaChi, Luong, TrangThai) VALUES
                                                                                                                      (1, N'Nguyễn Văn Minh', '1989-04-12', N'Nam', '0912345601', 'minh.nguyen@quickmart.vn', N'Quận 1, TP.HCM',  25000000, N'Đang làm'),
                                                                                                                      (2, N'Trần Thị Lan', '1992-09-08', N'Nữ', '0912345602', 'lan.tran@quickmart.vn', N'Quận 3, TP.HCM',  18000000, N'Đang làm'),
                                                                                                                      (3, N'Lê Quốc Bảo', '1995-01-20', N'Nam', '0912345604', 'bao.le@quickmart.vn', N'Quận 7, TP.HCM',  14000000, N'Đang làm'),
                                                                                                                      (4, N'Phạm Thu Hà', '1998-07-14', N'Nữ', '0912345604', 'ha.pham@quickmart.vn', N'Gò Vấp, TP.HCM',  10500000, N'Đang làm'),
                                                                                                                      (5, N'Đỗ Hoàng Nam', '1997-12-04', N'Nam', '0912345605', 'nam.do@quickmart.vn', N'Thủ Đức, TP.HCM',  10400000, N'Đang làm'),
                                                                                                                      (6, N'Võ Ngọc Anh', '1999-04-30', N'Nữ', '0912345606', 'anh.vo@quickmart.vn', N'Bình Thạnh, TP.HCM',  9800000, N'Đang làm'),
                                                                                                                      (7, N'Bùi Thanh Tùng', '1994-11-05', N'Nam', '0912345607', 'tung.bui@quickmart.vn', N'Tân Bình, TP.HCM',  9900000, N'Nghỉ phép'),
                                                                                                                      (8, N'Ngô Thùy Dung', '1996-06-25', N'Nữ', '0912345608', 'dung.ngo@quickmart.vn', N'Quận 10, TP.HCM', 11000000, N'Đang làm'),
                                                                                                                      (9, N'Huỳnh Gia Huy', '1991-02-17', N'Nam', '0912345609', 'huy.huynh@quickmart.vn', N'Quận 12, TP.HCM',  9500000, N'Nghỉ việc'),
                                                                                                                      (10, N'Linh Old', '1990-10-10', N'Nữ', '0912345610', 'linh.old@quickmart.vn', N'Quận 2, TP.HCM',  8000000, N'Ngừng sử dụng'),
                                                                                                                      (11, N'Trần Minh Tuấn', '1988-05-22', N'Nam', '0912345611', 'tuan.tran@quickmart.vn', N'Phú Nhuận, TP.HCM', 15500000, N'Đang làm'),
                                                                                                                      (12, N'Lê Thu Trang', '1993-08-14', N'Nữ', '0912345612', 'trang.le@quickmart.vn', N'Quận 4, TP.HCM', 11200000, N'Đang làm'),
                                                                                                                      (13, N'Nguyễn Công Sơn', '1996-04-28', N'Nam', '0912345613', 'son.nguyen@quickmart.vn', N'Quận 11, TP.HCM', 10800000, N'Đang làm'),
                                                                                                                      (14, N'Phan Thị Hương', '1998-11-15', N'Nữ', '0912345614', 'huong.phan@quickmart.vn', N'Tân Phú, TP.HCM', 9600000, N'Đang làm'),
                                                                                                                      (15, N'Hoàng Văn Long', '1991-07-09', N'Nam', '0912345615', 'long.hoang@quickmart.vn', N'Quận 5, TP.HCM', 10200000, N'Đang làm'),
                                                                                                                      (16, N'Dương Thị Oanh', '1995-12-20', N'Nữ', '0912345616', 'oanh.duong@quickmart.vn', N'Bình Tân, TP.HCM', 9900000, N'Đang làm'),
                                                                                                                      (17, N'Trương Minh Hiếu', '1997-06-05', N'Nam', '0912345617', 'hieu.truong@quickmart.vn', N'Nhà Bè, TP.HCM', 9700000, N'Đang làm'),
                                                                                                                      (18, N'Vũ Thị Liên', '1992-09-30', N'Nữ', '0912345618', 'lien.vu@quickmart.vn', N'Cần Thơ', 9400000, N'Đang làm'),
                                                                                                                      (19, N'Bùi Hoàng Anh', '1994-02-14', N'Nam', '0912345619', 'anh.bui@quickmart.vn', N'Vũng Tàu', 10100000, N'Đang làm'),
                                                                                                                      (20, N'Cao Thị Hà My', '1999-10-25', N'Nữ', '0912345620', 'hamy.cao@quickmart.vn', N'Biên Hòa, ĐN', 8900000, N'Đang làm');
SET IDENTITY_INSERT NHAN_VIEN OFF;

    -- Mật khẩu đã được hash bằng BCrypt (gensalt). Nếu cần thay đổi, chạy utility GenerateHashes.java để tạo hash mới. Password: 123456
INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, MaNhanVien, VaiTro, TrangThai) VALUES
                                                                                ('admin',      '$2a$10$afKR7Iaf1NGCK9aRO6MNGeskMCz6pP7754taROT9EGFEY3zkAIMEC', 1, N'ADMIN',    N'Hoạt động'),
                                                                                ('ketoan.lan', '$2a$10$7B49LxBTz6o8Yv8PvVoLuONcwF/tIYPrfBDPakY0jJEJnHNSAKjFe', 2, N'KETOAN',   N'Hoạt động'),
                                                                                ('thukho.bao', '$2a$10$44YXpuOqyb0iaQMCOeYsMeOZJGeoF2zAWlMsL/irZCNwuSgMX3e4S', 3, N'THUKHO',   N'Hoạt động'),
                                                                                ('thungan.ha', '$2a$10$oEsC8O7bJEwyXgIHZRZFAOw.YzmtqK2LyH99JY856v7XglCv8nWGW', 4, N'THUNGAN',  N'Hoạt động'),
                                                                                ('thungan.nam','$2a$10$HDeAvysgu1jO.hWx3mrYPe4K4Qq5uojcMfG29NlHENVshSgdAr.cS', 5, N'THUNGAN',  N'Hoạt động'),
                                                                                ('banhang.anh','$2a$10$Gf1tq4bn3twAk/.ZkWFiT.7PscYP9QfxVtW37JEHvX3xCz/Q2uYw.', 6, N'BANHANG',  N'Hoạt động'),
                                                                                ('banhang.tung','$2a$10$sfQG6Y1ZB/uYtqf1uy6Oaul2HvQojH7rAqh9zeYTAodiuQ1dt0iHq', 7, N'BANHANG',  N'Tạm khóa'),
                                                                                ('cskh.dung',  '$2a$10$my9Vime9n6NQ6bh3ejCjX.hZVWvzWm5z5HgepLMCsEpl0hVAlf3Oa', 8, N'CSKH',     N'Hoạt động'),
                                                                                ('giamsat.huy','$2a$10$XKTA/F612W3PN7Y3sPTmzunVnLXmfFw7imbGJMywqTj5MSeQM8pgy', 9, N'GIAMSAT',  N'Hoạt động'),
                                                                                ('linh.old',   '$2a$10$NIAQF.EYmGdlMAC71QGKm.6ERKsmNJAs8KjHTXzBw/b3Xkh7bhqzG', 10, N'BANHANG',  N'Ngừng sử dụng'),
                                                                                ('tuan.tran',  '$2a$10$afKR7Iaf1NGCK9aRO6MNGeskMCz6pP7754taROT9EGFEY3zkAIMEC', 11, N'KETOAN',   N'Hoạt động'),
                                                                                ('trang.le',   '$2a$10$7B49LxBTz6o8Yv8PvVoLuONcwF/tIYPrfBDPakY0jJEJnHNSAKjFe', 12, N'THUKHO',   N'Hoạt động'),
                                                                                ('son.nguyen', '$2a$10$44YXpuOqyb0iaQMCOeYsMeOZJGeoF2zAWlMsL/irZCNwuSgMX3e4S', 13, N'THUNGAN',  N'Hoạt động'),
                                                                                ('huong.phan', '$2a$10$oEsC8O7bJEwyXgIHZRZFAOw.YzmtqK2LyH99JY856v7XglCv8nWGW', 14, N'BANHANG',  N'Hoạt động'),
                                                                                ('long.hoang', '$2a$10$HDeAvysgu1jO.hWx3mrYPe4K4Qq5uojcMfG29NlHENVshSgdAr.cS', 15, N'BANHANG',  N'Hoạt động'),
                                                                                ('oanh.duong', '$2a$10$Gf1tq4bn3twAk/.ZkWFiT.7PscYP9QfxVtW37JEHvX3xCz/Q2uYw.', 16, N'THUNGAN',  N'Hoạt động'),
                                                                                ('hieu.truong','$2a$10$sfQG6Y1ZB/uYtqf1uy6Oaul2HvQojH7rAqh9zeYTAodiuQ1dt0iHq', 17, N'THUKHO',   N'Hoạt động'),
                                                                                ('lien.vu',    '$2a$10$my9Vime9n6NQ6bh3ejCjX.hZVWvzWm5z5HgepLMCsEpl0hVAlf3Oa', 18, N'CSKH',     N'Hoạt động'),
                                                                                ('anh.bui',    '$2a$10$XKTA/F612W3PN7Y3sPTmzunVnLXmfFw7imbGJMywqTj5MSeQM8pgy', 19, N'GIAMSAT',  N'Hoạt động'),
                                                                                ('hamy.cao',   '$2a$10$NIAQF.EYmGdlMAC71QGKm.6ERKsmNJAs8KjHTXzBw/b3Xkh7bhqzG', 20, N'BANHANG',  N'Hoạt động');

SET IDENTITY_INSERT DANH_MUC ON;
INSERT INTO DANH_MUC (MaDanhMuc, TenDanhMuc, MoTa, TrangThai) VALUES
                                                                  (1, N'Đồ uống', N'Nước ngọt, nước suối, trà đóng chai', N'Hoạt động'),
                                                                  (2, N'Thực phẩm khô', N'Mì gói, gạo, gia vị, đồ hộp', N'Hoạt động'),
                                                                  (3, N'Rau củ', N'Rau ăn lá, củ quả tươi', N'Hoạt động'),
                                                                  (4, N'Trái cây', N'Trái cây nội địa và nhập khẩu', N'Hoạt động'),
                                                                  (5, N'Sữa và chế phẩm', N'Sữa tươi, sữa chua, phô mai', N'Hoạt động'),
                                                                  (6, N'Đồ gia dụng', N'Nước rửa chén, giấy vệ sinh, túi rác', N'Hoạt động'),
                                                                  (7, N'Chăm sóc cá nhân', N'Sữa tắm, dầu gội, kem đánh răng', N'Hoạt động'),
                                                                  (8, N'Đông lạnh', N'Xúc xích, chả giò, hải sản đông lạnh', N'Hoạt động');
SET IDENTITY_INSERT DANH_MUC OFF;

    SET IDENTITY_INSERT NHA_CUNG_CAP ON;
INSERT INTO NHA_CUNG_CAP (MaNCC, TenNCC, SoDienThoai, Email, DiaChi, TrangThai) VALUES
                                                                                    (1, N'Công ty TNHH FMCG Việt', '02873001111', 'sales@fmcgviet.vn', N'KCN Tân Bình, TP.HCM', N'Hợp tác'),
                                                                                    (2, N'Công ty CP Nông Sản Xanh', '02873002222', 'contact@nongsanxanh.vn', N'Long An', N'Hợp tác'),
                                                                                    (3, N'FreshFarm Distribution', '02873004333', 'biz@freshfarm.vn', N'Đà Lạt, Lâm Đồng', N'Hợp tác'),
                                                                                    (4, N'Mega Supplier JSC', '02873004444', 'cs@megasupplier.vn', N'Biên Hòa, Đồng Nai', N'Hợp tác'),
                                                                                    (5, N'An Phát Consumer', '02873005555', 'partner@anphatconsumer.vn', N'Bình Dương', N'Tạm dừng'),
                                                                                    (6, N'Vina Cold Chain', '02873006666', 'support@vinacold.vn', N'Củ Chi, TP.HCM', N'Hợp tác'),
                                                                                    (7, N'Northern Foods Corp', '02873007777', 'sales@northernfoods.vn', N'Hà Nội', N'Hợp tác'),
                                                                                    (8, N'Green Valley Produce', '02873008888', 'contact@greenvalley.vn', N'Đà Nẵng', N'Hợp tác'),
                                                                                    (9, N'Mekong Delta Foods', '02873009999', 'export@mekongdelta.vn', N'Cần Thơ', N'Hợp tác'),
                                                                                    (10, N'Premium Import Ltd', '0287300aaaa', 'import@premiumltd.vn', N'Cát Lái, TP.HCM', N'Hợp tác'),
                                                                                    (11, N'ABC Trading Company', '0287300bbbb', 'trade@abcviet.vn', N'Quận 8, TP.HCM', N'Hợp tác'),
                                                                                    (12, N'Sunlight Beverages', '0287300cccc', 'sales@sunlightbev.vn', N'Bà Rịa - Vũng Tàu', N'Hợp tác');
SET IDENTITY_INSERT NHA_CUNG_CAP OFF;

    SET IDENTITY_INSERT KHACH_HANG ON;
INSERT INTO KHACH_HANG (MaKhachHang, TenKhachHang, SoDienThoai, Email, DiaChi, DiemTichLuy, TrangThai) VALUES
                                                                                                           (1, N'Nguyễn Thành Đạt', '0901112201', 'dat.nguyen@gmail.com', N'Quận 1, TP.HCM', 120, N'Hoạt động'),
                                                                                                           (2, N'Trần Bảo Ngọc', '0901112202', 'ngoc.tran@gmail.com', N'Quận 3, TP.HCM', 85, N'Hoạt động'),
                                                                                                           (3, N'Lê Minh Khang', '0901112203', 'khang.le@gmail.com', N'Quận 7, TP.HCM', 40, N'Hoạt động'),
                                                                                                           (4, N'Phạm Hoài Thư', '0901112204', 'thu.pham@gmail.com', N'Thủ Đức, TP.HCM', 200, N'VIP'),
                                                                                                           (5, N'Võ Quốc An', '0901112205', 'an.vo@gmail.com', N'Bình Thạnh, TP.HCM', 15, N'Hoạt động'),
                                                                                                           (6, N'Bùi Gia Hân', '0901112206', 'han.bui@gmail.com', N'Gò Vấp, TP.HCM', 60, N'Hoạt động'),
                                                                                                           (7, N'Ngô Quỳnh Mai', '0901112207', 'mai.ngo@gmail.com', N'Quận 10, TP.HCM', 30, N'Hoạt động'),
                                                                                                           (8, N'Đặng Hải Nam', '0901112208', 'nam.dang@gmail.com', N'Tân Bình, TP.HCM', 0, N'Mới'),
                                                                                                           (9, N'Huỳnh Yến Nhi', '0901112209', 'nhi.huynh@gmail.com', N'Bình Tân, TP.HCM', 150, N'VIP'),
                                                                                                           (10, N'Phan Đức Long', '0901112210', 'long.phan@gmail.com', N'Nhà Bè, TP.HCM', 10, N'Hoạt động'),
                                                                                                           (11, N'Trương Văn Tú', '0901112211', 'tu.truong@gmail.com', N'Quận 11, TP.HCM', 75, N'Hoạt động'),
                                                                                                           (12, N'Tôn Nữ Hương Giang', '0901112212', 'giang.ton@gmail.com', N'Quận 2, TP.HCM', 180, N'VIP'),
                                                                                                           (13, N'Hoàng Bảo Châu', '0901112213', 'chau.hoang@gmail.com', N'Phú Nhuận, TP.HCM', 45, N'Hoạt động'),
                                                                                                           (14, N'Đinh Thị Linh', '0901112214', 'linh.dinh@gmail.com', N'Tân Phú, TP.HCM', 90, N'Hoạt động'),
                                                                                                           (15, N'Ưu Văn Khánh', '0901112215', 'khanh.uu@gmail.com', N'Quận 4, TP.HCM', 25, N'Hoạt động'),
                                                                                                           (16, N'Nhan Thị Thu Hà', '0901112216', 'ha.nhan@gmail.com', N'Quận 5, TP.HCM', 110, N'Hoạt động'),
                                                                                                           (17, N'Lâm Quốc Bảo', '0901112217', 'bao.lam@gmail.com', N'Quận 12, TP.HCM', 55, N'Hoạt động'),
                                                                                                           (18, N'Mai Bảo Linh', '0901112218', 'linh.mai@gmail.com', N'Cần Thơ', 35, N'Hoạt động'),
                                                                                                           (19, N'Phạm Duy Tân', '0901112219', 'tan.pham@gmail.com', N'Vũng Tàu', 140, N'VIP'),
                                                                                                           (20, N'Lương Trọng Ánh', '0901112220', 'anh.luong@gmail.com', N'Biên Hòa, ĐN', 20, N'Hoạt động'),
                                                                                                           (21, N'Dương Minh Tuấn', '0901112221', 'tuan.duong@gmail.com', N'Hà Nội', 165, N'VIP'),
                                                                                                           (22, N'Nguyễn Kiều Oanh', '0901112222', 'oanh.nguyen@gmail.com', N'Đà Nẵng', 50, N'Hoạt động'),
                                                                                                           (23, N'Từ Thế Anh', '0901112223', 'anh.tu@gmail.com', N'Quận 6, TP.HCM', 0, N'Mới'),
                                                                                                           (24, N'Vương Thị Mỹ Duyên', '0901112224', 'duyen.vuong@gmail.com', N'Quận 8, TP.HCM', 95, N'Hoạt động'),
                                                                                                           (25, N'Tạ Văn Hùng', '0901112225', 'hung.ta@gmail.com', N'Quận 9, TP.HCM', 0, N'Mới'),
                                                                                                           (26, N'Ngô Thị Thanh Tâm', '0901112226', 'tam.ngo@gmail.com', N'Long Biên, Hà Nội', 125, N'Hoạt động'),
                                                                                                           (27, N'Bùi Quang Hùng', '0901112227', 'hung.bui@gmail.com', N'Ba Đình, Hà Nội', 70, N'Hoạt động'),
                                                                                                           (28, N'Hoàng Thị Quỳnh Anh', '0901112228', 'anh.hoang@gmail.com', N'Hai Bà Trưng, Hà Nội', 160, N'VIP'),
                                                                                                           (29, N'Vũ Văn Sơn', '0901112229', 'son.vu@gmail.com', N'Đống Đa, Hà Nội', 55, N'Hoạt động'),
                                                                                                           (30, N'Trang Thị Yên', '0901112230', 'yen.trang@gmail.com', N'Hai Phòng', 30, N'Hoạt động');
SET IDENTITY_INSERT KHACH_HANG OFF;

    SET IDENTITY_INSERT SAN_PHAM ON;
INSERT INTO SAN_PHAM (MaSanPham, TenSanPham, MaDanhMuc, GiaNhap, GiaBan, SoLuongTon, DonViTinh, NgaySanXuat, HanSuDung, TrangThai) VALUES
                                                                                                                                       (1, N'Nước suối Lavie 500ml', 1, 8500, 12000, 280, N'Chai', '2025-12-01', '2027-12-01', N'Đang bán'),
                                                                                                                                       (2, N'Coca Cola lon 330ml', 1, 12000, 16500, 240, N'Lon', '2025-11-20', '2026-11-20', N'Đang bán'),
                                                                                                                                       (3, N'Pepsi lon 330ml', 1, 14000, 19000, 180, N'Lon', '2025-10-15', '2026-10-15', N'Đang bán'),
                                                                                                                                       (4, N'Trà xanh C2 455ml', 1, 26000, 36000, 95, N'Chai', '2025-09-01', '2026-09-01', N'Đang bán'),
                                                                                                                                       (5, N'Sữa tươi Vinamilk 1L', 5, 28000, 39000, 75, N'Hộp', '2026-01-05', '2026-07-05', N'Đang bán'),
                                                                                                                                       (6, N'Sữa chua uống Probi', 5, 23000, 32000, 110, N'Lốc', '2025-12-10', '2026-05-10', N'Đang bán'),
                                                                                                                                       (7, N'Sữa chua có đường 4 hộp', 5, 19000, 28000, 140, N'Lốc', '2026-01-12', '2026-04-12', N'Đang bán'),
                                                                                                                                       (8, N'Phô mai lát 8 miếng', 5, 15000, 22000, 60, N'Gói', '2025-11-01', '2026-04-30', N'Ngừng kinh doanh'),
                                                                                                                                       (9, N'Mì Hảo Hảo tôm chua cay', 2, 15000, 23000, 420, N'Gói', '2025-10-01', '2026-10-01', N'Đang bán'),
                                                                                                                                       (10, N'Gạo ST25 túi 5kg', 2, 13000, 20000, 160, N'Kg', '2025-12-15', '2027-12-15', N'Đang bán'),
                                                                                                                                       (11, N'Dầu ăn Tường An 1L', 2, 18000, 27000, 130, N'Chai', '2025-08-01', '2027-08-01', N'Đang bán'),
                                                                                                                                       (12, N'Nước mắm 500ml', 2, 22000, 32000, 90, N'Chai', '2025-07-10', '2027-07-10', N'Đang bán'),
                                                                                                                                       (13, N'Táo Gala nhập khẩu', 4, 30000, 45000, 85, N'Kg', '2026-02-01', '2026-04-15', N'Đang bán'),
                                                                                                                                       (14, N'Chuối già Nam Mỹ', 4, 5000, 9000, 70, N'Kg', '2026-04-01', '2026-04-25', N'Đang bán'),
                                                                                                                                       (15, N'Cà rốt Đà Lạt', 3, 7000, 12000, 65, N'Kg', '2026-04-01', '2026-04-20', N'Đang bán'),
                                                                                                                                       (16, N'Khoai tây', 3, 9000, 15000, 95, N'Kg', '2026-02-20', '2026-04-30', N'Đang bán'),
                                                                                                                                       (17, N'Rau cải xanh', 3, 22000, 34000, 40, N'Kg', '2026-04-10', '2026-02-28', N'Hết hạn'),
                                                                                                                                       (18, N'Cà chua', 3, 35000, 52000, 0, N'Kg', '2026-04-12', '2026-04-18', N'Hết hàng'),
                                                                                                                                       (19, N'Dầu gội Sunsilk 650g', 7, 48000, 69000, 70, N'Chai', '2025-06-01', '2028-06-01', N'Đang bán'),
                                                                                                                                       (20, N'Sữa tắm Lifebuoy 850g', 7, 27000, 39000, 55, N'Chai', '2025-05-20', '2027-05-20', N'Đang bán'),
                                                                                                                                       (21, N'Kem đánh răng P/S 180g', 7, 45000, 69000, 100, N'Tuýp', '2025-04-15', '2028-04-15', N'Đang bán'),
                                                                                                                                       (22, N'Nước rửa chén Sunlight 3.6kg', 6, 32000, 49000, 45, N'Can', '2025-09-30', '2028-09-30', N'Đang bán'),
                                                                                                                                       (23, N'Giấy vệ sinh Bless You 10 cuộn', 6, 55000, 79000, 60, N'Lốc', '2025-10-20', '2028-10-20', N'Đang bán'),
                                                                                                                                       (24, N'Xúc xích tiệt trùng 500g', 8, 68000, 99000, 35, N'Gói', '2025-12-01', '2026-05-01', N'Đang bán'),
                                                                                                                                       (25, N'Sprite lon 330ml', 1, 13000, 18000, 220, N'Lon', '2025-11-15', '2026-11-15', N'Đang bán'),
                                                                                                                                       (26, N'Bia Heineken 330ml', 1, 18000, 28000, 150, N'Lon', '2025-10-20', '2026-10-20', N'Đang bán'),
                                                                                                                                       (27, N'Nước cam Minute Maid 1L', 1, 22000, 32000, 100, N'Chai', '2025-12-05', '2026-12-05', N'Đang bán'),
                                                                                                                                       (28, N'Trà sữa Ô Long Đài Loan', 1, 48000, 65000, 45, N'Hộp', '2025-11-01', '2026-11-01', N'Đang bán'),
                                                                                                                                       (29, N'Sữa bột Ensure 400g', 5, 125000, 165000, 20, N'Hộp', '2025-08-15', '2028-08-15', N'Đang bán'),
                                                                                                                                       (30, N'Pho mát Anchor 400g', 5, 65000, 95000, 35, N'Gói', '2025-09-01', '2027-09-01', N'Đang bán'),
                                                                                                                                       (31, N'Bơ Lurisia 200g', 5, 45000, 65000, 25, N'Gói', '2025-10-01', '2027-10-01', N'Đang bán'),
                                                                                                                                       (32, N'Mỳ Omachi 1kg', 2, 28000, 42000, 85, N'Kg', '2025-09-20', '2026-09-20', N'Đang bán'),
                                                                                                                                       (33, N'Hạt nêm Knorr 900g', 2, 35000, 52000, 60, N'Gói', '2025-07-01', '2027-07-01', N'Đang bán'),
                                                                                                                                       (34, N'Mắm tôm cô Bé', 2, 18000, 28000, 75, N'Hộp', '2025-08-10', '2027-08-10', N'Đang bán'),
                                                                                                                                       (35, N'Tương cà Chin Su', 2, 15000, 23000, 90, N'Hộp', '2025-09-15', '2027-09-15', N'Đang bán'),
                                                                                                                                       (36, N'Dứa tươi Lâm Đồng', 4, 15000, 25000, 40, N'Kg', '2026-04-08', '2026-04-20', N'Đang bán'),
                                                                                                                                       (37, N'Xoài Cát Chu', 4, 12000, 20000, 55, N'Kg', '2026-04-01', '2026-04-25', N'Đang bán'),
                                                                                                                                       (38, N'Ớt hiểm', 3, 18000, 30000, 35, N'Kg', '2026-04-10', '2026-04-18', N'Đang bán'),
                                                                                                                                       (39, N'Tỏi tây', 3, 25000, 40000, 20, N'Kg', '2026-02-25', '2026-04-30', N'Đang bán'),
                                                                                                                                       (40, N'Dưa chuột', 3, 8000, 14000, 50, N'Kg', '2026-04-11', '2026-04-22', N'Đang bán'),
                                                                                                                                       (41, N'Dầu gội Dove 500g', 7, 55000, 79000, 45, N'Chai', '2025-05-01', '2028-05-01', N'Đang bán'),
                                                                                                                                       (42, N'Sữa tắm Lux 650ml', 7, 32000, 48000, 60, N'Chai', '2025-06-10', '2028-06-10', N'Đang bán'),
                                                                                                                                       (43, N'Kem mặt Olay 50g', 7, 95000, 145000, 15, N'Tuýp', '2025-04-01', '2028-04-01', N'Đang bán'),
                                                                                                                                       (44, N'Xà phòng tắm Dux 80g', 7, 8000, 14000, 150, N'Cái', '2025-04-20', '2027-04-20', N'Đang bán'),
                                                                                                                                       (45, N'Nước tẩy Jex 800ml', 6, 18000, 28000, 55, N'Chai', '2025-08-01', '2028-08-01', N'Đang bán'),
                                                                                                                                       (46, N'Bụi lau nhà Vileda', 6, 45000, 68000, 30, N'Bộ', '2025-09-01', '2028-09-01', N'Đang bán'),
                                                                                                                                       (47, N'Túi rác màu đen 55L', 6, 25000, 38000, 200, N'Lốc', '2025-10-01', '2028-10-01', N'Đang bán'),
                                                                                                                                       (48, N'Khăn giấy Kleenex 3 lớp', 6, 35000, 52000, 80, N'Gói', '2025-09-15', '2028-09-15', N'Đang bán'),
                                                                                                                                       (49, N'Chả giò chiên 300g', 8, 42000, 62000, 40, N'Gói', '2025-12-10', '2026-06-10', N'Đang bán'),
                                                                                                                                       (50, N'Cánh gà nướng 500g', 8, 75000, 110000, 25, N'Gói', '2025-12-15', '2026-04-15', N'Đang bán');
SET IDENTITY_INSERT SAN_PHAM OFF;

UPDATE SAN_PHAM SET TrangThai = N'Ngừng kinh doanh' WHERE MaSanPham = 8;   -- phô mai
UPDATE SAN_PHAM SET SoLuongTon = 0, TrangThai = N'Hết hàng' WHERE MaSanPham = 18; -- cà chua
UPDATE SAN_PHAM SET HanSuDung = '2026-02-28', TrangThai = N'Hết hạn' WHERE MaSanPham = 17; -- rau cải xanh

SET IDENTITY_INSERT PHIEU_NHAP ON;
INSERT INTO PHIEU_NHAP (MaPhieuNhap, MaNhanVien, MaNCC, NgayNhap, TongTien, TrangThai) VALUES
                                                                                           (1, 3, 1, '2026-01-10 08:45:00', 3210000, N'Đã nhập'),
                                                                                           (2, 3, 1, '2026-01-15 09:30:00', 4680000, N'Đã nhập'),
                                                                                           (3, 3, 3, '2026-02-01 07:55:00', 6340000, N'Đã nhập'),
                                                                                           (4, 9, 3, '2026-02-18 10:20:00', 7220000, N'Đã nhập'),
                                                                                           (5, 9, 4, '2026-02-25 14:00:00', 10680000, N'Đã nhập'),
                                                                                           (6, 3, 2, '2026-04-01 08:10:00', 6310000, N'Đã nhập'),
                                                                                           (7, 3, 2, '2026-04-05 08:35:00', 3640000, N'Đã nhập'),
                                                                                           (8, 9, 6, '2026-04-08 15:20:00', 5780000, N'Đã nhập'),
                                                                                           (9, 3, 1, '2026-04-09 09:15:00', 4250000, N'Đã nhập'),
                                                                                           (10, 9, 5, '2026-04-11 08:30:00', 3890000, N'Đã nhập'),
                                                                                           (11, 3, 6, '2026-04-12 14:45:00', 7120000, N'Đã nhập'),
                                                                                           (12, 9, 2, '2026-04-13 10:00:00', 5640000, N'Đã nhập'),
                                                                                           (13, 3, 7, '2026-04-14 07:20:00', 8950000, N'Đã nhập');
SET IDENTITY_INSERT PHIEU_NHAP OFF;

INSERT INTO CT_PHIEU_NHAP (MaPhieuNhap, MaSanPham, SoLuong, GiaNhap, ThanhTien) VALUES
                                                                                    (1, 1, 100, 8500, 850000),
                                                                                    (1, 2, 80, 12000, 960000),
                                                                                    (1, 5, 50, 28000, 1400000),

                                                                                    (2, 9, 100, 15000, 1500000),
                                                                                    (2,10, 120, 13000, 1560000),
                                                                                    (2,11, 90, 18000, 1620000),

                                                                                    (3,13, 150, 30000, 4500000),
                                                                                    (3,14, 200, 5000, 1000000),
                                                                                    (3,15, 120, 7000, 840000),

                                                                                    (4,17, 80, 22000, 1760000),
                                                                                    (4,18, 60, 35000, 2100000),
                                                                                    (4,19, 70, 48000, 3360000),

                                                                                    (5,21, 100, 45000, 4500000),
                                                                                    (5,22, 90, 32000, 2880000),
                                                                                    (5,23, 60, 55000, 3300000),

                                                                                    (6, 6, 120, 23000, 2760000),
                                                                                    (6, 7, 100, 19000, 1900000),
                                                                                    (6, 8, 110, 15000, 1650000),

                                                                                    (7, 3, 70, 14000, 980000),
                                                                                    (7, 4, 60, 26000, 1560000),
                                                                                    (7,12, 50, 22000, 1100000),

                                                                                    (8,16, 100, 9000, 900000),
                                                                                    (8,20, 80, 27000, 2160000),
                                                                                    (8,24, 40, 68000, 2720000),

                                                                                    (9, 25, 110, 13000, 1430000),
                                                                                    (9, 26, 85, 18000, 1530000),
                                                                                    (9, 27, 65, 22000, 1430000),
                                                                                    (9, 28, 35, 48000, 1680000),

                                                                                    (10, 29, 15, 125000, 1875000),
                                                                                    (10, 30, 28, 65000, 1820000),
                                                                                    (10, 31, 20, 45000, 900000),

                                                                                    (11, 32, 75, 28000, 2100000),
                                                                                    (11, 33, 55, 35000, 1925000),
                                                                                    (11, 34, 65, 18000, 1170000),
                                                                                    (11, 35, 80, 15000, 1200000),

                                                                                    (12, 36, 45, 15000, 675000),
                                                                                    (12, 37, 60, 12000, 720000),
                                                                                    (12, 38, 40, 18000, 720000),
                                                                                    (12, 39, 25, 25000, 625000),
                                                                                    (12, 40, 55, 8000, 440000),
                                                                                    (12, 41, 50, 55000, 2750000),

                                                                                    (13, 42, 70, 32000, 2240000),
                                                                                    (13, 43, 20, 95000, 1900000),
                                                                                    (13, 44, 200, 8000, 1600000),
                                                                                    (13, 45, 65, 18000, 1170000),
                                                                                    (13, 46, 35, 45000, 1575000),
                                                                                    (13, 47, 220, 25000, 5500000);

SET IDENTITY_INSERT HOA_DON ON;
INSERT INTO HOA_DON (MaHoaDon, MaNhanVien, MaKhachHang, NgayLap, TongTien, TienKhachDua, TienThoi, TrangThai) VALUES
                                                                                                                  (1, 4, 1, '2026-04-01 08:15:00', 108000, 200000, 92000, N'Đã thanh toán'),
                                                                                                                  (2, 4, 2, '2026-04-01 09:05:00', 104000, 200000, 97000, N'Đã thanh toán'),
                                                                                                                  (3, 5, NULL, '2026-04-01 10:20:00', 93000, 100000, 7000, N'Đã thanh toán'),
                                                                                                                  (4, 5, 3, '2026-04-01 11:40:00', 86000, 100000, 14000, N'Đã thanh toán'),
                                                                                                                  (5, 4, 4, '2026-04-01 14:30:00', 197000, 200000, 3000, N'Đã thanh toán'),
                                                                                                                  (6, 5, 5, '2026-04-01 16:10:00', 159000, 200000, 41000, N'Đã thanh toán'),
                                                                                                                  (7, 4, NULL, '2026-04-02 08:45:00', 173000, 200000, 27000, N'Đã thanh toán'),
                                                                                                                  (8, 5, 6, '2026-04-02 09:55:00', 111000, 120000, 9000, N'Đã thanh toán'),
                                                                                                                  (9, 4, 7, '2026-04-02 12:15:00', 109000, 110000, 1000, N'Đã thanh toán'),
                                                                                                                  (10, 5, 8, '2026-04-02 15:50:00', 298500, 300000, 1500, N'Đã thanh toán'),
                                                                                                                  (11, 4, NULL, '2026-04-04 08:10:00', 173000, 200000, 27000, N'Đã thanh toán'),
                                                                                                                  (12, 5, 9, '2026-04-04 10:25:00', 170000, 200000, 30000, N'Đã thanh toán'),
                                                                                                                  (13, 4, NULL, '2026-04-04 11:30:00', 85000, NULL, NULL, N'Đã hủy'),
                                                                                                                  (14, 5, 10, '2026-04-04 17:20:00', 178000, NULL, NULL, N'Chờ thanh toán'),
                                                                                                                  (15, 4, 2, '2026-04-04 18:05:00', 493000, 500000, 7000, N'Đã thanh toán'),
                                                                                                                  (16, 5, 11, '2026-04-04 09:30:00', 145000, 150000, 5000, N'Đã thanh toán'),
                                                                                                                  (17, 4, 12, '2026-04-04 11:45:00', 282000, 300000, 18000, N'Đã thanh toán'),
                                                                                                                  (18, 5, 13, '2026-04-04 14:20:00', 178000, 200000, 22000, N'Đã thanh toán'),
                                                                                                                  (19, 4, 14, '2026-04-04 15:50:00', 224000, 250000, 26000, N'Đã thanh toán'),
                                                                                                                  (20, 6, 15, '2026-04-04 16:30:00', 134000, 140000, 6000, N'Đã thanh toán'),
                                                                                                                  (21, 5, 16, '2026-04-05 08:00:00', 215000, 220000, 5000, N'Đã thanh toán'),
                                                                                                                  (22, 4, 17, '2026-04-05 09:15:00', 186000, 200000, 14000, N'Đã thanh toán'),
                                                                                                                  (23, 5, 18, '2026-04-05 10:40:00', 142000, 150000, 8000, N'Đã thanh toán'),
                                                                                                                  (24, 4, 19, '2026-04-05 12:25:00', 276000, 300000, 24000, N'Đã thanh toán'),
                                                                                                                  (25, 5, 20, '2026-04-05 14:00:00', 158000, 200000, 42000, N'Đã thanh toán'),
                                                                                                                  (26, 6, 21, '2026-04-05 15:30:00', 304000, 310000, 6000, N'Đã thanh toán'),
                                                                                                                  (27, 4, 22, '2026-04-05 16:45:00', 187000, 200000, 13000, N'Đã thanh toán'),
                                                                                                                  (28, 5, 23, '2026-04-06 08:20:00', 165000, 200000, 35000, N'Đã thanh toán'),
                                                                                                                  (29, 4, 24, '2026-04-06 09:50:00', 256000, 260000, 4000, N'Đã thanh toán'),
                                                                                                                  (30, 5, 25, '2026-04-06 11:10:00', 134000, 150000, 16000, N'Đã thanh toán'),
                                                                                                                  (31, 6, 26, '2026-04-06 13:00:00', 267000, 300000, 33000, N'Đã thanh toán'),
                                                                                                                  (32, 4, 27, '2026-04-06 14:25:00', 219000, 250000, 31000, N'Đã thanh toán'),
                                                                                                                  (33, 5, 28, '2026-04-06 15:40:00', 298000, 300000, 2000, N'Đã thanh toán'),
                                                                                                                  (34, 4, 29, '2026-04-07 08:15:00', 172000, 200000, 28000, N'Đã thanh toán'),
                                                                                                                  (35, 5, 30, '2026-04-07 09:45:00', 143000, 150000, 7000, N'Đã thanh toán'),
                                                                                                                  (36, 6, 1, '2026-04-07 11:20:00', 287000, 300000, 13000, N'Đã thanh toán'),
                                                                                                                  (37, 4, 3, '2026-04-07 12:50:00', 156000, 200000, 44000, N'Đã thanh toán'),
                                                                                                                  (38, 5, 4, '2026-04-07 14:10:00', 245000, 250000, 5000, N'Đã thanh toán'),
                                                                                                                  (39, 4, 5, '2026-04-07 15:30:00', 198000, 200000, 2000, N'Đã thanh toán'),
                                                                                                                  (40, 5, 6, '2026-04-08 08:00:00', 267000, 300000, 33000, N'Đã thanh toán'),
                                                                                                                  (41, 6, 7, '2026-04-08 09:30:00', 234000, 250000, 16000, N'Đã thanh toán'),
                                                                                                                  (42, 4, 8, '2026-04-08 11:00:00', 189000, 200000, 11000, N'Đã thanh toán'),
                                                                                                                  (43, 5, 9, '2026-04-08 12:30:00', 412000, 420000, 8000, N'Đã thanh toán'),
                                                                                                                  (44, 4, 10, '2026-04-08 14:00:00', 276000, 300000, 24000, N'Đã thanh toán'),
                                                                                                                  (45, 5, NULL, '2026-04-08 15:20:00', 165000, 200000, 35000, N'Đã thanh toán'),
                                                                                                                  (46, 6, 12, '2026-04-09 08:15:00', 387000, 400000, 13000, N'Đã thanh toán'),
                                                                                                                  (47, 4, 14, '2026-04-09 09:45:00', 234000, 250000, 16000, N'Đã thanh toán'),
                                                                                                                  (48, 5, 15, '2026-04-09 11:10:00', 298000, 300000, 2000, N'Đã thanh toán'),
                                                                                                                  (49, 4, 16, '2026-04-09 12:40:00', 212000, 250000, 38000, N'Đã thanh toán'),
                                                                                                                  (50, 5, 17, '2026-04-09 14:00:00', 156000, 200000, 44000, N'Đã thanh toán'),
                                                                                                                  (51, 6, 18, '2026-04-09 15:30:00', 178000, 200000, 22000, N'Đã thanh toán'),
                                                                                                                  (52, 4, 19, '2026-04-10 08:20:00', 345000, 350000, 5000, N'Đã thanh toán'),
                                                                                                                  (53, 5, 20, '2026-04-10 09:50:00', 267000, 300000, 33000, N'Đã thanh toán'),
                                                                                                                  (54, 4, 21, '2026-04-10 11:15:00', 423000, 450000, 27000, N'Đã thanh toán'),
                                                                                                                  (55, 5, 22, '2026-04-10 12:45:00', 198000, 200000, 2000, N'Đã thanh toán'),
                                                                                                                  (56, 6, 24, '2026-04-10 14:10:00', 289000, 300000, 11000, N'Đã thanh toán'),
                                                                                                                  (57, 4, 26, '2026-04-10 15:40:00', 212000, 250000, 38000, N'Đã thanh toán'),
                                                                                                                  (58, 5, 27, '2026-04-11 08:00:00', 276000, 300000, 24000, N'Đã thanh toán'),
                                                                                                                  (59, 4, 28, '2026-04-11 09:30:00', 354000, 400000, 46000, N'Đã thanh toán'),
                                                                                                                  (60, 5, 29, '2026-04-11 11:00:00', 245000, 250000, 5000, N'Đã thanh toán'),
                                                                                                                  (61, 6, 30, '2026-04-11 12:30:00', 178000, 200000, 22000, N'Đã thanh toán'),
                                                                                                                  (62, 4, NULL, '2026-04-11 14:00:00', 165000, 200000, 35000, N'Đã thanh toán'),
                                                                                                                  (63, 5, 2, '2026-04-11 15:20:00', 287000, 300000, 13000, N'Đã thanh toán'),
                                                                                                                  (64, 4, 4, '2026-04-12 08:15:00', 156000, 200000, 44000, N'Đã thanh toán'),
                                                                                                                  (65, 5, 6, '2026-04-12 09:45:00', 234000, 250000, 16000, N'Đã thanh toán'),
                                                                                                                  (66, 6, 9, '2026-04-12 11:10:00', 298000, 300000, 2000, N'Đã thanh toán'),
                                                                                                                  (67, 4, 12, '2026-04-12 12:40:00', 389000, 400000, 11000, N'Đã thanh toán'),
                                                                                                                  (68, 5, 15, '2026-04-12 14:00:00', 267000, 300000, 33000, N'Đã thanh toán'),
                                                                                                                  (69, 4, 18, '2026-04-12 15:30:00', 212000, 250000, 38000, N'Đã thanh toán'),
                                                                                                                  (70, 5, 21, '2026-04-13 08:20:00', 345000, 350000, 5000, N'Đã thanh toán'),
                                                                                                                  (71, 6, 24, '2026-04-13 09:50:00', 276000, 300000, 24000, N'Đã thanh toán'),
                                                                                                                  (72, 4, 27, '2026-04-13 11:15:00', 198000, 200000, 2000, N'Đã thanh toán'),
                                                                                                                  (73, 5, NULL, '2026-04-13 12:45:00', 423000, 450000, 27000, N'Đã thanh toán'),
                                                                                                                  (74, 4, 1, '2026-04-13 14:10:00', 289000, 300000, 11000, N'Đã thanh toán'),
                                                                                                                  (75, 5, 3, '2026-04-13 15:40:00', 178000, 200000, 22000, N'Chờ thanh toán'),
                                                                                                                  (76, 6, 5, '2026-04-14 08:00:00', 267000, 300000, 33000, N'Đã thanh toán'),
                                                                                                                  (77, 4, 7, '2026-04-14 09:30:00', 354000, 400000, 46000, N'Đã thanh toán'),
                                                                                                                  (78, 5, 10, '2026-04-14 11:00:00', 156000, 200000, 44000, N'Đã thanh toán'),
                                                                                                                  (79, 4, 13, '2026-04-14 12:30:00', 245000, 250000, 5000, N'Đã thanh toán'),
                                                                                                                  (80, 5, 16, '2026-04-14 14:00:00', 298000, 300000, 2000, N'Đã thanh toán');
SET IDENTITY_INSERT HOA_DON OFF;

INSERT INTO CT_HOA_DON (MaHoaDon, MaSanPham, SoLuong, DonGia, ThanhTien) VALUES
                                                                             (1, 1, 4, 12000, 48000), (1, 2, 2, 16500, 33000), (1,14, 3, 9000, 27000),
                                                                             (2, 5, 1, 39000, 39000), (2, 6, 2, 32000, 64000),
                                                                             (3, 9, 2, 23000, 46000), (3,10, 1, 20000, 20000), (3,11, 1, 27000, 27000),
                                                                             (4,17, 1, 34000, 34000), (4,18, 1, 52000, 52000),
                                                                             (5,21, 1, 69000, 69000), (5,22, 1, 49000, 49000), (5,23, 1, 79000, 79000),
                                                                             (6,13, 2, 45000, 90000), (6,15, 2, 12000, 24000), (6,16, 3, 15000, 45000),
                                                                             (7, 3, 2, 19000, 38000), (7, 4, 1, 36000, 36000), (7,24, 1, 99000, 99000),
                                                                             (8, 7, 1, 28000, 28000), (8, 8, 2, 22000, 44000), (8,20, 1, 39000, 39000),
                                                                             (9,12, 2, 32000, 64000), (9,14, 5, 9000, 45000),
                                                                             (10,1,10,12000,120000), (10,2,5,16500,82500), (10,6,3,32000,96000),
                                                                             (11,18,2,52000,104000), (11,19,1,69000,69000),
                                                                             (12,5,2,39000,78000), (12,9,1,23000,23000), (12,21,1,69000,69000),
                                                                             (13,10,2,20000,40000), (13,16,3,15000,45000),
                                                                             (14,23,1,79000,79000), (14,24,1,99000,99000),
                                                                             (15,1,6,12000,72000), (15,3,4,19000,76000), (15,5,3,39000,117000), (15,13,2,45000,90000), (15,21,2,69000,138000),
                                                                             (16,2,3,16500,49500), (16,27,2,32000,64000), (16,32,1,42000,42000),
                                                                             (17,4,2,36000,72000), (17,11,2,27000,54000), (17,26,2,28000,56000), (17,40,2,14000,28000), (17,44,1,14000,14000), (17,48,1,52000,52000),
                                                                             (18,6,1,32000,32000), (18,12,1,32000,32000), (18,25,2,18000,36000), (18,37,1,20000,20000), (18,43,2,145000,290000), (18,45,1,28000,28000),
                                                                             (19,1,2,12000,24000), (19,9,3,23000,69000), (19,15,2,12000,24000), (19,20,1,39000,39000), (19,30,1,95000,95000), (19,38,1,30000,30000),
                                                                             (20,3,2,19000,38000), (20,10,1,20000,20000), (20,21,1,69000,69000), (20,31,1,65000,65000), (20,36,1,25000,25000),
                                                                             (21,2,4,16500,66000), (21,7,2,28000,56000), (21,14,3,9000,27000), (21,22,1,49000,49000), (21,39,1,40000,40000),
                                                                             (22,5,1,39000,39000), (22,16,2,15000,30000), (22,24,2,99000,198000), (22,34,1,28000,28000), (22,41,1,79000,79000),
                                                                             (23,11,1,27000,27000), (23,12,2,32000,64000), (23,33,1,52000,52000), (23,42,1,48000,48000),
                                                                             (24,4,1,36000,36000), (24,13,2,45000,90000), (24,19,1,69000,69000), (24,26,1,28000,28000), (24,43,1,145000,145000),
                                                                             (25,1,3,12000,36000), (25,3,2,19000,38000), (25,6,1,32000,32000), (25,27,2,32000,64000), (25,37,1,20000,20000),
                                                                             (26,9,2,23000,46000), (26,21,1,69000,69000), (26,25,2,18000,36000), (26,30,1,95000,95000), (26,35,1,23000,23000), (26,40,2,14000,28000),
                                                                             (27,2,2,16500,33000), (27,10,1,20000,20000), (27,15,3,12000,36000), (27,28,1,65000,65000), (27,38,1,30000,30000), (27,44,3,14000,42000),
                                                                             (28,5,2,39000,78000), (28,11,1,27000,27000), (28,23,1,79000,79000), (28,31,1,65000,65000),
                                                                             (29,4,1,36000,36000), (29,7,1,28000,28000), (29,14,5,9000,45000), (29,20,1,39000,39000), (29,26,2,28000,56000), (29,32,2,42000,84000),
                                                                             (30,1,2,12000,24000), (30,9,1,23000,23000), (30,12,2,32000,64000), (30,24,1,99000,99000),
                                                                             (31,3,3,19000,57000), (31,6,1,32000,32000), (31,13,1,45000,45000), (31,25,2,18000,36000), (31,40,2,14000,28000), (31,43,1,145000,145000), (31,48,1,52000,52000),
                                                                             (32,2,4,16500,66000), (32,10,2,20000,40000), (32,16,3,15000,45000), (32,21,1,69000,69000),
                                                                             (33,5,2,39000,78000), (33,11,1,27000,27000), (33,15,2,12000,24000), (33,27,1,32000,32000), (33,37,2,20000,40000), (33,42,1,48000,48000), (33,45,1,28000,28000),
                                                                             (34,4,2,36000,72000), (34,7,1,28000,28000), (34,14,3,9000,27000), (34,26,2,28000,56000), (34,32,1,42000,42000),
                                                                             (35,1,3,12000,36000), (35,9,2,23000,46000), (35,12,1,32000,32000), (35,23,1,79000,79000), (35,38,1,30000,30000),
                                                                             (36,3,2,19000,38000), (36,6,1,32000,32000), (36,13,2,45000,90000), (36,20,1,39000,39000), (36,24,1,99000,99000), (36,34,1,28000,28000), (36,40,1,14000,14000),
                                                                             (37,2,3,16500,49500), (37,10,1,20000,20000), (37,25,2,18000,36000), (37,44,5,14000,70000),
                                                                             (38,5,1,39000,39000), (38,11,1,27000,27000), (38,16,2,15000,30000), (38,21,1,69000,69000), (38,30,1,95000,95000), (38,43,1,145000,145000),
                                                                             (39,4,1,36000,36000), (39,7,2,28000,56000), (39,14,2,9000,18000), (39,26,1,28000,28000), (39,31,1,65000,65000), (39,42,2,48000,96000),
                                                                             (40,1,4,12000,48000), (40,9,1,23000,23000), (40,12,2,32000,64000), (40,15,3,12000,36000), (40,27,1,32000,32000), (40,37,2,20000,40000), (40,45,1,28000,28000), (40,48,1,52000,52000),
                                                                             (41,2,2,16500,33000), (41,3,3,19000,57000), (41,10,1,20000,20000), (41,13,2,45000,90000), (41,23,1,79000,79000),
                                                                             (42,5,1,39000,39000), (42,6,1,32000,32000), (42,11,2,27000,54000), (42,25,2,18000,36000), (42,32,1,42000,42000),
                                                                             (43,4,1,36000,36000), (43,7,1,28000,28000), (43,14,5,9000,45000), (43,21,1,69000,69000), (43,24,2,99000,198000), (43,38,1,30000,30000), (43,40,2,14000,28000), (43,44,3,14000,42000),
                                                                             (44,1,5,12000,60000), (44,9,2,23000,46000), (44,16,2,15000,30000), (44,26,2,28000,56000), (44,31,1,65000,65000), (44,43,1,145000,145000),
                                                                             (45,2,3,16500,49500), (45,10,1,20000,20000), (45,12,2,32000,64000),
                                                                             (46,3,2,19000,38000), (46,5,1,39000,39000), (46,13,2,45000,90000), (46,15,3,12000,36000), (46,20,1,39000,39000), (46,27,2,32000,64000), (46,37,1,20000,20000), (46,42,1,48000,48000),
                                                                             (47,4,1,36000,36000), (47,6,1,32000,32000), (47,11,2,27000,54000), (47,21,1,69000,69000), (47,32,2,42000,84000),
                                                                             (48,1,3,12000,36000), (48,7,1,28000,28000), (48,14,4,9000,36000), (48,23,1,79000,79000), (48,24,1,99000,99000), (48,40,1,14000,14000), (48,45,1,28000,28000),
                                                                             (49,2,2,16500,33000), (49,9,2,23000,46000), (49,16,3,15000,45000), (49,25,2,18000,36000), (49,31,1,65000,65000), (49,38,1,30000,30000), (49,43,2,145000,290000),
                                                                             (50,3,2,19000,38000), (50,5,1,39000,39000), (50,10,1,20000,20000), (50,12,1,32000,32000), (50,26,2,28000,56000), (50,34,1,28000,28000), (50,44,4,14000,56000),
                                                                             (51,4,1,36000,36000), (51,11,1,27000,27000), (51,15,2,12000,24000), (51,20,1,39000,39000), (51,27,2,32000,64000),
                                                                             (52,1,4,12000,48000), (52,6,1,32000,32000), (52,13,2,45000,90000), (52,21,1,69000,69000), (52,30,1,95000,95000), (52,40,2,14000,28000), (52,42,1,48000,48000),
                                                                             (53,2,3,16500,49500), (53,7,1,28000,28000), (53,9,2,23000,46000), (53,14,3,9000,27000), (53,16,2,15000,30000), (53,25,2,18000,36000), (53,37,1,20000,20000), (53,45,1,28000,28000),
                                                                             (54,3,2,19000,38000), (54,5,2,39000,78000), (54,10,2,20000,40000), (54,12,1,32000,32000), (54,23,1,79000,79000), (54,31,1,65000,65000), (54,38,1,30000,30000), (54,43,1,145000,145000), (54,48,1,52000,52000),
                                                                             (55,4,1,36000,36000), (55,11,1,27000,27000), (55,15,1,12000,12000), (55,20,1,39000,39000), (55,26,1,28000,28000),
                                                                             (56,1,3,12000,36000), (56,6,1,32000,32000), (56,9,1,23000,23000), (56,13,1,45000,45000), (56,21,2,69000,138000),
                                                                             (57,2,2,16500,33000), (57,7,2,28000,56000), (57,14,3,9000,27000), (57,16,2,15000,30000), (57,24,1,99000,99000), (57,32,1,42000,42000),
                                                                             (58,3,1,19000,19000), (58,5,1,39000,39000), (58,10,1,20000,20000), (58,12,2,32000,64000), (58,15,2,12000,24000), (58,27,1,32000,32000), (58,40,2,14000,28000), (58,44,3,14000,42000),
                                                                             (59,4,2,36000,72000), (59,11,1,27000,27000), (59,20,1,39000,39000), (59,23,1,79000,79000), (59,26,2,28000,56000), (59,31,1,65000,65000), (59,37,1,20000,20000), (59,42,1,48000,48000),
                                                                             (60,1,2,12000,24000), (60,6,1,32000,32000), (60,9,2,23000,46000), (60,13,1,45000,45000), (60,21,1,69000,69000), (60,25,1,18000,18000), (60,38,1,30000,30000),
                                                                             (61,2,3,16500,49500), (61,7,1,28000,28000), (61,14,2,9000,18000), (61,16,1,15000,15000), (61,24,2,99000,198000), (61,40,1,14000,14000),
                                                                             (62,3,1,19000,19000), (62,5,1,39000,39000), (62,10,2,20000,40000), (62,12,1,32000,32000), (62,27,1,32000,32000), (62,43,1,145000,145000),
                                                                             (63,4,1,36000,36000), (63,11,2,27000,54000), (63,15,2,12000,24000), (63,20,1,39000,39000), (63,26,1,28000,28000), (63,34,1,28000,28000), (63,45,1,28000,28000),
                                                                             (64,1,3,12000,36000), (64,6,1,32000,32000), (64,9,1,23000,23000), (64,13,2,45000,90000),
                                                                             (65,2,2,16500,33000), (65,7,1,28000,28000), (65,14,3,9000,27000), (65,16,2,15000,30000), (65,21,1,69000,69000), (65,25,1,18000,18000),
                                                                             (66,3,2,19000,38000), (66,5,1,39000,39000), (66,10,1,20000,20000), (66,12,2,32000,64000), (66,23,1,79000,79000), (66,31,1,65000,65000),
                                                                             (67,4,1,36000,36000), (67,11,2,27000,54000), (67,15,1,12000,12000), (67,20,1,39000,39000), (67,26,2,28000,56000), (67,38,1,30000,30000), (67,40,2,14000,28000), (67,42,1,48000,48000), (67,44,1,14000,14000),
                                                                             (68,1,4,12000,48000), (68,6,1,32000,32000), (68,9,2,23000,46000), (68,13,1,45000,45000), (68,27,1,32000,32000), (68,43,1,145000,145000),
                                                                             (69,2,3,16500,49500), (69,7,1,28000,28000), (69,14,2,9000,18000), (69,16,3,15000,45000), (69,21,1,69000,69000), (69,34,1,28000,28000),
                                                                             (70,3,1,19000,19000), (70,5,2,39000,78000), (70,10,1,20000,20000), (70,12,1,32000,32000), (70,15,2,12000,24000), (70,23,1,79000,79000), (70,25,2,18000,36000), (70,45,1,28000,28000),
                                                                             (71,4,1,36000,36000), (71,11,1,27000,27000), (71,20,1,39000,39000), (71,26,1,28000,28000), (71,31,1,65000,65000), (71,37,2,20000,40000), (71,42,1,48000,48000), (71,48,1,52000,52000),
                                                                             (72,1,2,12000,24000), (72,6,1,32000,32000), (72,9,1,23000,23000), (72,13,2,45000,90000), (72,21,1,69000,69000),
                                                                             (73,2,4,16500,66000), (73,7,2,28000,56000), (73,14,3,9000,27000), (73,16,2,15000,30000), (73,24,1,99000,99000), (73,40,1,14000,14000), (73,43,1,145000,145000), (73,44,2,14000,28000),
                                                                             (74,3,2,19000,38000), (74,5,1,39000,39000), (74,10,1,20000,20000), (74,12,2,32000,64000), (74,27,1,32000,32000), (74,38,1,30000,30000),
                                                                             (75,4,1,36000,36000), (75,11,1,27000,27000), (75,15,1,12000,12000), (75,20,1,39000,39000), (75,26,2,28000,56000),
                                                                             (76,1,3,12000,36000), (76,6,1,32000,32000), (76,9,2,23000,46000), (76,13,1,45000,45000), (76,23,1,79000,79000), (76,31,1,65000,65000), (76,42,1,48000,48000),
                                                                             (77,2,2,16500,33000), (77,7,1,28000,28000), (77,14,4,9000,36000), (77,16,3,15000,45000), (77,21,1,69000,69000), (77,25,1,18000,18000), (77,37,1,20000,20000), (77,45,1,28000,28000),
                                                                             (78,3,1,19000,19000), (78,5,1,39000,39000), (78,10,2,20000,40000), (78,12,1,32000,32000), (78,15,2,12000,24000), (78,34,1,28000,28000), (78,40,2,14000,28000),
                                                                             (79,4,2,36000,72000), (79,11,1,27000,27000), (79,20,1,39000,39000), (79,26,1,28000,28000), (79,43,1,145000,145000),
                                                                             (80,1,4,12000,48000), (80,6,1,32000,32000), (80,9,1,23000,23000), (80,13,2,45000,90000), (80,27,1,32000,32000);

COMMIT TRAN;
PRINT N'Seed dữ liệu thành công.';
END TRY
BEGIN CATCH
IF @@TRANCOUNT > 0 ROLLBACK TRAN;
    PRINT N'Seed thất bại: ' + ERROR_MESSAGE();
END CATCH;
GO