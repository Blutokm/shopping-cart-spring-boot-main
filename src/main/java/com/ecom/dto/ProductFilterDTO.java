package com.ecom.dto;

public class ProductFilterDTO {
    
    private String category;
    private Double minPrice;
    private Double maxPrice;
    private Boolean hasDiscount;
    private String sort;
    private Integer pageNo;
    private String keyword;
    
    public ProductFilterDTO() {}
    
    public ProductFilterDTO(String category, Double minPrice, Double maxPrice, 
                           Boolean hasDiscount, String sort, Integer pageNo, String keyword) {
        this.category = category;
        this.minPrice = minPrice != null ? minPrice : 0.0;
        this.maxPrice = maxPrice != null ? maxPrice : Double.MAX_VALUE;
        this.hasDiscount = hasDiscount != null ? hasDiscount : false;
        this.sort = sort != null ? sort : "";
        this.pageNo = pageNo != null ? pageNo : 0;
        this.keyword = keyword != null ? keyword.trim() : "";
    }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public Double getMinPrice() { return minPrice != null ? minPrice : 0.0; }
    public void setMinPrice(Double minPrice) { this.minPrice = minPrice; }
    
    public Double getMaxPrice() { return maxPrice != null ? maxPrice : Double.MAX_VALUE; }
    public void setMaxPrice(Double maxPrice) { this.maxPrice = maxPrice; }
    
    public Boolean getHasDiscount() { return hasDiscount != null ? hasDiscount : false; }
    public void setHasDiscount(Boolean hasDiscount) { this.hasDiscount = hasDiscount; }
    
    public String getSort() { return sort != null ? sort : ""; }
    public void setSort(String sort) { this.sort = sort; }
    
    public Integer getPageNo() { return pageNo != null ? pageNo : 0; }
    public void setPageNo(Integer pageNo) { this.pageNo = pageNo; }
    
    public String getKeyword() { return keyword != null ? keyword : ""; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
}