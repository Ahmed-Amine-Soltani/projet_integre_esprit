package tn.cloudnerds.managedbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import tn.cloudnerds.entities.ApplicationUser;
import tn.cloudnerds.interfaces.UserServiceLocal;


@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean implements Serializable {

	private String email; 
	private String password; 
	private ApplicationUser user; 
	private boolean jsfTest = false;
	
	//added by soltani
	public static ApplicationUser productUser; 
	
	@EJB
	UserServiceLocal userService;
	
	
	
	public LoginBean() {
		//super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ApplicationUser getUser() {
		return user;
	}

	public void setUser(ApplicationUser user) {
		this.user = user;
	}
	
	public boolean isJsfTest() {
		return jsfTest;
	}

	public void setJsfTest(boolean jsfTest) {
		this.jsfTest = jsfTest;
	}
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String doLogin() {
		String navigateTo = "null";
		user = userService.login(email, password);
		
		// added by soltani
		productUser = user;
		//
		
		if (user != null) {
			System.out.println("Success " + user.getEmail());
			setJsfTest(true) ;
			navigateTo = "/template/index?faces-redirect=true"; 
			}
		else {
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Bad Credentials"));
		}
		return navigateTo; 
	}
	
	public String logoff() {
		System.out.println("logoff");
		setJsfTest(false) ;
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/template/login?faces-redirect=true";
	}

	
	
}
