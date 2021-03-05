package tn.cloudnerds.services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.jayway.jsonpath.JsonPath;

import tn.cloudnerds.entities.ApplicationUser;
import tn.cloudnerds.interfaces.UserServiceLocal;
import tn.cloudnerds.interfaces.UserServiceRemote;


/**
 * Session Bean implementation class UserService
 */
@Stateless
@LocalBean
public class UserService implements UserServiceRemote, UserServiceLocal {

	public String GlobalEndPoint = "localhost:1958";
	
	/**
     * Default constructor. 
     */
    public UserService() {
        
    }

	@Override
	public ApplicationUser login(String email, String password) {
    	Client client = ClientBuilder.newClient();
    	
    	WebTarget web = client.target("http://" + GlobalEndPoint + "/api/user/"); 
    	
    	WebTarget user = web.path(email + "/" + password);
    	
    	Response response = user.request().get();
    	
    	String result = response.readEntity(String.class); 

    	int id = JsonPath.read(result, "$.Result.Id");
    	String username = JsonPath.read(result, "$.Result.UserName");
    	
    	ApplicationUser appUser = new ApplicationUser(id, email, username);
    
		System.out.println("JSON Id " + id);
    
    	response.close(); 

    	return appUser;    	
	
	}

}
