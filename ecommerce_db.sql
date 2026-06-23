-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 23, 2026 lúc 05:07 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `ecommerce_db`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cart`
--

CREATE TABLE `cart` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `totalPrice` double DEFAULT NULL,
  `totalOrderPrice` double DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`id`, `image_name`, `is_active`, `name`) VALUES
(1, 'Trangsuc.jpg', b'1', 'Trang Sức'),
(2, 'Tuixach.jpg', b'1', 'Túi Xách'),
(3, 'Kinhmat.jpg', b'1', 'Kính Mắt'),
(4, 'dongho.jpg', b'1', 'Đồng Hồ'),
(5, 'Nuochoa.jpg', b'1', 'Nước Hoa'),
(6, 'Balo.jpg', b'1', 'Balo'),
(7, 'Tatvo.jpg', b'1', 'Vớ'),
(8, 'Mu.webp', b'1', 'Mũ');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orderaddress`
--

CREATE TABLE `orderaddress` (
  `id` int(11) NOT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobileNo` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `pincode` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_address`
--

CREATE TABLE `order_address` (
  `id` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `mobile_no` varchar(255) DEFAULT NULL,
  `pincode` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `order_address`
--

INSERT INTO `order_address` (`id`, `address`, `city`, `email`, `first_name`, `last_name`, `mobile_no`, `pincode`, `state`) VALUES
(1, 'Việt Nam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '1', 'ChươngMỹ'),
(2, 'Việt Nam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '1', 'ChươngMỹ'),
(3, 'Việt Nam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '1', 'ChươngMỹ'),
(4, 'Việt Nam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '1', 'ChươngMỹ'),
(5, 'Việt Nam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '1', 'ChươngMỹ'),
(6, 'Việt Nam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '1', 'ChươngMỹ'),
(7, 'ViệtNam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '1', 'ChươngMỹ'),
(8, 'Tốt Động-Chương Mỹ-Hà Nội', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '1', 'ChươngMỹ'),
(9, 'Việt Nam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '1', 'ChươngMỹ'),
(10, 'Việt Nam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '1', 'ChươngMỹ'),
(11, 'Việt Nam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '12', 'ChươngMỹ'),
(12, 'Việt Nam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '23424536526', '32', 'ChươngMỹ'),
(13, 'ViệtNam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '43', 'ChươngMỹ'),
(14, 'Việt Nam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '3', 'ChươngMỹ'),
(15, 'ViệtNam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '123', 'ChươngMỹ'),
(16, 'VietNam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '23424536526', '1', 'ChươngMỹ'),
(17, 'ViệtNam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '1', 'ChuongMy'),
(18, 'ViệtNam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '23424536526', '1', 'ChươngMỹ'),
(19, 'ViệtNam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '23424536526', '12', 'ChươngMỹ'),
(20, 'ViệtNam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '1', 'ChươngMỹ'),
(21, 'VietNam', 'Ha Noi', 'phamkhiem@gmail.com', 'Khiem', 'Pham', '1234567890', '1', 'ChươngMỹ'),
(22, 'ViệtNam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '1234567890', '1', 'ChươngMỹ'),
(23, 'ViệtNam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '23424536526', '1', 'ChươngMỹ'),
(24, 'Việt Nam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '0393846572', '123', 'Chương Mỹ'),
(25, 'Việt Nam', 'Ha Noi', 'phamkhiem@gmail.com', 'Phạm', 'Khiêm', '0393846572', '123', 'Chương Mỹ');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `tille` varchar(500) DEFAULT NULL,
  `discount` int(11) DEFAULT NULL,
  `discountPrice` double DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `discount_price` double DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `created_by_user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`id`, `category`, `description`, `image`, `price`, `tille`, `discount`, `discountPrice`, `isActive`, `discount_price`, `is_active`, `title`, `created_by_user_id`) VALUES
(1, 'Túi Xách', '– Thương hiệu: ELLY.\r\n– Sản xuất: Trung Quốc (theo tiêu chuẩn chất lượng của thương hiệu ELLY).\r\n– Màu sắc: Đen, nude.\r\n– Kích thước: 12.5 x 18 x 4.5 cm (chiều ngang x chiều dọc x độ dày).\r\n– Chất liệu: Da tổng hợp cao cấp.\r\n– Kiểu dáng: Túi đeo chéo, đeo vai, xách tay.\r\n– Công năng sử dụng: Đựng điện thoại, đồ dùng cá nhân nhỏ gọn,…\r\n– Trọn bộ sản phẩm gồm: Túi xách nữ thời trang ELLY – EL356 + Túi đựng sản phẩm + Hộp.\r\n– Bảo hành: 03 tháng (với lỗi do sản xuất).', 'tui-xach-nu-thoi-trang-cao-cap-elly-el338-33.jpg', 799000, NULL, 50, NULL, NULL, 399500, b'1', 'Túi xách nữ thời trang ELLY – EL356', 5),
(2, 'Túi Xách', '– Thương hiệu: ELLY.\r\n– Sản xuất: Trung Quốc (theo tiêu chuẩn chất lượng của thương hiệu ELLY).\r\n– Màu sắc: Đen, trắng, tím.\r\n– Kích thước: 26/20 x 20.5 x 9.5 cm (Chiều ngang x chiều dọc x độ dày).\r\n– Chất liệu: Da tổng hợp cao cấp.\r\n– Kiểu dáng: Túi đeo chéo, đeo vai, xách tay.\r\n– Công năng sử dụng: Đựng ô nhỏ, điện thoại, ví, đồ dùng cá nhân, bút, sổ tay nhỏ,..\r\n– Trọn bộ sản phẩm gồm: Túi xách nữ thời trang ELLY – EL338 + Túi đựng sản phẩm + Hộp.\r\n– Bảo hành: 03 tháng (với lỗi do sản xuất).', 'tui-xach-nu-thoi-trang-elly-el356-2.jpg', 900000, NULL, 0, NULL, NULL, 900000, b'1', 'Túi xách nữ thời trang ELLY – EL338', 5),
(3, 'Túi Xách', '– Thương hiệu: ELLY.\r\n– Sản xuất: Trung Quốc (theo tiêu chuẩn chất lượng của thương hiệu ELLY).\r\n– Màu sắc: Đen, nude, nâu.\r\n– Kích thước: 26.5 x 16 x 9.5 cm (Chiều ngang x chiều dọc x độ dày).\r\n– Chất liệu: Da tổng hợp cao cấp.\r\n– Kiểu dáng: Túi đeo chéo, đeo vai, xách tay .\r\n– Công năng sử dụng: Đựng điện thoại, ví, đồ dùng cá nhân, bút, sổ tay nhỏ,..\r\n– Trọn bộ sản phẩm gồm: Túi xách nữ thời trang ELLY – EL340 + Túi đựng sản phẩm + Hộp.\r\n– Bảo hành: 03 tháng (với lỗi do sản xuất).', 'tui-xach-nu-thoi-trang-elly-el340-37-768x768.jpg', 1199000, NULL, 0, NULL, NULL, 1199000, b'1', 'Túi xách nữ thời trang ELLY – EL340', 5),
(4, 'Túi Xách', '– Thương hiệu: ELLY.\r\n– Sản xuất: Trung Quốc (theo tiêu chuẩn chất lượng của thương hiệu ELLY).\r\n– Màu sắc: Đen, nâu, ghi.\r\n– Kích thước: 37/ 35.5 x 26 x 12 cm ( Miệng/ đáy chiều ngang x chiều dọc x độ dày).\r\n– Chất liệu: Da tổng hợp cao cấp.\r\n– Kiểu dáng: Túi đeo chéo, đeo vai, xách tay.\r\n– Công năng sử dụng: Đựng sổ sách, ipad, điện thoại, ví, đồ dùng cá nhân, bút, sổ tay nhỏ,..\r\n– Trọn bộ sản phẩm gồm: Túi xách nữ thời trang ELLY – EL342 + Túi đựng sản phẩm + Hộp.\r\n– Bảo hành: 03 tháng (với lỗi do sản xuất).', 'tui-xach-nu-thoi-trang-elly-el342-27-768x768.jpg', 999000, NULL, 0, NULL, NULL, 999000, b'1', 'Túi xách nữ thời trang ELLY – EL342', 5),
(5, 'Balo', '– Thương hiệu: LECOS.\r\n– Sản xuất: Gia công tại Trung Quốc (theo tiêu chuẩn chất lượng của thương hiệu LECOS).\r\n– Màu sắc: Đen.\r\n– Kích thước: 30 x 44 x 15 cm (chiều ngang x chiều dọc x độ dày).\r\n– Chất liệu: Vải Oxford.\r\n– Kiểu dáng: Balo.\r\n– Công năng sử dụng: đựng sách vở, laptop, ipad, đồ cá nhân,..\r\n– Trọn bộ sản phẩm gồm: Balo nam cao cấp LECOS – LBH8 + Túi đựng sản phẩm + Hộp.\r\n– Bảo hành: 06 tháng (với lỗi do sản xuất).', 'balo-nam-cao-cap-lecos-lb2-9.jpg', 1990000, NULL, 0, NULL, NULL, 1990000, b'1', 'Balo nam cao cấp LECOS – LBH8', 5),
(6, 'Balo', '– Thương hiệu: LECOS.\r\n– Sản xuất: Gia công tại Trung Quốc (theo tiêu chuẩn chất lượng của thương hiệu LECOS).\r\n– Màu sắc: Đen.\r\n– Kích thước: 29 x 36 x 11 cm (chiều ngang x chiều dọc x độ dày).\r\n– Chất liệu: Vải Oxford.\r\n– Kiểu dáng: Balo.\r\n– Công năng sử dụng: Đựng điện thoại, sách vở, đồ cá nhân…\r\n– Trọn bộ sản phẩm gồm: Balo nam cao cấp LECOS – LBH2 + Túi đựng sản phẩm + Hộp.\r\n– Bảo hành: 06 tháng (với lỗi do sản xuất).', 'DAI-DIEN-Balo-nam-cao-cap-LECOS-LBH8-11.jpg', 1799000, NULL, 0, NULL, NULL, 1799000, b'1', 'Balo nam cao cấp LECOS – LBH2', 5),
(7, 'Balo', '– Thương hiệu: LECOS.\r\n– Sản xuất: Gia công tại Trung Quốc (theo tiêu chuẩn chất lượng của thương hiệu LECOS).\r\n– Màu sắc: Đen.\r\n– Kích thước: 31 x 43 x 14.5 cm(chiều ngang x chiều dọc x độ dày).\r\n– Chất liệu: Vải Oxford.\r\n– Kiểu dáng: Balo.\r\n– Công năng sử dụng: đựng sách vở, laptop, ipad, đồ cá nhân,..\r\n– Trọn bộ sản phẩm gồm: Balo nam cao cấp LECOS – LBH7 + Túi đựng sản phẩm + Hộp.\r\n– Bảo hành: 06 tháng (với lỗi do sản xuất).', 'DAI-DIEN-Balo-nam-cao-cap-LECOS-LBH7-3.jpg', 1799000, NULL, 0, NULL, NULL, 1799000, b'1', 'Balo nam cao cấp LECOS – LBH7', 5),
(8, 'Balo', '– Thương hiệu: ELLY HOMME.\r\n– Sản xuất: Trung Quốc (theo tiêu chuẩn chất lượng của thương hiệu ELLY).\r\n– Màu sắc: Đen, nâu.\r\n– Kích thước: 26 x 37 x 12 cm (chiều ngang x chiều dọc x độ dày).\r\n– Chất liệu: Da thật cao cấp.\r\n– Kiểu dáng: Balo.\r\n– Công năng sử dụng: Đựng điện thoại, laptop, sổ sách, sạc pin, đồ cá nhân,…\r\n– Trọn bộ sản phẩm gồm: Balo nam da thật ELLY HOMME – EBM5 + Túi đựng sản phẩm + Hộp.\r\n– Bảo hành: 06 tháng (với lỗi do sản xuất).', 'ba-lo-nam-da-that-elly-ebm525.jpg', 1799000, NULL, 10, NULL, NULL, 1619100, b'1', 'Balo nam da thật ELLY HOMME – EBM5', 5),
(9, 'Đồng Hồ', '– Thương hiệu: ELLY.\r\n– Sản xuất: Gia công lắp ráp tại nhà máy Trung Quốc (theo tiêu chuẩn chất lượng của thương hiệu ELLY).\r\n– Màu sắc: Trắng, đen.\r\n– Chất liệu vỏ: Thép không gỉ.\r\n– Chất liệu dây đeo: Thép không gỉ.\r\n– Chất liệu mặt kính: Sapphire.\r\n– Đường kính mặt: 28mm.\r\n– Chống nước: 3ATM.\r\n– Nguồn năng lượng: Pin.\r\n– Bộ máy: Quartz của Nhật Bản.\r\n– Trọn bộ sản phẩm gồm: Đồng hồ nữ cao cấp ELLY – EH3 + Hộp đựng đồng hồ cao cấp + Sổ hướng dẫn sử dụng và bảo hành.\r\n– Thời gian bảo hành: 01 năm (chi tiết xem trong sổ hướng dẫn sử dụng và bảo hành).', 'dong-ho-nu-thoi-trang-cao-cap-elly-eh3-13.jpg', 2990000, NULL, 0, NULL, NULL, 2990000, b'1', 'Đồng hồ nữ cao cấp ELLY – EH3', 5),
(10, 'Đồng Hồ', '– Thương hiệu: ELLY.\r\n– Sản xuất: Gia công lắp ráp tại nhà máy Trung Quốc (theo tiêu chuẩn chất lượng của thương hiệu ELLY).\r\n– Màu sắc: Xanh, trắng.\r\n– Chất liệu vỏ: Kim loại.\r\n– Chất liệu dây đeo: Da thật.\r\n– Chất liệu mặt kính: Mặt phủ Sapphire.\r\n– Đường kính mặt số: 25 mm (bề ngang).\r\n– Chống nước: 3ATM.\r\n– Nguồn năng lượng: Pin.\r\n– Bộ máy: Quartz của Nhật Bản.\r\n– Trọn bộ sản phẩm gồm: Đồng hồ nữ cao cấp ELLY – EH22 + Hộp đựng đồng hồ cao cấp + Sổ hướng dẫn sử dụng và bảo hành.\r\n– Thời gian bảo hành: 01 năm (chi tiết xem trong sổ hướng dẫn sử dụng và bảo hành).', 'dong-ho-nu-cao-cap-elly-eh22-7-4-768x768.jpg', 1199000, NULL, 10, NULL, NULL, 1079100, b'1', 'Đồng hồ nữ cao cấp ELLY – EH22', 5),
(11, 'Đồng Hồ', '– Thương hiệu: ELLY.\r\n– Sản xuất: Gia công lắp ráp tại nhà máy Trung Quốc (theo tiêu chuẩn chất lượng của thương hiệu ELLY).\r\n– Màu sắc: Gold, crom.\r\n– Chất liệu vỏ: Kim loại.\r\n– Chất liệu dây đeo: Kim loại.\r\n– Chất liệu mặt kính: Mặt phủ Sapphire.\r\n– Kích thước mặt số: 20 mm (bề ngang).\r\n– Chống nước: 3ATM.\r\n– Nguồn năng lượng: Pin.\r\n– Bộ máy: Quartz của Nhật Bản.\r\n– Trọn bộ sản phẩm gồm: Đồng hồ nữ cao cấp ELLY – EH18 + Hộp đựng đồng hồ cao cấp + Sổ hướng dẫn sử dụng và bảo hành.\r\n– Thời gian bảo hành: 01 năm (chi tiết xem trong sổ hướng dẫn sử dụng và bảo hành).', 'dong-ho-nu-cao-cap-elly-eh18-15-2.jpg', 1399000, NULL, 0, NULL, NULL, 1399000, b'1', 'Đồng hồ nữ cao cấp ELLY – EH18', 5),
(12, 'Đồng Hồ', '– Thương hiệu: ELLY.\r\n– Sản xuất: Gia công lắp ráp tại nhà máy Trung Quốc (theo tiêu chuẩn chất lượng của thương hiệu ELLY).\r\n– Màu sắc: Đen, trắng.\r\n– Chất liệu vỏ: Kim loại.\r\n– Chất liệu dây đeo: Da thật.\r\n– Chất liệu mặt kính: Mặt phủ Sapphire.\r\n– Kích thước mặt số: 21 mm (bề ngang).\r\n– Chống nước: 3ATM.\r\n– Nguồn năng lượng: Pin.\r\n– Bộ máy: Quartz của Nhật Bản.\r\n– Trọn bộ sản phẩm gồm: Đồng hồ nữ cao cấp ELLY – EH19 + Hộp đựng đồng hồ cao cấp + Sổ hướng dẫn sử dụng và bảo hành.\r\n– Thời gian bảo hành: 01 năm (chi tiết xem trong sổ hướng dẫn sử dụng và bảo hành).', 'dong-ho-nu-cao-cap-elly-eh19-37-768x768.jpg', 1199000, NULL, 0, NULL, NULL, 1199000, b'1', 'Đồng hồ nữ cao cấp ELLY – EH19', 5),
(13, 'Kính Mắt', 'THÔNG TIN GỌNG KÍNH\r\n* Thương Hiệu:LILY\r\n* Mã sản phẩm: C-NFKL-VT-00329\r\n*Thông tin kỹ thuật số :14.5-5.4-1.9\r\n*Chất liệu: Nhựa pha kim loại\r\n*Giá sản phẩm: 450000.0 VNĐ\r\n*Xuất sứ: Trung Quốc\r\n*CHỊU TRÁCH NHIỆM SP: CÔNG TY CỔ PHẦN ULTD THỊNH PHÁT\r\n*CẢNH BÁO: BẢO QUẢN TRONG HỘP KÍNH\r\n*HDSD: DÙNG ĐỂ ĐEO MẮT, TRÁNH NHIỆT ĐỘ CAO & VA CHẠM MẠNH', '20250922-86swdk0rve-1769568551000.png', 450000, NULL, 0, NULL, NULL, 450000, b'1', 'Kính Nhựa Pha Kim Loại LiLy 00329', 5),
(14, 'Kính Mắt', 'THÔNG TIN GỌNG KÍNH\r\n* Thương Hiệu:LILY\r\n* Mã sản phẩm: C-ND-VCN-KC26250\r\n*Thông tin kỹ thuật số :14.6-5.3-1.7\r\n*Chất liệu: Nhựa Dẻo\r\n*Giá sản phẩm: 180000.0 VNĐ\r\n*Xuất sứ: Trung Quốc\r\n*CHỊU TRÁCH NHIỆM SP: CÔNG TY CỔ PHẦN ULTD THỊNH PHÁT\r\n*CẢNH BÁO: BẢO QUẢN TRONG HỘP KÍNH\r\n*HDSD: DÙNG ĐỂ ĐEO MẮT, TRÁNH NHIỆT ĐỘ CAO & VA CHẠM MẠNH', '20250922-v7w3aij9bu-1769568506000.jpeg', 180000, NULL, 0, NULL, NULL, 180000, b'1', 'Kính Nhựa LiLy KC26250', 5),
(15, 'Kính Mắt', 'Thương hiệu: RayBan\r\nMã sản phẩm: 0RB4441D_686880_53\r\nXuất xứ: Ý\r\nBảo hành: 1 năm\r\nGiới tính: Nữ\r\nPhong cách vành gọng: Nguyên khung\r\nKiểu dáng: Oval\r\nChất liệu gọng: Nhựa\r\nMàu sắc: Đen\r\nCác chức năng: Chống tia UV\r\nKích thước tròng: 53mm\r\nĐộ dài gọng: 145mm\r\nĐộ dài cầu kính: 21mm', 'mat-kinh-rayban-0rb4441d-686880-53-1.webp', 1990000, NULL, 0, NULL, NULL, 1990000, b'1', 'Gọng kính Nhựa – Chống tia UV – 0RB4441D_686880_53', 5),
(16, 'Kính Mắt', 'Thương hiệu: Oakley\r\nMã sản phẩm: 0OO9513D_951302_39\r\nXuất xứ: Mỹ\r\nBảo hành: 1 năm\r\nGiới tính: Nam\r\nPhong cách vành gọng: Bắt ốc\r\nHình dạng: Chữ nhật\r\nChất liệu gọng: Nhựa\r\nMàu sắc: Đen\r\nKích thước tròng: 39mm\r\nĐộ dài gọng: 125mm\r\nĐộ dài cầu kính: 139mm', 'mat-kinh-oakley-0oo9513d-951302-39-1.webp', 1799000, NULL, 0, NULL, NULL, 1799000, b'1', 'Oakley Cybr Dyno Nam – Gọng kính Nhựa – 0OO9513D_951302_39', 5),
(17, 'Mũ', 'Chất liệu:Cotton\r\nGiới tính:Unisex\r\nMàu sắc:Trắng\r\nKiểu dáng:Mũ lưỡi trai', 'mu-mlb-rookie-unstructured-ballcap-new-york-yankees-3acp7701n-50whs-mau-trang-626664fe.webp', 850000, NULL, 30, NULL, NULL, 595000, b'1', 'Mũ MLB Rookie Unstructured Ballcap New York Yankees 3ACP7701N-50WHS Màu Trắng', 5),
(18, 'Mũ', 'Chất liệu:100% Cotton\r\nGiới tính:Nam\r\nMàu sắc:Xanh chàm\r\nKiểu dáng:Mũ tròn', 'mu-coach-script-embroidered-denim-bucket-hat-ccq60-mau-xanh-cham-68fb4a05b0bca-2410202516422.webp', 599000, NULL, 0, NULL, NULL, 599000, b'1', 'Mũ Coach Script Embroidered Denim Bucket Hat CCQ60 Màu Xanh Tràm', 5),
(19, 'Mũ', 'Chất liệu:Chất liệu tổng hợp, vải\r\nMặt hàng:Có sẵn\r\nGiới tính:Nữ\r\nMàu sắc:Hồng', 'mu-nu-dior-diorclub-v1u-pink-mau-hong-682a8bf519870-19052025084005.webp', 1700000, NULL, 0, NULL, NULL, 1700000, b'1', 'Mũ Nửa Đầu Nữ Dior Diorclub V1U Pink Màu Hồng', 5),
(20, 'Mũ', 'Chất liệu:100% Cotton\r\nGiới tính:Unisex\r\nMàu sắc:Trắng\r\nKiểu dáng:Mũ nửa đầu', 'mu-mlb-paisley-suncap-new-york-yankees-3asc06123-50ivs-mau-trang-629daf9cc7d11-0606202214411.webp', 1280000, NULL, 50, NULL, NULL, 640000, b'1', 'Mũ MLB Paisley Sun Cap New York Yankees 3ASC06123-50IVS Màu Trắng', 5),
(22, 'Trang Sức', 'Chất liệu:Mạ Rhodium\r\nGiới tính:Nữ\r\nMàu sắc:Bạc\r\nKiểu dáng:Dây chuyền', 'day-chuyen-swarovski-dancing-swan-necklace-blue-rhodium-plated-5533397-mau-bac-686897.webp', 2500000, NULL, 40, NULL, NULL, 1500000, b'1', 'Dây Chuyền Nữ Swarovski Dancing Swan Necklace, Blue, Rhodium Plated 5533397', 3),
(23, 'Trang Sức', 'Chất liệu:Mạ vàng hồng, pha lê, đá Zirconia\r\nGiới tính:Nữ\r\nMàu sắc:Vàng hồng\r\nKiểu dáng:Vòng đeo tay', 'vong-deo-tay-nu-swarovski-swan-bracelet-swan-pink-rose-gold-tone-plated-54722708mau-v.webp', 3300000, NULL, 0, NULL, NULL, 3300000, b'1', 'Vòng Đeo Tay Nữ Swarovski Swan Bracelet Swan, Pink, Rose Gold-Tone Plated 5472271', 3),
(24, 'Trang Sức', 'Chất liệu:Nhựa resin, kim loại\r\nGiới tính:Nữ\r\nMàu sắc:Trắng ngà\r\nKiểu dáng:Khuyên tai', 'cloud-image-1779351160890-khuyen-tai-nu-vivienne-westwood-faux-pearl-martha-drop-earr.webp', 6200000, NULL, 0, NULL, NULL, 6200000, b'1', 'Khuyên Tai Nữ Vivienne Westwood Faux Pearl Martha Drop Earrings Màu Trắng Ngà', 3),
(25, 'Trang Sức', 'Chất liệu:Bạc sterling, đá zirconia\r\nGiới tính:Nữ\r\nMàu sắc:Bạc\r\nKiểu dáng:Nhẫn', '64a28cea3ae4b-03072023155506.webp', 1450000, NULL, 20, NULL, NULL, 1160000, b'1', 'Nhẫn Nữ Pandora Celestial Blue Sparkling Moon Ring 192675C01 Màu Bạc', 3),
(26, 'Nước Hoa', 'Năm phát hành:2024\r\nGiới tính:Unisex\r\nXuất xứ thương hiệu:Pháp', 'cloud-image-1779443006228-n--a--c-hoa-unisex-maison-crivelli-cuir-infrarouge-extrait-d.webp', 6800000, NULL, 0, NULL, NULL, 6800000, b'1', 'Nước Hoa Unisex Maison Crivelli Cuir Infrarouge Extrait De Parfum', 3),
(27, 'Nước Hoa', 'Năm phát hành:2026\r\nGiới tính:Unisex\r\nXuất xứ thương hiệu:Pháp', 'cloud-image-177944130409-nuoc-hoa-unisex-creed-wild-vetiver-edp--1-.webp', 5700000, NULL, 0, NULL, NULL, 5700000, b'1', 'Nước Hoa Unisex Creed Wild Vetiver EDP ', 3),
(28, 'Nước Hoa', 'Năm phát hành:2019\r\nMặt hàng:Có sẵn\r\nGiới tính:Unisex\r\nXuất xứ thương hiệu:Pháp', 'nuoc-hoa-louis-vuitton-lv-les-sables-roses-edp-100ml-6850f1fbbd5f6-17062025114131.webp', 9700000, NULL, 0, NULL, NULL, 9700000, b'1', 'Nước Hoa Louis Vuitton LV Les Sables Roses EDP', 3),
(29, 'Nước Hoa', 'Năm phát hành:2012\r\nGiới tính:Nam\r\nXuất xứ thương hiệu:Ý', 'cloud-image-1779500389403-n--a--c-hoa-nam-versace-eros-edp-mini-5ml.webp', 590000, NULL, 0, NULL, NULL, 590000, b'1', 'Nước Hoa Nam Versace Eros EDP Mini ', 3),
(30, 'Vớ', 'Chất liệu:70% Nylon, 20% Cotton, 10% Spandex\r\nKiểu dáng:Tất cổ trung\r\nPhù hợp:Các hoạt động thể thao, đặc biệt các bộ môn dùng vợt', '-trangtat-pkb-nam-co-trung-43_2.avif', 800000, NULL, 0, NULL, NULL, 800000, b'1', 'Tất Pickleball Nam Cổ Trung', 3),
(31, 'Vớ', 'Chất liệu:45% Cotton, 50% Nylon, 5% Spandex\r\nKiểu dáng:Tất Cổ Trung\r\nPhù hợp:Chơi bóng đá, thể thao thường ngày', 'tat-bong-da-co-trung-wc-54-den_95.avif', 110000, NULL, 0, NULL, NULL, 110000, b'1', 'Icon prevIcon prev Tất Bóng Đá Cổ Trung World Cup', 3),
(32, 'Vớ', 'Chất liệu:70% Nylon Siltai, 20% Cotton, 10% Spandex\r\nKiểu dáng:Tất 5 ngón\r\nPhù hợp:Chạy bộ, luyện tập thể thao, vận động hàng ngày', 'tat-chay-bo-xo-ngon-56-den_90.avif', 320000, NULL, 30, NULL, NULL, 224000, b'1', 'Tất Xỏ Ngón Chạy Bộ', 3),
(33, 'Vớ', 'Chất liệu:75% Nylon quick dry, 15% Nylon 10% Spandex\r\nKiểu dáng:Thân 5.5 | Bàn 23 | Rộng cổ 9 | Rộng thân 8\r\nPhù hợp:Hoạt động thường ngày', 'tat-luoi-nam-coolmate-chong-truot-30-den_76.avif', 50000, NULL, 0, NULL, NULL, 50000, b'1', 'Tất Lười Nam Coolmate chống trượt', 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `productorder`
--

CREATE TABLE `productorder` (
  `id` int(11) NOT NULL,
  `orderId` varchar(255) DEFAULT NULL,
  `orderDate` datetime(6) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `paymentType` varchar(255) DEFAULT NULL,
  `order_address_id` int(11) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_image`
--

CREATE TABLE `product_image` (
  `id` int(11) NOT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product_image`
--

INSERT INTO `product_image` (`id`, `image_name`, `product_id`) VALUES
(26, 'dong-ho-nu-cao-cap-elly-eh22-2.jpg', 10),
(27, 'dong-ho-nu-cao-cap-elly-eh22-3.jpg', 10),
(36, 'mat-kinh-oakley-0oo9513d-951302-39-chinh-hang-5.webp', 16),
(37, 'mat-kinh-oakley-0oo9513d-951302-39-chinh-hang-6.webp', 16),
(73, 'nuoc-hoa-louis-vuitton-lv-les-sables-roses-edp-100ml-6850f1fbbd5f6-1706202511413.webp', 28),
(74, 'nuoc-hoa-louis-vuitton-lv-les-sables-roses-edp-100ml-6850f1fbbdbac-1706202511411.webp', 28),
(86, 'tui-xach-nu-thoi-trang-elly-el356-7.jpg', 2),
(87, 'tui-xach-nu-thoi-trang-elly-el356-8.jpg', 2),
(88, 'tui-xach-nu-thoi-trang-elly-el340-30.jpg', 3),
(89, 'tui-xach-nu-thoi-trang-elly-el340-36.jpg', 3),
(90, 'tui-xach-nu-thoi-trang-elly-el342-7.jpg', 4),
(91, 'tui-xach-nu-thoi-trang-elly-el356-8.jpg', 4),
(92, 'Balo-nam-cao-cap-LECOS-LBH7-1-1.jpg', 5),
(93, 'Balo-nam-cao-cap-LECOS-LBH7-13.jpg', 5),
(94, 'Balo-nam-cao-cap-LECOS-LBH8-1-1.jpg', 7),
(95, 'Balo-nam-cao-cap-LECOS-LBH8-7.jpg', 7),
(96, 'ba-lo-nam-da-that-elly-ebm58-1-1.jpg', 8),
(97, 'z6309480915387_c35964f676780f8db9a3be5daec7fca3.jpg', 8),
(98, 'ba-lo-nam-da-that-elly-ebm517.jpg', 6),
(99, 'tui-xach-nam-cao-cap-da-that-lecos-lbh2-4.jpg', 6),
(102, 'dong-ho-nu-thoi-trang-cao-cap-elly-eh3-7.jpg', 9),
(103, 'dong-ho-nu-thoi-trang-cao-cap-elly-eh3-9.jpg', 9),
(104, 'dong-ho-nu-cao-cap-elly-eh18-2-1.jpg', 11),
(105, 'dong-ho-nu-cao-cap-elly-eh18-9-2.jpg', 11),
(106, 'dong-ho-nu-cao-cap-elly-eh19-28.jpg', 12),
(107, 'dong-ho-nu-cao-cap-elly-eh19-29.jpg', 12),
(108, '20250922-aswoanerkq-1769568551000.png', 13),
(109, '20250922-vghq0t3anv-1769568506000.jpeg', 14),
(112, 'mat-kinh-rayban-0rb4441d-686880-53-1-300x30.webp', 15),
(113, 'mat-kinh-rayban-0rb4441d-686880-53-1-300x300.webp', 15),
(114, 'mu-mlb-rookie-unstructured-ballcap-new-york-yankees-3acp7701n-50whs-mau-trang-62666505d80d3-.webp', 17),
(115, 'mu-mlb-rookie-unstructured-ballcap-new-york-yankees-3acp7701n-50whs-mau-trang-62666505d80d5-.webp', 17),
(116, 'mu-coach-script-embroidered-denim-bucket-hat-ccq60-mau-xanh-cham-68fb4a05b248f-2410202516422.webp', 18),
(117, 'mu-nu-dior-diorclub-v1u-pink-mau-hong-682a8bf51aa0d-19052025084003.webp', 19),
(118, 'mu-nu-dior-diorclub-v1u-pink-mau-hong-682a8bf51aa0d-19052025084005.webp', 19),
(119, 'mu-mlb-paisley-suncap-new-york-yankees-3asc06123-50ivs-mau-trang-629daf9cdfc75-0606202214410.webp', 20),
(120, 'mu-mlb-paisley-suncap-new-york-yankees-3asc06123-50ivs-mau-trang-629daf9cdfc75-0606202214411.webp', 20),
(121, 'day-chuyen-swarovski-dancing-swan-necklace-blue-rhodium-plated-5533397-mau-bac-68689.webp', 22),
(122, 'day-chuyen-swarovski-dancing-swan-necklace-blue-rhodium-plated-5533397-mau-bac-686816.webp', 22),
(123, 'vong-deo-tay-nu-swarovski-swan-bracelet-swan-pink-rose-gold-tone-plated-5472270-mau-v.webp', 23),
(124, 'vong-deo-tay-nu-swarovski-swan-bracelet-swan-pink-rose-gold-tone-plated-5472271-mau-v.webp', 23),
(125, 'cloud-image-177935116044-khuyen-tai-nu-vivienne-westwood-faux-pearl-martha-drop-earr.webp', 24),
(126, 'cloud-image-1779351160694-khuyen-tai-nu-vivienne-westwood-faux-pearl-martha-drop-earr.webp', 24),
(127, '64a28cea1f858-0307202315550.webp', 25),
(128, '64a28cea1f858-03072023155506.webp', 25),
(131, 'cloud-image-17794430066-n--a--c-hoa-unisex-maison-crivelli-cuir-infrarouge-extrait-d.webp', 27),
(132, 'cloud-image-177944130478-nuoc-hoa-unisex-creed-wild-vetiver-edp--4-.webp', 27),
(133, 'cloud-image-1779500389613-n--a--c-hoa-nam-versace-eros-edp-mini-5ml-2.webp', 29),
(134, 'cloud-image-177944300665-n--a--c-hoa-unisex-maison-crivelli-cuir-infrarouge-extrait-d.webp', 26),
(135, 'cloud-image-1779443006859-n--a--c-hoa-unisex-maison-crivelli-cuir-infrarouge-extrait-d.webp', 26),
(136, '-trangtat-pkb-nam-co-trung-Shocks_.avif', 30),
(137, '-trangtat-pkb-nam-co-trung-Shocks_2.avif', 30),
(138, 'tat-bong-da-co-trung-wc-6-den.avif', 31),
(139, 'tat-bong-da-co-trung-wc-64-den.avif', 31),
(140, 'tat-chay-bo-xo-ngon-3-den.avif', 32),
(141, 'tat-chay-bo-xo-ngon-36-den.avif', 32),
(142, 'tat-luoi-nam-coolmate-chong-truot-_8-den.avif', 33),
(143, 'tat-luoi-nam-coolmate-chong-truot-_-den.avif', 33),
(148, 'DSC_7348.jpg', 1),
(149, 'tui-xach-nu-thoi-trang-elly-el342-34.jpg', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_order`
--

CREATE TABLE `product_order` (
  `id` int(11) NOT NULL,
  `color` varchar(255) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `payment_type` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `order_address_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product_order`
--

INSERT INTO `product_order` (`id`, `color`, `order_date`, `order_id`, `payment_type`, `price`, `quantity`, `size`, `status`, `order_address_id`, `product_id`, `user_id`) VALUES
(1, 'Đen', '2026-05-25', '23f05972-083a-45e2-8533-c614bd2b839c', 'COD', 50000, 2, 'Free Size', 'Cancelled', 1, 33, 2),
(2, 'Nâu', '2026-05-25', '23f05972-083a-45e2-8533-c614bd2b839c', 'COD', 9700000, 1, 'Free Size', 'Cancelled', 2, 28, 2),
(3, 'Bạc', '2026-05-25', '23f05972-083a-45e2-8533-c614bd2b839c', 'COD', 1500000, 3, 'Free Size', 'Cancelled', 3, 22, 2),
(4, 'Đen', '2026-05-25', '23f05972-083a-45e2-8533-c614bd2b839c', 'COD', 110000, 1, 'Free Size', 'Cancelled', 4, 31, 2),
(5, 'Đen', '2026-05-25', '9da73cab-37b0-4cf0-aa9a-fa8dbc4418d9', 'COD', 110000, 1, 'Free Size', 'Cancelled', 5, 31, 2),
(6, 'Đen', '2026-05-25', '98ed06da-9761-403a-9def-70154fc4efe7', 'COD', 110000, 1, 'Free Size', 'Cancelled', 6, 31, 2),
(7, 'Nâu', '2026-05-25', 'c605c0fb-5ed1-47f1-a78c-7733d0e77941', 'COD', 1619100, 1, 'L', 'Cancelled', 7, 8, 2),
(8, 'Nâu', '2026-05-26', '8d0b0391-b862-4bb9-b9e7-9e887b9e5bf4', 'COD', 9700000, 1, 'Free Size', 'Cancelled', 8, 28, 2),
(9, 'Ghi', '2026-05-26', '0cce0a31-a5b5-4732-93f6-15f48380f9b7', 'COD', 224000, 2, 'Free Size', 'Cancelled', 9, 32, 2),
(10, 'Trắng', '2026-05-26', '0cce0a31-a5b5-4732-93f6-15f48380f9b7', 'COD', 800000, 1, 'Free Size', 'Cancelled', 10, 30, 2),
(11, 'Xanh', '2026-05-27', '12437a6c-3612-454e-b9a8-9eb234f7d0f2', 'COD', 5700000, 1, 'Free Size', 'Cancelled', 11, 27, 2),
(12, 'Ghi', '2026-05-27', '6b6196a5-4603-4b88-966a-9cd89d573e6b', 'COD', 224000, 1, 'Free Size', 'Cancelled', 12, 32, 2),
(13, 'Nâu', '2026-05-27', '584ff293-b01c-43d6-918f-9cfd69e96457', 'COD', 1619100, 1, 'L', 'Cancelled', 13, 8, 2),
(14, 'Đen', '2026-05-27', 'c3862ac8-b76f-47f6-b0b7-fe98c13ba6ae', 'COD', 399500, 1, 'Free Size', 'Cancelled', 14, 1, 2),
(15, 'Vàng', '2026-05-27', '6011a40f-8616-4efb-8d13-c83d6abee62e', 'COD', 6800000, 1, 'Free Size', 'Cancelled', 15, 26, 2),
(16, 'Đen', '2026-06-05', '8c7d366e-4171-42ea-a024-cd3a4ea8cf49', 'VNPAY', 50000, 1, 'Free Size', 'Cancelled', 16, 33, 2),
(17, 'Đen', '2026-06-05', '2af7a762-2612-43cb-965c-56e29ff7e078', 'VNPAY', 110000, 1, 'Free Size', 'Cancelled', 17, 31, 2),
(18, 'Xanh', '2026-06-05', 'aa5c6e46-7ad0-47a9-a473-da322da9d6d2', 'VNPAY', 5700000, 1, 'Free Size', 'Cancelled', 18, 27, 2),
(19, 'Đen', '2026-06-05', '6f556a35-214c-4460-9bcd-7a4a33bab71f', 'VNPAY', 110000, 1, 'Free Size', 'Cancelled', 19, 31, 2),
(20, 'Xanh', '2026-06-05', '01c685f4-b905-4aa0-934a-e7b62fef9ae6', 'VNPAY', 5700000, 1, 'Free Size', 'Cancelled', 20, 27, 2),
(21, 'Vàng', '2026-06-05', '136ddf6b-3e80-4a7a-8680-1cadc5879df9', 'VNPAY', 6800000, 1, 'Free Size', 'Cancelled', 21, 26, 2),
(22, 'Vàng', '2026-06-05', 'd4f72847-0f2c-4a01-82ff-9e88f9b062f0', 'VNPAY', 6800000, 1, 'Free Size', 'Cancelled', 22, 26, 2),
(23, 'Đen', '2026-06-06', 'f84eb818-c6b3-4b9c-8688-48eed0b6d790', 'COD', 110000, 1, 'Free Size', 'Delivered', 23, 31, 2),
(24, 'Đen', '2026-06-12', '7660d359-ae38-45ee-a7dd-eb55d90e0d17', 'VNPAY', 50000, 2, 'Free Size', 'Hủy', 24, 33, 2),
(25, 'Đen', '2026-06-12', '39cad74f-b613-4966-9eca-03d00f29bb80', 'COD', 50000, 2, 'Free Size', 'Pending', 25, 33, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_variant`
--

CREATE TABLE `product_variant` (
  `id` int(11) NOT NULL,
  `color` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product_variant`
--

INSERT INTO `product_variant` (`id`, `color`, `size`, `stock`, `product_id`) VALUES
(28, 'Đen', 'Free Size', 20, 16),
(52, 'Nâu', 'Free Size', 18, 28),
(59, 'Trắng', 'L', 50, 10),
(60, 'Xanh', 'L', 50, 10),
(68, 'Trắng', 'Free Size', 100, 2),
(69, 'Đen', 'Free Size', 100, 2),
(70, 'Đen', 'Free Size', 100, 3),
(71, 'Nâu', 'Free Size', 100, 4),
(72, 'Đen', 'L', 50, 5),
(73, 'Đen', 'L', 50, 7),
(74, 'Nâu', 'L', 48, 8),
(75, 'Đen', 'L', 50, 6),
(77, 'Đen', 'L', 50, 9),
(78, 'Vàng', 'Free Size', 50, 11),
(79, 'Bạc', 'Free Size', 50, 11),
(80, 'Trắng', 'Free Size', 50, 12),
(81, 'Đen', 'Free Size', 50, 12),
(82, 'Đen', 'Free Size', 20, 13),
(83, 'Trắng', 'Free Size', 20, 13),
(84, 'Ghi', 'Free Size', 20, 14),
(85, 'Xanh', 'Free Size', 20, 14),
(87, 'Đen', 'Free Size', 20, 15),
(88, 'Trắng', 'Free Size', 20, 17),
(89, 'Xanh Tràm', 'XL', 20, 18),
(90, 'Hồng', 'Free Size', 20, 19),
(91, 'Trắng', 'Free Size', 20, 20),
(92, 'Bạc', 'Free Size', 17, 22),
(93, 'Vàng', 'Free Size', 20, 23),
(94, 'Hồng', 'Free Size', 20, 23),
(95, 'Trắng', 'Free Size', 20, 24),
(96, 'Ghi', 'Free Size', 20, 25),
(98, 'Xanh', 'Free Size', 17, 27),
(99, 'Xanh', 'Free Size', 20, 29),
(100, 'Vàng', 'Free Size', 17, 26),
(108, 'Trắng', 'Free Size', 19, 30),
(109, 'Đen', 'Free Size', 14, 31),
(110, 'Ghi', 'Free Size', 17, 32),
(111, 'Đen', 'Free Size', 13, 33),
(117, 'Đen', 'Free Size', 99, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_dtls`
--

CREATE TABLE `user_dtls` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `mobileNumber` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `pincode` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `profileImage` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `isEnable` tinyint(1) DEFAULT NULL,
  `accountNonLocked` tinyint(1) DEFAULT NULL,
  `failedAttempt` int(11) DEFAULT NULL,
  `lockTime` datetime(6) DEFAULT NULL,
  `resetToken` varchar(255) DEFAULT NULL,
  `account_non_locked` bit(1) DEFAULT NULL,
  `failed_attempt` int(11) DEFAULT NULL,
  `is_enable` bit(1) DEFAULT NULL,
  `lock_time` datetime(6) DEFAULT NULL,
  `mobile_number` varchar(255) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `reset_token` varchar(255) DEFAULT NULL,
  `max_product_limit` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user_dtls`
--

INSERT INTO `user_dtls` (`id`, `name`, `mobileNumber`, `address`, `email`, `city`, `state`, `pincode`, `password`, `profileImage`, `role`, `isEnable`, `accountNonLocked`, `failedAttempt`, `lockTime`, `resetToken`, `account_non_locked`, `failed_attempt`, `is_enable`, `lock_time`, `mobile_number`, `profile_image`, `reset_token`, `max_product_limit`) VALUES
(1, 'Admin', NULL, 'Việt Nam', 'admin@gmail.com', 'Hà Nội', 'Quận/huyện', '112', '$2a$10$.TqGnJJ4jvZ/zxPh0SP.lOs4gwUlkNB2nahqzbwskBiZS8Z9FV9Qu', NULL, 'ROLE_SUPERADMIN', NULL, NULL, NULL, NULL, NULL, b'1', 2, b'1', NULL, '123456789', 'icon_avatar.jpg', NULL, NULL),
(2, 'User1', NULL, 'Việt Nam', 'phamkhiem@gmail.com', 'Hà Nội', 'Quận/Huyện', '113', '$2a$10$skeHNfv/gbs4/zRvuR/wVeppBrz7EzoDXkfA5Eqnzg4g0cDNLKmuW', NULL, 'ROLE_USER', NULL, NULL, NULL, NULL, NULL, b'1', 3, b'1', NULL, '123456789', 'icon_avatar.jpg', NULL, NULL),
(3, 'Quang Nam', NULL, 'Việt Nam', 'admin1@gmail.com', 'Hà Nội', 'Phú Xuyên', '1', '$2a$10$TXKXgVS2rBpKAxHzpB9kROxMcI65Xz.c6PUt8UFlcRV1BGp6BWcyC', NULL, 'ROLE_MANAGER', NULL, NULL, NULL, NULL, NULL, b'1', 0, b'1', NULL, '123456789', 'icon_avatar.jpg', NULL, 20),
(5, 'Trọng Thông', NULL, 'Việt Nam', 'admin2@gmail.com', 'Hà Nội', 'Đông Anh', '1', '$2a$10$FXOYq71i.sAL86oN2uCayORZ2QopoGdnJDKLBCKM1T75mbdoBiQii', NULL, 'ROLE_MANAGER', NULL, NULL, NULL, NULL, NULL, b'1', 0, b'1', NULL, '123456789', 'icon_avatar.jpg', NULL, 30);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Chỉ mục cho bảng `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `orderaddress`
--
ALTER TABLE `orderaddress`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `order_address`
--
ALTER TABLE `order_address`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfgup327itahkn5okgvh4ob305` (`created_by_user_id`);

--
-- Chỉ mục cho bảng `productorder`
--
ALTER TABLE `productorder`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `product_image`
--
ALTER TABLE `product_image`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product_id` (`product_id`);

--
-- Chỉ mục cho bảng `product_order`
--
ALTER TABLE `product_order`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_qcdbxaeuc7c5gahwh0dutg04r` (`order_address_id`),
  ADD KEY `FKh73acsd9s5wp6l0e55td6jr1m` (`product_id`),
  ADD KEY `FK4f2ycr32kigtux5ag3tv0xu5m` (`user_id`);

--
-- Chỉ mục cho bảng `product_variant`
--
ALTER TABLE `product_variant`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product_id` (`product_id`);

--
-- Chỉ mục cho bảng `user_dtls`
--
ALTER TABLE `user_dtls`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `cart`
--
ALTER TABLE `cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT cho bảng `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `orderaddress`
--
ALTER TABLE `orderaddress`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `order_address`
--
ALTER TABLE `order_address`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT cho bảng `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT cho bảng `productorder`
--
ALTER TABLE `productorder`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `product_image`
--
ALTER TABLE `product_image`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=151;

--
-- AUTO_INCREMENT cho bảng `product_order`
--
ALTER TABLE `product_order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT cho bảng `product_variant`
--
ALTER TABLE `product_variant`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=118;

--
-- AUTO_INCREMENT cho bảng `user_dtls`
--
ALTER TABLE `user_dtls`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_dtls` (`id`),
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

--
-- Các ràng buộc cho bảng `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FKfgup327itahkn5okgvh4ob305` FOREIGN KEY (`created_by_user_id`) REFERENCES `user_dtls` (`id`);

--
-- Các ràng buộc cho bảng `productorder`
--
ALTER TABLE `productorder`
  ADD CONSTRAINT `productorder_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `productorder_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user_dtls` (`id`);

--
-- Các ràng buộc cho bảng `product_image`
--
ALTER TABLE `product_image`
  ADD CONSTRAINT `product_image_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `product_order`
--
ALTER TABLE `product_order`
  ADD CONSTRAINT `FK4f2ycr32kigtux5ag3tv0xu5m` FOREIGN KEY (`user_id`) REFERENCES `user_dtls` (`id`),
  ADD CONSTRAINT `FK8frxalwc79tpxo7hgqp3hsjck` FOREIGN KEY (`order_address_id`) REFERENCES `order_address` (`id`),
  ADD CONSTRAINT `FKh73acsd9s5wp6l0e55td6jr1m` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

--
-- Các ràng buộc cho bảng `product_variant`
--
ALTER TABLE `product_variant`
  ADD CONSTRAINT `product_variant_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
