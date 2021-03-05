package tn.cloudnerds.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Null;
import javax.faces.bean.SessionScoped;

import tn.cloudnerds.entities.ApplicationUser;
import tn.cloudnerds.entities.Cart;
import tn.cloudnerds.entities.CartLine;
import tn.cloudnerds.entities.PointsLoyalty;
import tn.cloudnerds.entities.Product;
import tn.cloudnerds.services.ProductService;

@ManagedBean
@SessionScoped
public class ProductBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	ProductService productService;

	// List<Cart> listAllCartByParentId;

		@PostConstruct
		public void init() {
			// listAllCartByParentId = productService.GetAllCartByParentId(parentId());
		}
	
		List<Product> listAllProduct;
	
		public List<Product> listOfAllProduct() {
			listAllProduct = productService.GetAllProduct();
	
	//		String version = FacesContext.class.getPackage().getImplementationVersion();
	//		System.out.println(version);
			return listAllProduct;
		}

	public List<Product> GetlistOfAllProduct() {
		return listAllProduct;
	}

	List<Product> listCartProduct;

	public List<Product> ProductByCartLinesIds() {
		if (productService.GetNotYetPurchasedCartByParentId(parentId()) == null) {
			listCartProduct = null;
		} else {
			listCartProduct = productService.GetProductByCartLinesIds(parentId());
		}
		return listCartProduct;
	}

	public List<Product> GetProductByCartLinesIds() {
		return listCartProduct;
	}

//	public void jasperReport() {
//		productService.jasperReport(parentId());
//	}

//	
//	public void initOrReloadIfNeeded(){
//		System.out.println("test");
//	}

	private String quantityChoosedFromView;

	public String getQuantityChoosedFromView() {
		return quantityChoosedFromView;
	}

	public void setQuantityChoosedFromView(String quantityChoosedFromView) {
		this.quantityChoosedFromView = quantityChoosedFromView;
	}

	public Product productFromView;

	public void setProductFromView(Product productFromView) {
		this.productFromView = productFromView;
	}

//	public void setQuantityChoosedFromView(int quantityChoosedFromView) {
//		this.quantityChoosedFromView = quantityChoosedFromView;
//	}

//	public List<Cart> GetAllCartByParentId() {
//
////		for (CartLine cartLine : listAllParentCartLine) {
////			System.out.println(cartLine.getProduct().getName());
////			}
//
//		return listAllCartByParentId;
//	}

	public String ConfirmPurchaseWithoutLoyaltyPoints() {
		 productService.jasperReport(parentId());
		productService.ConfirmPurchaseWithoutLoyaltyPoints(parentId());
		hide2();
		return "ProductHome.jsf";

	}

	public String ConfirmPurchaseUsingLoyaltyPoints() {
		 productService.jasperReport(parentId());
		productService.ConfirmPurchaseUsingLoyaltyPoints(parentId());
		hide2();
		return "ProductHome.jsf";

	}

	public void AddProductToCart() {
		productService.AddProductToCart(productFromView.id, Integer.parseInt(quantityChoosedFromView), parentId());
		showPopup = false;
		clear();
	}

	public Cart GetNotYetPurchasedCartByParentId() {
		Cart cart = productService.GetNotYetPurchasedCartByParentId(parentId());
		if (cart != null) {
			return cart;
		}
		return null;
	}

	public PointsLoyalty GetPointsLoyalty() {
		return productService.GetPointsLoyalty(parentId());
	}

	public int theAmountYouHaveToPay() {	
		
		int amount = (GetNotYetPurchasedCartByParentId().getCartTotalPrice() - GetPointsLoyalty().getPoints());
		if (amount < 0) {
			return 0;
		}
		return amount;
	}

	public int RemainingLoyaltyPointAmount() {
		int amount = (GetPointsLoyalty().getPoints() - GetNotYetPurchasedCartByParentId().getCartTotalPrice());
		if (amount < 0) {
			return 0;
		}
		return amount;
	}

	
	
//	public String PaymentWithPaypalWithOutLoyalPoint() {
//		
//		String navigateTo = productService.PaymentWithPaypalWithOutLoyalPoint(parentId());
//			 return navigateTo;
//	}

	/** Start Popup add Product to cart functions **/

	private boolean showPopup;

	public Product show() {

		showPopup = true;
		return productFromView;

	}

	public void hide() {
		showPopup = false;
	}

	public boolean isShowPopup() {
		return showPopup;
	}

	public void setShowPopup(boolean showPopup) {
		this.showPopup = showPopup;
	}

	/** End Popup add Product to cart functions **/

	/** Start Popup my cart functions **/

	private boolean showPopup2;

	public void show2() {
		showPopup2 = true;
	}

	public void hide2() {
		showPopup2 = false;
	}

	public boolean isShowPopup2() {
		return showPopup2;
	}

	public void setShowPopup2(boolean showPopup2) {
		this.showPopup2 = showPopup2;
	}

	/** End Popup my cart functions **/

	public void clear() {
		setQuantityChoosedFromView(null);
	}

	/*******************************
	 * search functions
	 ********************************/

	private Date date1 = null;
	private Date date2 = null;
	Date dateFromView;

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	private String keyword;

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	List<Cart> listAllCartByParentIdViewTest;

	public List<Cart> listAllCartByParentIdViewTest() {
		listAllCartByParentIdViewTest = productService.GetAllCartByParentId(parentId());
		return listAllCartByParentIdViewTest;
	}
	
	
	List<Cart> testCart;
	public List<Cart> test() {
		testCart = productService.GetAllCartByParentId(parentId());
		return testCart;
	}

	List<Cart> listAllCartByParentId;

	public List<Cart> Search() throws ParseException {

		if ((null == date1 || null == date2) && (null == keyword || "".equals(keyword))) {
			listAllCartByParentId = productService.GetAllCartByParentId(parentId());
			return listAllCartByParentId;
		} else if (null == keyword || "".equals(keyword)) {
			listAllCartByParentId = productService.searchCartByDate(date1, date2,
					productService.GetAllCartByParentId(parentId()));
			return listAllCartByParentId;

		} else if (null == date1 || null == date2) {
			listAllCartByParentId = productService.searchProductByName(keyword,
					productService.GetAllCartByParentId(parentId()));
			return listAllCartByParentId;

		} else {
			listAllCartByParentId = productService.searchProductByName(keyword,
					productService.searchCartByDate(date1, date2, productService.GetAllCartByParentId(parentId())));

			return listAllCartByParentId;
		}

	}

	public List<Cart> getSearch() throws ParseException {
		return listAllCartByParentId;
	}

	/*******************************
	 * end search functions
	 ********************************/

	/***************************
	 * NavigateTo Views with init function
	 * 
	 * @throws ParseException
	 ***************************/
	public String DisplayMyCartHistory() throws ParseException {
		Search();
		String navigateTo = "/kindergarten-web/template/product-views/PurchaseHistory.jsf";
		return navigateTo;
	}

	public String DisplayMyCart() {
		if(productService.TestCreationPointsLoyalty(parentId()) == false) {
			productService.CreatePointsLoyalty(parentId());
		}
		
		ProductByCartLinesIds();
		String navigateTo = "/kindergarten-web/template/product-views/MyCart.jsf";
		return navigateTo;
	}

	public String DisplayProductHomePage() {
		listOfAllProduct();
		String navigateTo = "/kindergarten-web/template/product-views/ProductHome.jsf";
		return navigateTo;
	}

	public int parentId() {
		if (LoginBean.productUser == null) {
			return 0;
		} else {
			return LoginBean.productUser.getId();
		}
	}

}
