package tn.cloudnerds.entities;


import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonDeserialize(using = ApplicationUserDeserializer.class)
public class ApplicationUser {
	//@JsonProperty("Id")
	//@SerializedName("Id")
	private int id;
	//@JsonProperty("HomeTown")
	//@SerializedName("HomeTown")
	private String homeTown;
	//@JsonProperty("Email")
	//@SerializedName("Email")
	private String email;
	//@JsonProperty("UserName")
	//@SerializedName("UserName")
	private String userName;
	//@JsonProperty("EmailConfirmed")
	//@SerializedName("EmailConfirmed")
	private int emailConfirmed;
	//@JsonProperty("Address")
	//@SerializedName("Address")
	private String passwordHash;
	//@JsonProperty("SecurityStamp")
	//@SerializedName("SecurityStamp")
	private String securityStamp;
	//@JsonProperty("PhoneNumber")
	//@SerializedName("PhoneNumber")
	private String phoneNumber;
	//@JsonProperty("PhoneNumberConfirmed")
	//@SerializedName("PhoneNumberConfirmed")
	private int phoneNumberConfirmed;
	//@JsonProperty("TwoFactorEnabled")
	//@SerializedName("TwoFactorEnabled")
	private int twoFactorEnabled;
	//@JsonProperty("LockoutEndDateUtc")
	//@SerializedName("LockoutEndDateUtc")
	private DateTime lockoutEndDateUtc;
	//@JsonProperty("LockoutEnabled")
	//@SerializedName("LockoutEnabled")
	private int lockoutEnabled;
	//@JsonProperty("AccessFailedCount")
	//@SerializedName("AccessFailedCount")
	private int accessFailedCount;
	
	
	public ApplicationUser() {
		
	}
	
	public ApplicationUser(int id, String homeTown, String email, String userName, int emailConfirmed,
			String passwordHash, String securityStamp, String phoneNumber, int phoneNumberConfirmed,
			int twoFactorEnabled, DateTime lockoutEndDateUtc, int lockoutEnabled, int accessFailedCount) {
		super();
		this.id = id;
		this.homeTown = homeTown;
		this.email = email;
		this.userName = userName;
		this.emailConfirmed = emailConfirmed;
		this.passwordHash = passwordHash;
		this.securityStamp = securityStamp;
		this.phoneNumber = phoneNumber;
		this.phoneNumberConfirmed = phoneNumberConfirmed;
		this.twoFactorEnabled = twoFactorEnabled;
		this.lockoutEndDateUtc = lockoutEndDateUtc;
		this.lockoutEnabled = lockoutEnabled;
		this.accessFailedCount = accessFailedCount;
	}

	

	public ApplicationUser(int id, String email, String userName) {
		super();
		this.id = id;
		this.email = email;
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHomeTown() {
		return homeTown;
	}

	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getEmailConfirmed() {
		return emailConfirmed;
	}

	public void setEmailConfirmed(int emailConfirmed) {
		this.emailConfirmed = emailConfirmed;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getSecurityStamp() {
		return securityStamp;
	}

	public void setSecurityStamp(String securityStamp) {
		this.securityStamp = securityStamp;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getPhoneNumberConfirmed() {
		return phoneNumberConfirmed;
	}

	public void setPhoneNumberConfirmed(int phoneNumberConfirmed) {
		this.phoneNumberConfirmed = phoneNumberConfirmed;
	}

	public int getTwoFactorEnabled() {
		return twoFactorEnabled;
	}

	public void setTwoFactorEnabled(int twoFactorEnabled) {
		this.twoFactorEnabled = twoFactorEnabled;
	}

	public DateTime getLockoutEndDateUtc() {
		return lockoutEndDateUtc;
	}

	public void setLockoutEndDateUtc(DateTime lockoutEndDateUtc) {
		this.lockoutEndDateUtc = lockoutEndDateUtc;
	}

	public int getLockoutEnabled() {
		return lockoutEnabled;
	}

	public void setLockoutEnabled(int lockoutEnabled) {
		this.lockoutEnabled = lockoutEnabled;
	}

	public int getAccessFailedCount() {
		return accessFailedCount;
	}

	public void setAccessFailedCount(int accessFailedCount) {
		this.accessFailedCount = accessFailedCount;
	}



	@Override
	public String toString() {
		return "ApplicationUser [id=" + id + ", homeTown=" + homeTown + ", email=" + email + ", userName=" + userName
				+ ", emailConfirmed=" + emailConfirmed + ", passwordHash=" + passwordHash + ", securityStamp="
				+ securityStamp + ", phoneNumber=" + phoneNumber + ", phoneNumberConfirmed=" + phoneNumberConfirmed
				+ ", twoFactorEnabled=" + twoFactorEnabled + ", lockoutEndDateUtc=" + lockoutEndDateUtc
				+ ", lockoutEnabled=" + lockoutEnabled + ", accessFailedCount=" + accessFailedCount + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accessFailedCount;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + emailConfirmed;
		result = prime * result + ((homeTown == null) ? 0 : homeTown.hashCode());
		result = prime * result + id;
		result = prime * result + lockoutEnabled;
		result = prime * result + ((lockoutEndDateUtc == null) ? 0 : lockoutEndDateUtc.hashCode());
		result = prime * result + ((passwordHash == null) ? 0 : passwordHash.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + phoneNumberConfirmed;
		result = prime * result + ((securityStamp == null) ? 0 : securityStamp.hashCode());
		result = prime * result + twoFactorEnabled;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationUser other = (ApplicationUser) obj;
		if (accessFailedCount != other.accessFailedCount)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (emailConfirmed != other.emailConfirmed)
			return false;
		if (homeTown == null) {
			if (other.homeTown != null)
				return false;
		} else if (!homeTown.equals(other.homeTown))
			return false;
		if (id != other.id)
			return false;
		if (lockoutEnabled != other.lockoutEnabled)
			return false;
		if (lockoutEndDateUtc == null) {
			if (other.lockoutEndDateUtc != null)
				return false;
		} else if (!lockoutEndDateUtc.equals(other.lockoutEndDateUtc))
			return false;
		if (passwordHash == null) {
			if (other.passwordHash != null)
				return false;
		} else if (!passwordHash.equals(other.passwordHash))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (phoneNumberConfirmed != other.phoneNumberConfirmed)
			return false;
		if (securityStamp == null) {
			if (other.securityStamp != null)
				return false;
		} else if (!securityStamp.equals(other.securityStamp))
			return false;
		if (twoFactorEnabled != other.twoFactorEnabled)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	
	
}
