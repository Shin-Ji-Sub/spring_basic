package com.coffeeorderproject.service;

import java.util.ArrayList;

import admin.coffeeorderproject.dto.ProductDto;
import user.coffeeorderproject.dao.ProductShopDao;
import user.coffeeorderproject.dto.BoardDto;

public class ProductService {

	private ProductShopDao productShopDao = new ProductShopDao();
	
	// All Products
	public ArrayList<ProductDto> getAllProducts(int pageNum, int selectValue) {
		ArrayList<ProductDto> productArr = productShopDao.selectAllProducts(pageNum, selectValue);
		
		for (int i = 0; i < productArr.size(); i++) {
			switch (productArr.get(i).getProductCategoryId()) {
			case 1 : productArr.get(i).setProductCategoryName("커피"); break;
			case 2 : productArr.get(i).setProductCategoryName("할리치노"); break;
			case 3 : productArr.get(i).setProductCategoryName("빙수"); break;
			case 4 : productArr.get(i).setProductCategoryName("음료"); break;
			case 5 : productArr.get(i).setProductCategoryName("디저트"); break;
			}
		}
		
		return productArr;
	}

	// Category Products
	public ArrayList<ProductDto> getCategoryProduct(int cateNum, int selectValue, int pageNum) {
		ArrayList<ProductDto> productArr = productShopDao.selectCategoryProduct(cateNum, selectValue, pageNum);
		return productArr;
	}

	// Search Products
	public ArrayList<ProductDto> getSearchProduct(String searchKeyword, int pageNum, int selectValue) {
		ArrayList<ProductDto> productArr = productShopDao.selectSearchProduct(searchKeyword, pageNum, selectValue);
		return productArr;
	}

	// Sort Products
	public ArrayList<ProductDto> getSortProduct(int selectValue) {
		ArrayList<ProductDto> productArr = productShopDao.selectSortProduct(selectValue);
		return productArr;
	}

	// Products Count for Pagination
	public int getProductCount() {
		int productCount = productShopDao.getProductCount();
		return productCount;
	}

	// Get Detail Product
	public ProductDto getDetailProduct(int prodno) {
		ProductDto product = productShopDao.selectDetailProduct(prodno);
		return product;
	}

	// Get Detail Product
	public ArrayList<ProductDto> getDetailCateProduct(int prodno) {
		ArrayList<ProductDto> products = productShopDao.selectDetailCateProduct(prodno);
		return products;
	}

	public ArrayList<ProductDto> selectAllProduct() {
		ArrayList<ProductDto> productArr = productShopDao.selectAllproduct();
		return productArr;
	}

	// Get Product Reviews
	public ArrayList<BoardDto> getProductReview(int prodno) {
		ArrayList<BoardDto> reviewArr = productShopDao.selectProductReview(prodno);
		
		return reviewArr;
	}

	public int getProductSize(int cateNum) {
		int productsSize = productShopDao.selectProductsSize(cateNum);
		return productsSize;
	}
	
}
