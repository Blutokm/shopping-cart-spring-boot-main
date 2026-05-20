package com.ecom.util;

public enum OrderStatus {

	IN_PROGRESS(1, "Pending"), RECEIVED(2, "Received"), PACKED(3, "Packed"), SHIPPING(4, "Shipping"),
	DELIVERED(5, "Delivered"), CANCELLED(6, "Cancelled"), ;

	private Integer id;
	private String name;

	private OrderStatus(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
