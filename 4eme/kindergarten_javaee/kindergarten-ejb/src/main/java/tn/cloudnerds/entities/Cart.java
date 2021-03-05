package tn.cloudnerds.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Cart implements Serializable {
	private static final long serialVersionUID = 1L;
	int cartId;
	Date cartDate ;
	int cartTotalPrice ;
	String cartTotalPriceWord ;
	List<CartLine> cartLines ;
	
	public Cart() {
	}

	public Cart(int cartId, int cartTotalPrice) {
		this.cartId = cartId;
		this.cartTotalPrice = cartTotalPrice;
	}


	public Cart(int cartId, Date cartDate, int cartTotalPrice,String cartTotalPriceWord , List<CartLine> cartLines) {
		this.cartId = cartId;
		this.cartDate = cartDate;
		this.cartTotalPrice = cartTotalPrice;
		this.cartTotalPriceWord = cartTotalPriceWord ;
		this.cartLines = cartLines;
	}
	
	public Cart(List<CartLine> cartLines) {
		this.cartLines = cartLines;
	}




	public String getCartTotalPriceWord() {
		return cartTotalPriceWord;
	}









	public void setCartTotalPriceWord(String cartTotalPriceWord) {
		this.cartTotalPriceWord = cartTotalPriceWord;
	}









	public int getCartTotalPrice() {
		return cartTotalPrice;
	}





	public void setCartTotalPrice(int cartTotalPrice) {
		this.cartTotalPrice = cartTotalPrice;
	}





	public int getCartId() {
		return cartId;
	}





	public void setCartId(int cartId) {
		this.cartId = cartId;
	}





	public Date getCartDate() {
		return cartDate;
	}





	public void setCartDate(Date cartDate) {
		this.cartDate = cartDate;
	}





	public List<CartLine> getCartLines() {
		return cartLines;
	}


	public void setCartLines(List<CartLine> cartLines) {
		this.cartLines = cartLines;
	}
	
	
	
	

}
