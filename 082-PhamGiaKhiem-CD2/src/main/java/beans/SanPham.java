package beans;

public class SanPham {
	int id;
	float gia;
	String tenSP;
	int	soluong;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getGia() {
		return gia;
	}
	public void setGia(float d) {
		this.gia = d;
	}
	public String getTenSP() {
		return tenSP;
	}
	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}
	public int getSoluong() {
		return soluong;
	}
	public void setSoluong(int soluong) {
		this.soluong = soluong;
	}
	public SanPham(int id, float gia, String tenSP, int soluong) {
		super();
		this.id = id;
		this.gia = gia;
		this.tenSP = tenSP;
		this.soluong = soluong;
	}
	public SanPham() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
