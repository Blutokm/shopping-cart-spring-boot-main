<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Danh sách Sản Phẩm</h2>
    <a href="addSanPham">Thêm Sản Phẩm Mới</a>
    <br><br>
    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Giá</th>
            <th>Tên sản phẩm</th>
            <th>Số lượng</th>
            <th>Hành động</th>
        </tr>
        <c:forEach var="sp" items="${list}">
            <tr>
                <td>${sp.id}</td>
                <td>${sp.gia}</td>
                <td>${sp.tensp}</td>
                <td>${sp.soluong}</td>
                <td>
                    <a href="editSanPham/${sp.id}">Sửa</a> |
                    <a href="deleteSanPham/${sp.id}" onclick="return confirm('Bạn có chắc muốn xóa?');">Xóa</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>