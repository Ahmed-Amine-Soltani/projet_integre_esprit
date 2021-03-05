package tn.cloudnerds.entities;

import java.io.Serializable;

public class CartLine implements Serializable {
	private static final long serialVersionUID = 1L;
	int cartLineId;
	int cartId ;
	int quantityChoosed ;
	int totalCartLinePrice ;
	Product product ;
	int productPrice ;
	String productName ;
	
	public CartLine() {
	}

	
	
	
	public CartLine(int quantityChoosed, int totalCartLinePrice, int productPrice, String productName) {
		super();
		this.quantityChoosed = quantityChoosed;
		this.totalCartLinePrice = totalCartLinePrice;
		this.productPrice = productPrice;
		this.productName = productName;
	}




	public int getCartId() {
		return cartId;
	}




	public void setCartId(int cartId) {
		this.cartId = cartId;
	}




	public int getTotalCartLinePrice() {
		return totalCartLinePrice;
	}




	public void setTotalCartLinePrice(int totalCartLinePrice) {
		this.totalCartLinePrice = totalCartLinePrice;
	}




	public int getProductPrice() {
		return productPrice;
	}




	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}




	public String getProductName() {
		return productName;
	}




	public void setProductName(String productName) {
		this.productName = productName;
	}




	public CartLine(int cartLineId, int cartId, int quantityChoosed, Product product) {
		this.cartLineId = cartLineId;
		this.cartId = cartId;
		this.quantityChoosed = quantityChoosed;
		this.product = product;
	}




	public int getQuantityChoosed() {
		return quantityChoosed;
	}




	public void setQuantityChoosed(int quantityChoosed) {
		this.quantityChoosed = quantityChoosed;
	}




	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCartLineId() {
		return cartLineId;
	}

	public void setCartLineId(int cartLineId) {
		this.cartLineId = cartLineId;
	}

}
