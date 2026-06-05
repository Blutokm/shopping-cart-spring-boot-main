package com.ecom.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	public Product() {
	}

	@Column(length = 500)
	private String title;

	@Column(length = 5000)
	private String description;

	private String category;

	private Double price;

	private String image;

	private int discount;

	private Double discountPrice;

	@ManyToOne
	@JoinColumn(name = "created_by_user_id")
	private UserDtls createdBy;

	public Product(Integer id, String title, String description, String category, Double price, String image,
			int discount, Double discountPrice, Boolean isActive, List<ProductVariant> variants) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.category = category;
		this.price = price;
		this.image = image;
		this.discount = discount;
		this.discountPrice = discountPrice;
		this.isActive = isActive;
		this.variants = variants;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public Double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<ProductVariant> getVariants() {
		return variants;
	}

	public void setVariants(List<ProductVariant> variants) {
		this.variants = variants;
	}

	private Boolean isActive;

	public UserDtls getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserDtls createdBy) {
		this.createdBy = createdBy;
	}

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductVariant> variants = new ArrayList<>();

	public List<ProductImage> getExtraImages() {
		return extraImages;
	}

	public void setExtraImages(List<ProductImage> extraImages) {
		this.extraImages = extraImages;
	}

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductImage> extraImages = new ArrayList<>();

	public int getStock() {
		// TODO Auto-generated method stub
		return 0;
	}
}