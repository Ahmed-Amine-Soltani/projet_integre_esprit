package tn.cloudnerds.interfaces;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import tn.cloudnerds.entities.*;


@Remote
public interface IProductServiceRemote {
	
	List<Product> GetAllProduct();
    List<Cart> GetAllCartByParentId(int parentId);
    void AddProductToCart(int productId , int quantityChoosed , int userId) ;
    List<Product> GetProductByCartLinesIds(int parentId) ;
    void ConfirmPurchaseWithoutLoyaltyPoints(int parentId);
    PointsLoyalty GetPointsLoyalty(int parentIdWs);
    void ConfirmPurchaseUsingLoyaltyPoints(int parentId);
    void UpdatePointsLoyalty(int parentId, int points,String todo);
//    public String PaymentWithPaypalWithOutLoyalPoint(int parentId);
    public List<Cart> searchProductByName(String name,List<Cart> cartList) ;
    public List<Cart> searchCartByDate(Date dateFromView1,Date dateFromView2,List<Cart> cartList)  throws ParseException ;
    //void InsertPointsLoyalty(int parentId, int points) ;
    boolean TestCreationPointsLoyalty(int productId) ;
    public void CreatePointsLoyalty(int productId);

}
