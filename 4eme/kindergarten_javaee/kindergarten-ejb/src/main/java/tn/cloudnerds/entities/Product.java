package tn.cloudnerds.entities;
import java.io.Serializable;

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public int id ;
	public String name;
	public double price ;
	public String img1;
	public String img2;
	public String img3;
	public boolean productStatus;
	public String description;
	
	
	public Product(int id, String name, String img1, String img2, String img3) {
		this.id = id;
		this.name = name;
		this.img1 = img1;
		this.img2 = img2;
		this.img3 = img3;
	}

	public Product(String name, double price ,String img1) {
		super();
		this.name = name;
		this.price = price ;
		this.img1 = img1;
	}

	public Product() {
		super();
	}  

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}





	public void setPrice(double price) {
		this.price = price;
	}





	public String getImg2() {
		return img2;
	}





	public void setImg2(String img2) {
		this.img2 = img2;
	}





	public String getImg3() {
		return img3;
	}





	public void setImg3(String img3) {
		this.img3 = img3;
	}





	public boolean isProductStatus() {
		return productStatus;
	}





	public void setProductStatus(boolean productStatus) {
		this.productStatus = productStatus;
	}





	public String getDescription() {
		return description;
	}





	public void setDescription(String description) {
		this.description = description;
	}





	public String getImg1() {
		return img1;
	}


	public void setImg1(String img1) {
		this.img1 = img1;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}





	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", img1=" + img1 + ", img2=" + img2
				+ ", img3=" + img3 + ", productStatus=" + productStatus + ", description=" + description + "]";
	}

	
	


}
