package tn.cloudnerds.interfaces;

import javax.ejb.Remote;

import tn.cloudnerds.entities.ApplicationUser;

@Remote
public interface UserServiceRemote {
	public ApplicationUser login(String email, String password);
}
