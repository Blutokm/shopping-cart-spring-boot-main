*GIỚI THIỆU DỰ ÁN: HỆ THỐNG WEBSITE BÁN HÀNG TRỰC TUYẾN (E-COMMERCE)
1. Tổng quan dự án
   
   	-Tên dự án: Shopping Cart Web Application
   
    -Mục tiêu: Xây dựng một nền tảng thương mại điện tử hoàn chỉnh, thân thiện với người dùng, giúp tối ưu hóa quy trình mua sắm trực tuyến. Dự án không chỉ cung cấp các tính năng bán hàng cơ bản mà còn tích hợp các giải pháp hiện đại như thanh toán trực tuyến qua cổng VNPay.

    -Website được thiết kế với giao diện hiện đại, chuẩn Responsive, đem lại trải nghiệm mượt mà trên cả PC, Tablet và Mobile.
		 
2. Các chức năng nổi bật
   
  Hệ thống được chia làm hai phân hệ chính với các quyền hạn chuyên biệt:
  
    👤 Phân hệ Khách hàng (User)
    
    -Quản lý tài khoản: Đăng ký, đăng nhập (bảo mật bằng Spring Security), cập nhật thông tin cá nhân, tính năng Quên mật khẩu/Đổi mật khẩu.
    
    -Khám phá sản phẩm: Xem danh sách sản phẩm, hiển thị đa dạng ảnh và phân loại theo danh mục (Balo, Kính mắt, Nước hoa, Túi xách, Đồng hồ...). Có tính năng tìm kiếm, lọc và phân trang.
 
    -Chi tiết sản phẩm: Hiển thị thông tin chi tiết, hình ảnh, biến thể sản phẩm (Product Variant - màu sắc, kích cỡ) và tình trạng tồn kho.
  
    -Giỏ hàng (Shopping Cart): Thêm/bớt sản phẩm, cập nhật số lượng, tự động tính tổng tiền thanh toán.
  
    -Thanh toán & Đặt hàng:
  
    -Hỗ trợ thanh toán khi nhận hàng (COD).

    -Tích hợp thanh toán trực tuyến an toàn qua cổng thanh toán VNPay.
 
    -Quản lý đơn hàng: Theo dõi lịch sử mua hàng và tình trạng xử lý đơn hàng chi tiết.

    🌟 Tính năng đặc biệt (AI Chatbot): Tích hợp trợ lý ảo thông minh (sử dụng Gemini AI API) giúp tự động trả lời, tư vấn và giải đáp thắc mắc của khách hàng trực tiếp trên website.

    🛡️ Phân hệ Quản trị viên (Admin)
  
    -Bảng điều khiển (Dashboard): Hiển thị thống kê tổng quan về doanh thu, tổng số đơn hàng, sản phẩm và người dùng (Statistics Module).

    -Quản lý Danh mục (Categories): Thêm, sửa, xóa, ẩn/hiện các danh mục sản phẩm.
  
    -Quản lý Sản phẩm (Products):
 
    -Tạo sản phẩm mới, định giá, mô tả, cập nhật số lượng.
    
    -Quản lý hình ảnh sản phẩm (Upload ảnh).
    
    -Quản lý Đơn hàng (Order Management): Xem danh sách đơn đặt hàng, cập nhật trạng thái đơn hàng theo luồng (Chờ xác nhận -> Đang giao -> Đã giao hàng -> Hoàn thành / Hủy).
    
    -Quản lý Người dùng (User Management): Xem danh sách khách hàng, cấp quyền Admin hoặc khóa tài khoản khi cần thiết.

3. Công nghệ & Kiến trúc sử dụng
   
  Dự án được xây dựng dựa trên mô hình MVC (Model - View - Controller), sử dụng các công nghệ tiêu chuẩn của hệ sinh thái Java:
 
    💻 Back-end:
    
    -Nền tảng: Java 17+, Spring Boot.
    
    -Bảo mật: Spring Security (Xử lý Authentication & Authorization, mã hóa mật khẩu, phân quyền truy cập).
    
    -Cơ sở dữ liệu & ORM: MySQL, Spring Data JPA, Hibernate (Quản lý các thực thể và tự động hóa truy vấn cơ sở dữ liệu).
    
    -Template Engine: Thymeleaf (Render giao diện động từ server-side).

    🎨 Front-end:
  
    -HTML5, CSS3, JavaScript.
    
    -Bootstrap (Xây dựng UI/UX nhanh chóng, đảm bảo tính Responsive).
    
    -Sử dụng thư viện AJAX để tối ưu thao tác thêm vào giỏ hàng và tương tác với Chatbot mà không cần reload trang.

    🔗 API & Tích hợp (3rd Party Services):
 
    -VNPay API: Xử lý giao dịch thanh toán trực tuyến.
    
    -Google Gemini AI API: Xử lý logic cho Chatbot tư vấn khách hàng̣̣̣̣(Tính năng đang phát triển).

4. Hình ảnh

***Giao diện User**

Giao diện Trang Chủ
<img width="1531" height="727" alt="image" src="https://github.com/user-attachments/assets/78becbcd-c9d1-4b7d-8abf-d0d8e81fa2ea" />
<img width="1496" height="677" alt="image" src="https://github.com/user-attachments/assets/e857965e-557e-46fa-9fa3-c41a1eb666ce" />

Giao diện Xem Sản Phẩm 
<img width="1525" height="721" alt="image" src="https://github.com/user-attachments/assets/2c534b07-fc22-4db7-b512-fdbd5fff0c9f" />

Giao diện Đăng Nhập/Đăng Ký
<img width="1526" height="717" alt="image" src="https://github.com/user-attachments/assets/84926888-8130-4f1d-9004-a103da446288" />
<img width="1516" height="706" alt="image" src="https://github.com/user-attachments/assets/7ee85793-c1fe-41a3-a470-a98af472da12" />

Giao diện Xem Chi Tiết Sản Phẩm
<img width="1513" height="716" alt="image" src="https://github.com/user-attachments/assets/5f8f8dae-b23a-4701-9111-36183fa131a3" />

Giao diện Đơn Hàng
<img width="1526" height="735" alt="image" src="https://github.com/user-attachments/assets/293e58ed-14c0-47ce-af74-85887579141c" />

Giao diện Giỏ Hàng
<img width="1516" height="496" alt="image" src="https://github.com/user-attachments/assets/0beec78a-04a4-4919-b2cb-5019000acaec" />

Gieo diện Thanh Toán
<img width="1529" height="725" alt="image" src="https://github.com/user-attachments/assets/5415a18a-5b79-4fe1-9cf0-959d8740d5e3" />

***Giao diện Admin**

Giao diện Doardboard
<img width="1523" height="728" alt="image" src="https://github.com/user-attachments/assets/efb32732-becd-43a4-acda-a900a9ad0d4e" />

Giao diện Quản Lý Danh Mục
<img width="1517" height="708" alt="image" src="https://github.com/user-attachments/assets/f54a553d-72dd-4b6b-a7ef-f8405dd86ea3" />

Giao diện Quản Lý Sản Phẩm
<img width="1516" height="713" alt="image" src="https://github.com/user-attachments/assets/22c1eeb7-8fc5-464a-83e4-cbbbfda591d5" />

Giao diện Quản Lý Đơn Hàng
<img width="1515" height="728" alt="image" src="https://github.com/user-attachments/assets/dc59fc86-f59b-4c75-ad48-cd45a33b8197" />

Giao diện Quản Lý User/Manager
<img width="1531" height="710" alt="image" src="https://github.com/user-attachments/assets/3e5aabba-6c8f-4435-8ebd-e4fe762628f5" />
<img width="1528" height="720" alt="image" src="https://github.com/user-attachments/assets/bfcb8af7-4d47-40c1-8af0-f299a21411aa" />

***Giao diện Manager**
<img width="1517" height="720" alt="image" src="https://github.com/user-attachments/assets/47f51664-5c8e-4a12-86a1-9297a4ed7356" />

