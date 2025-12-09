package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import beans.SanPham;
import beans.User;


public class sanphamDAO {
	private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    // Lấy toàn bộ sản phẩm
    public List<SanPham> getSanPhams() {
        String sql = "SELECT * FROM sanpham";
        return template.query(sql, new RowMapper<SanPham>() {
            public SanPham mapRow(ResultSet rs, int rowNum) throws SQLException {
                SanPham sp = new SanPham();
                sp.setId(rs.getInt("id"));
                sp.setGia(rs.getFloat("gia"));
                sp.setTenSP(rs.getString("tensp"));
                sp.setSoluong(rs.getInt("soluong"));
                return sp;
            }
        });
    }

    // Lấy toàn bộ user (cho login)
    public List<User> getUsers() {
        String sql = "SELECT * FROM user";
        return template.query(sql, new RowMapper<User>() {
            public User mapRow(ResultSet rs, int row) throws SQLException {
                User u = new User();
                u.setID(rs.getInt("ID"));
                u.setUserName(rs.getString("userName"));
                u.setPassword(rs.getString("password"));
                return u;
            }
        });
    }

    // Thêm sản phẩm mới
    public int addSanPham(SanPham sp) {
        String sql = "INSERT INTO sanpham(gia, tensp, soluong) VALUES (?, ?, ?)";
        return template.update(sql, sp.getGia(), sp.getTenSP(), sp.getSoluong());
    }

    // Cập nhật sản phẩm
    public int update(SanPham sp) {
        String sql = "UPDATE sanpham SET gia=?, tensp=?, soluong=? WHERE id=?";
        return template.update(sql, sp.getGia(), sp.getTenSP(), sp.getSoluong(), sp.getId());
    }

    // Lấy sản phẩm theo ID
    @SuppressWarnings("deprecation")
    public SanPham getSanPhamById(int id) {
        String sql = "SELECT * FROM sanpham WHERE id=?";
        return template.queryForObject(sql, new Object[]{id}, new RowMapper<SanPham>() {
            public SanPham mapRow(ResultSet rs, int rowNum) throws SQLException {
                SanPham sp = new SanPham();
                sp.setId(rs.getInt("id"));
                sp.setGia(rs.getFloat("gia"));
                sp.setTenSP(rs.getString("tensp"));
                sp.setSoluong(rs.getInt("soluong"));
                return sp;
            }
        });
    }

    // Xóa sản phẩm
    public int deleteSanPham(int id) {
        String sql = "DELETE FROM sanpham WHERE id=?";
        return template.update(sql, id);
    }
}
