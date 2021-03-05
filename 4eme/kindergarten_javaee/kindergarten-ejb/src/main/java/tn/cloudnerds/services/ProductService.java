package tn.cloudnerds.services;

import tn.cloudnerds.entities.*;
import tn.cloudnerds.interfaces.IProductServiceLocal;
import tn.cloudnerds.interfaces.IProductServiceRemote;
import tn.cloudnerds.utilities.JasperReportAPI;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.field.RemainderDateTimeField;

import com.fasterxml.jackson.annotation.JsonValue;
import com.jayway.jsonpath.JsonPath;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Stateful
@LocalBean
public class ProductService implements IProductServiceRemote, IProductServiceLocal {

	public String GlobalEndPoint = "localhost:1958";

	@Override
	public void AddProductToCart(int productId, int quantityChoosed, int userId) {

		Client client = ClientBuilder.newClient();

		WebTarget web = client.target("http://" + GlobalEndPoint + "/Product/Api/AddProductToCart/" + userId + "/"
				+ productId + "/" + quantityChoosed);

		Response response = web.request().post(null);

		String result = response.readEntity(String.class);
//		System.out.println(result);

		response.close();

	}

	JasperReportAPI jr = new JasperReportAPI();

	public void jasperReport(int parentId) {
		jr.jasperReport(parentId);
	}

	@Override
	public void ConfirmPurchaseWithoutLoyaltyPoints(int parentId) {

		Cart cart = GetNotYetPurchasedCartByParentId(parentId);
		int totalCartAmount = cart.getCartTotalPrice();
		int points = (int) (totalCartAmount / 3);
		UpdatePointsLoyalty(parentId, points, "add");

		Client client = ClientBuilder.newClient();
		WebTarget web = client.target("http://" + GlobalEndPoint + "/Product/Api/ConfirmPurchase/" + parentId);
		Response response = web.request().post(null);
		String result = response.readEntity(String.class);
//		System.out.println(result);
		response.close();
	}

	@Override
	public void ConfirmPurchaseUsingLoyaltyPoints(int parentId) {

		Cart myCart = GetNotYetPurchasedCartByParentId(parentId);
		PointsLoyalty myPointsLoyalty = GetPointsLoyalty(parentId);

		int totalCartPrice = myCart.getCartTotalPrice();
		int points = myPointsLoyalty.getPoints();
		int amountToPay = totalCartPrice - points;
		if (amountToPay < 0) {
			amountToPay = 0;
		}

		Client client = ClientBuilder.newClient();
		WebTarget web = client.target("http://" + GlobalEndPoint + "/Product/Api/ConfirmPurchaseUsingLoyaltyPoints/"
				+ parentId + "/" + amountToPay);
		Response response = web.request().post(null);

		String result = response.readEntity(String.class);

		response.close();
		int remaingPoints;
		if ((points - totalCartPrice) < 0) {
			remaingPoints = 0;
		} else {
			remaingPoints = points - totalCartPrice;
		}
		UpdatePointsLoyalty(parentId, remaingPoints, "remove");

	}

	@Override
	public void UpdatePointsLoyalty(int parentId, int points, String todo) {

		PointsLoyalty myPointsLoyalty = GetPointsLoyalty(parentId);
		if (todo.equals("add") || todo == "add") {

			myPointsLoyalty.setPoints(myPointsLoyalty.getPoints() + points);
		} else if (todo.equals("remove") || todo == "remove") {
			myPointsLoyalty.setPoints(points);
		}
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://" + GlobalEndPoint + "/Product/Api/UpdatePointsLoyalty");
		Response response = target.request().put(Entity.entity(myPointsLoyalty, MediaType.APPLICATION_JSON));

	}

//	@Override
//	public void InsertPointsLoyalty(int userId, int points) {
//
//		PointsLoyalty myPointsLoyalty = GetPointsLoyalty(userId);
//		myPointsLoyalty.setPoints(points);
//
//		Client client = ClientBuilder.newClient();
//
//		WebTarget web = client
//				.target("http://" + GlobalEndPoint + "/Product/Api/InsertPointsLoyalty/" + userId + "/" + points);
//
//		Response response = web.request().post(null);
//
//		String result = response.readEntity(String.class);
////		System.out.println(result);
//
//		response.close();
//
//	}

	@Override
	public PointsLoyalty GetPointsLoyalty(int parentIdWs) {

		Client client = ClientBuilder.newClient();

		WebTarget web = client.target("http://" + GlobalEndPoint + "/Product/Api/GetPointsLoyalty/" + parentIdWs);

		Response response = web.request().get();

		String result = response.readEntity(String.class);
		// if there is not a cart i return 0
		if (result.equals("null")) {
			return null;
		}
		JsonReader jsonReader = Json.createReader(new StringReader(result));
		JsonObject object = jsonReader.readObject();
		int pointsLoyaltyId = object.getInt("id");
		int parentId = object.getInt("parentId");
		int points = object.getInt("points");
		PointsLoyalty pointLoyalty = new PointsLoyalty(pointsLoyaltyId, parentId, points);

//		System.out.println(points);
		return pointLoyalty;
	}

	@Override
	public List<Product> GetAllProduct() {
		List<Product> productList = new ArrayList<Product>();
		Client client = ClientBuilder.newClient();
		WebTarget web = client.target("http://" + GlobalEndPoint + "/Product/Api/GetAllProduct");
		Response response = web.request().get();
		String result = response.readEntity(String.class);
		JsonReader jsonReader = Json.createReader(new StringReader(result));
		JsonArray object = jsonReader.readArray();

		for (int i = 0; i < object.size(); i++) {

			Product myProduct = new Product();

			myProduct.setId(object.getJsonObject(i).getInt("id"));
			myProduct.setName(object.getJsonObject(i).getString("name"));
			myProduct.setImg1(object.getJsonObject(i).getString("img1Base64"));
			myProduct.setImg2(object.getJsonObject(i).getString("img1Base64"));
			myProduct.setImg3(object.getJsonObject(i).getString("img1Base64"));
			// JsonArray image ;
			// myproduct.setImg(object.getJsonObject(i).getJsonArray("Img1").toString().getBytes());
//    		JsonArray byteArray = object.getJsonObject(i).getJsonArray("Img1");
//    		byte[] bytes = new byte[byteArray.size()];
//            for (int i1 = 0; i1 < byteArray.size(); i1++)
//                bytes[i1] = (byte) byteArray.getInt(i1);
//    		myproduct.setName(bytes.toString());
//    		
//    		System.out.println(bytes);
			// byte[] bytes = new byte[backByte.size()];
//    		for (int i1 = 0; i1 < backByte.length; i1++) {
//    	       System.out.println((byte)((backByte[i1]) & 0xFF));
//    	        
//    	}
//    	
//    		myproduct.setImg(bytes);

			// System.out.println(image);

//   		JsonArray objAsBytes = object.getJsonObject(i).getJsonArray("Img1");
//			System.out.println(objAsBytes);

//    	System.out.println(object.getJsonObject(i).getString("Img1").toString().getBytes());

//    		try (ByteArrayOutputStream oos = new ByteArrayOutputStream(); 
//    				    JsonWriter writer = Json.createWriter(oos)){
//    		            writer.writeObject(object.getJsonObject(i));
//    		            writer.close();
//    		            oos.flush();
//    		           System.out.println(oos.toByteArray());
//    		           myproduct.setImg(oos.toByteArray());
//    		        } catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//    		  

//			System.out.println("testtttttttttt get all product");
			productList.add(myProduct);

		}
		Collections.reverse(productList);
		return productList;
	}

	@Override
	public List<Cart> GetAllCartByParentId(int parentId) {

		ArrayList<Cart> cartList = new ArrayList<Cart>();
		Client client = ClientBuilder.newClient();

		WebTarget web = client.target("http://" + GlobalEndPoint + "/Product/Api/GetAllCartByParentId/" + parentId);

		Response response = web.request().get();

		String result = response.readEntity(String.class);

		JsonReader jsonReader = Json.createReader(new StringReader(result));
		int k = 0;
		JsonArray object = jsonReader.readArray();

		for (int i = 0; i < object.size(); i++) {

			// to display just the cart with status false ( cart purchased )
			if (!object.getJsonObject(i).getBoolean("cartStatus")) {
				System.out.println("cart id : " + object.getJsonObject(i).getInt("id"));
//				System.out.println("cart id : " + object.getJsonObject(i).getString("purchaseDate"));
				int cartTotalPrice = 0;

				// **************** Retrieve Date *********************//
				Date cartDate = null;
				String cartTotalPriceWord = null;
				try {

					String jsonDate = object.getJsonObject(i).getString("purchaseDate");
					SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					cartDate = formatDate.parse(jsonDate);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				System.out.println(cartDate);
				// ****************** end Retrieve Date ******************** //

				JsonArray cartLineJsonArray = object.getJsonObject(i).getJsonArray("cartLines");
				ArrayList<CartLine> cartLineList = new ArrayList<CartLine>();
				for (int j = 0; j < cartLineJsonArray.size(); j++) {
//					System.out.println("product name : "
//							+ cartLineJsonArray.getJsonObject(j).getJsonObject("myProduct").getString("name"));
					// System.out.println("product img1Base64 " +
					// cartLineJsonArray.getJsonObject(j).getJsonObject("myProduct").getString("img1Base64"));
//					System.out.println("cart id : " + cartLineJsonArray.getJsonObject(j).getInt("cartId"));
//					System.out.println("cartLine id : " + cartLineJsonArray.getJsonObject(j).getInt("id"));
					CartLine cartLine = new CartLine(cartLineJsonArray.getJsonObject(j).getInt("id"),
							cartLineJsonArray.getJsonObject(j).getInt("cartId"),
							cartLineJsonArray.getJsonObject(j).getInt("quantityChoosed"),
							new Product(cartLineJsonArray.getJsonObject(j).getJsonObject("myProduct").getString("name"),
									cartLineJsonArray.getJsonObject(j).getJsonObject("myProduct").getInt("price"),
									cartLineJsonArray.getJsonObject(j).getJsonObject("myProduct")
											.getString("img1Base64")));
					cartLineList.add(cartLine);

					cartTotalPrice += cartLineJsonArray.getJsonObject(j).getInt("totalCartLinePrice");

					k += 1;

				}
//				System.out.println("output : " + convertQuantityChoosed(cartTotalPrice));	
				cartTotalPriceWord = convertQuantityChoosed(cartTotalPrice);
				Cart cart = new Cart(object.getJsonObject(i).getInt("id"), cartDate, cartTotalPrice, cartTotalPriceWord,
						cartLineList);
				cartList.add(cart);
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + k);
			}
		}
		Collections.reverse(cartList);
		return cartList;
	}

	/** end get cart id **/

	/*** get cart id ***/
	public Cart GetNotYetPurchasedCartByParentId(int parentId) {
		Client client = ClientBuilder.newClient();
		WebTarget web = client
				.target("http://" + GlobalEndPoint + "/Product/Api/GetNotYetPurchasedCartByParentId/" + parentId);
		Response response = web.request().get();
		String result = response.readEntity(String.class);
		if (result.equals("null")) {
			return null;
		}
		JsonReader jsonReader = Json.createReader(new StringReader(result));
		JsonObject object = jsonReader.readObject();
		int cartId = object.getInt("id");
		int totalCartPrice = object.getInt("totalCartPrice");
		Cart cart = new Cart(cartId, totalCartPrice);

		return cart;
	}

	/** end get cart id **/

	/** get cartLines ids **/
	public List<Integer> GetCartLinesProductsByCartId(int parentId) {
		Cart cart = GetNotYetPurchasedCartByParentId(parentId);
		Client client = ClientBuilder.newClient();
		WebTarget web = client
				.target("http://" + GlobalEndPoint + "/Product/Api/GetCartLinesByCartId/" + cart.getCartId());
		Response response = web.request().get();
		String result = response.readEntity(String.class);
		JsonReader jsonReader = Json.createReader(new StringReader(result));
		JsonArray object = jsonReader.readArray();
		ArrayList<Integer> cartLineIdsList = new ArrayList<Integer>();
		for (int i = 0; i < object.size(); i++) {
			cartLineIdsList.add(object.getJsonObject(i).getInt("productId"));
		}
		return cartLineIdsList;
	}

	public List<CartLine> CartLineForJasperReport(int parentId) {
		Cart cart = GetNotYetPurchasedCartByParentId(parentId);
		Client client = ClientBuilder.newClient();
		WebTarget web = client
				.target("http://" + GlobalEndPoint + "/Product/Api/GetCartLinesByCartId/" + cart.getCartId());
		Response response = web.request().get();
		String result = response.readEntity(String.class);
		JsonReader jsonReader = Json.createReader(new StringReader(result));
		JsonArray object = jsonReader.readArray();
		ArrayList<CartLine> cartLineIdsList = new ArrayList<CartLine>();
		for (int i = 0; i < object.size(); i++) {
			cartLineIdsList.add(new CartLine(object.getJsonObject(i).getInt("quantityChoosed"),
					object.getJsonObject(i).getInt("totalCartLinePrice"),
					object.getJsonObject(i).getInt("productPrice"),
					object.getJsonObject(i).getJsonObject("myProduct").getString("name")));
		}
		return cartLineIdsList;
	}

	/** end get cartLines ids **/
	@Override
	public List<Product> GetProductByCartLinesIds(int parentId) {

		List<Integer> cartLineIdsList = GetCartLinesProductsByCartId(parentId);

		Client client = ClientBuilder.newClient();

		ArrayList<Product> productList = new ArrayList<Product>();
		for (int i = 0; i < cartLineIdsList.size(); i++) {
			WebTarget web = client
					.target("http://" + GlobalEndPoint + "/Product/Api/GetProductById/" + cartLineIdsList.get(i));

			Response response = web.request().get();

			String result = response.readEntity(String.class);

			JsonReader jsonReader = Json.createReader(new StringReader(result));
			JsonObject object = jsonReader.readObject();

			Product myProduct = new Product(object.getString("name"), object.getInt("price"),
					object.getString("img1Base64"));

//			System.out.println(myProduct.toString());
			productList.add(myProduct);
		}
		return productList;

	}

//	@Override
//	public String PaymentWithPaypalWithOutLoyalPoint(int parentId) {
//
//
//		Client client = ClientBuilder.newClient();
//		
//			WebTarget web = client
//					.target("http://"+GlobalEndPoint+"/Product/PaymentWithPaypalWS?parentId="+parentId);
//
//			Response response =web.request().post(null);
//			
//			String result = response.readEntity(String.class).replace("t\\u0026t", "t&t");
//			result = result.replace("\"", "");
//			System.out.println(result);
//			return result ;
//	}

	@Override
	public List<Cart> searchProductByName(String name, List<Cart> cartList) {

		List<Cart> cartLitSearched = new ArrayList<Cart>();
		for (Cart cart : cartList) {
			List<CartLine> cartLineList = cart.getCartLines();
			for (CartLine cartLine : cartLineList) {
				System.out.println(cartLine.getProduct().getName());
				if (cartLine.getProduct().getName().equals(name)) {
					cartLitSearched.add(cart);
					break;
				}

			}
		}
		return cartLitSearched;

	}

	@Override
	public List<Cart> searchCartByDate(Date dateFromView1, Date dateFromView2, List<Cart> cartList)
			throws ParseException {

		List<Cart> cartLitSearched = new ArrayList<Cart>();
		for (Cart cart : cartList) {
			String stringDateFromView1 = new SimpleDateFormat("yyyy-MM-dd").format(dateFromView1);
			String stringDateFromView2 = new SimpleDateFormat("yyyy-MM-dd").format(dateFromView2);
			String stringCartDate = new SimpleDateFormat("yyyy-MM-dd").format(cart.getCartDate());

			Date dateFromView1Formated = new SimpleDateFormat("yyyy-MM-dd").parse(stringDateFromView1);
			Date dateFromView2Formated = new SimpleDateFormat("yyyy-MM-dd").parse(stringDateFromView2);
			Date cartDateFormated = new SimpleDateFormat("yyyy-MM-dd").parse(stringCartDate);

			Calendar c_dateFromView1Formated = Calendar.getInstance();
			Calendar c_dateFromView2Formated = Calendar.getInstance();
			c_dateFromView1Formated.setTime(dateFromView1Formated);
			c_dateFromView2Formated.setTime(dateFromView2Formated);
			c_dateFromView1Formated.add(Calendar.DATE, -1);
			c_dateFromView2Formated.add(Calendar.DATE, 1);
			System.out.println(c_dateFromView1Formated.getTime());
			System.out.println(c_dateFromView2Formated.getTime());

			// System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(dateFromView1));
			// System.out.println(new
			// SimpleDateFormat("yyyy-MM-dd").format(cart.getCartDate()));

//			if(dateFromView2 == null && cart.getCartDate().after(dateFromView1)) {
//					cartLitSearched.add(cart);
//			}
//			
//			if(dateFromView1 == null && cart.getCartDate().before(dateFromView2)) {
//				cartLitSearched.add(cart);
//	      	}
//		

			if (cartDateFormated.after(c_dateFromView1Formated.getTime())
					&& cartDateFormated.before(c_dateFromView2Formated.getTime())) {
				cartLitSearched.add(cart);
			}

		}
		return cartLitSearched;

	}

	@Override
	public boolean TestCreationPointsLoyalty(int productId) {
		Client client = ClientBuilder.newClient();
		WebTarget web = client
				.target("http://" + GlobalEndPoint + "/Product/Api/TestCreationPointsLoyalty/" + productId);
		Response response = web.request().post(null);
		String result = response.readEntity(String.class);

		response.close();
		if (result.equals("\"yes\"") || result == "\"yes\"") {
			return true;

		} else
			return false;

	}

	@Override
	public void CreatePointsLoyalty(int productId) {
		Client client = ClientBuilder.newClient();
		WebTarget web = client.target("http://" + GlobalEndPoint + "/Product/Api/CreatePointsLoyalty/" + productId);
		Response response = web.request().post(null);
		String result = response.readEntity(String.class);
		response.close();

	}

	/********** function to convert numbers to words **************/

	private static final String[] tensNames = { "", " ten", " twenty", " thirty", " forty", " fifty", " sixty",
			" seventy", " eighty", " ninety" };

	private static final String[] numNames = { "", " one", " two", " three", " four", " five", " six", " seven",
			" eight", " nine", " ten", " eleven", " twelve", " thirteen", " fourteen", " fifteen", " sixteen",
			" seventeen", " eighteen", " nineteen" };

	private static String convertLessThanOneThousand(int number) {
		String soFar;

		if (number % 100 < 20) {
			soFar = numNames[number % 100];
			number /= 100;
		} else {
			soFar = numNames[number % 10];
			number /= 10;

			soFar = tensNames[number % 10] + soFar;
			number /= 10;
		}
		if (number == 0)
			return soFar;
		return numNames[number] + " hundred" + soFar;
	}

	public static String convertQuantityChoosed(long number) {
		// 0 to 999 999 999 999
		if (number == 0) {
			return "zero";
		}

		String snumber = Long.toString(number);

		// pad with "0"
		String mask = "000000000000";
		DecimalFormat df = new DecimalFormat(mask);
		snumber = df.format(number);

		// XXXnnnnnnnnn
		int billions = Integer.parseInt(snumber.substring(0, 3));
		// nnnXXXnnnnnn
		int millions = Integer.parseInt(snumber.substring(3, 6));
		// nnnnnnXXXnnn
		int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
		// nnnnnnnnnXXX
		int thousands = Integer.parseInt(snumber.substring(9, 12));

		String tradBillions;
		switch (billions) {
		case 0:
			tradBillions = "";
			break;
		case 1:
			tradBillions = convertLessThanOneThousand(billions) + " billion ";
			break;
		default:
			tradBillions = convertLessThanOneThousand(billions) + " billion ";
		}
		String result = tradBillions;

		String tradMillions;
		switch (millions) {
		case 0:
			tradMillions = "";
			break;
		case 1:
			tradMillions = convertLessThanOneThousand(millions) + " million ";
			break;
		default:
			tradMillions = convertLessThanOneThousand(millions) + " million ";
		}
		result = result + tradMillions;

		String tradHundredThousands;
		switch (hundredThousands) {
		case 0:
			tradHundredThousands = "";
			break;
		case 1:
			tradHundredThousands = "one thousand ";
			break;
		default:
			tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " thousand ";
		}
		result = result + tradHundredThousands;

		String tradThousand;
		tradThousand = convertLessThanOneThousand(thousands);
		result = result + tradThousand;

		// remove extra spaces!
		return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	}

	/********** end function to convert numbers to words **************/

}
