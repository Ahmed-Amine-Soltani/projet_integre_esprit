package projet.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import projet.models.Utilisateur;

public class ServiceLogin_1 {

	// pour la hashage de mot de passe "sha512" ( le hachage que fosuserBundle
	// utilise )
	/*public static String get_SHA_512_SecurePassword(String passwordToHash, String salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes(StandardCharsets.UTF_8));
			byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}*/
	
	public static int Inscription(Utilisateur utilisateur) {
		// Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value en regarde le cryptage de fosuserbundle il utilise $2y$13$.
		  int workload = 13;
		int status = 0;
		String sql = "INSERT INTO fos_user(username,password,email,roles,enabled,username_canonical) VALUES(?,?,?,?,?,?)";
		System.out.println(sql);

		try {
			Connection connexion = ServiceEvenement.creationConnexion();
			PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
			preparedStatement.setString(1, utilisateur.getNom_Utilisateur());
			/* pour que le hachage de mot de passe soit le meme dont FOSUSERBUNDLE utilise on a utilise le replaceFirst parce que le cryptage de fosuserbundle 
                        commence par 2y*/ 
			 String mdp = BCrypt.hashpw(utilisateur.getMotDePasse_Utilisateur(), BCrypt.gensalt(workload));
			 preparedStatement.setString(2, mdp.replaceFirst("2a","2y"));
			//preparedStatement.setString(2, utilisateur.getMotDePasse_Utilisateur());
			preparedStatement.setString(3, utilisateur.getEmail());
			// a:0:{} pour le role user
			preparedStatement.setString(4, "a:0:{}");
			// enabled
			preparedStatement.setInt(5, 1);
			// username_canonical prend la meme valeur de username
			preparedStatement.setString(6, utilisateur.getNom_Utilisateur());
			status = preparedStatement.executeUpdate();
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static boolean testMotDePasse(String motDePasseGUI, String motDePasseBD) {
		boolean password_verified = false;

		if(null == motDePasseBD)
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
		
		// en remplaçant 2y par 2a le cryptage on obtient le cryptage par defaut pour que la methode checkpw puisse comparer
		password_verified = BCrypt.checkpw(motDePasseGUI, motDePasseBD.replaceFirst("2y", "2a"));

		return(password_verified);
	}
	
	public static List<Utilisateur> getTtUtilisateur() {
		List<Utilisateur> list = new ArrayList<Utilisateur>();
		try {
			String sql = "select * from fos_user ";
			Connection connexion = ServiceEvenement.creationConnexion();
			PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Utilisateur utilisateur = new Utilisateur();
				
                                // l'id pour pouvoir connecté a son accueil spacifique
                                utilisateur.setId_Utilisateur(resultSet.getInt("id"));
                                // nom et mot de passe pour login
				utilisateur.setNom_Utilisateur(resultSet.getString("username"));
				utilisateur.setMotDePasse_Utilisateur(resultSet.getString("password"));
                                

				list.add(utilisateur);
			}
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return list;

	}
        
        public static Utilisateur getUtilisateur(String nomUtilisateur){
            Utilisateur utilisateur = new Utilisateur();
		try {
			String sql = "select * from fos_user where username = ?";
			Connection connexion = ServiceEvenement.creationConnexion();
			PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement(sql);
                        preparedStatement.setString(1, nomUtilisateur);
			ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
                                utilisateur.setId_Utilisateur(resultSet.getInt("id"));
				utilisateur.setNom_Utilisateur(resultSet.getString("username"));
				utilisateur.setEmail(resultSet.getString("email"));
				utilisateur.setRole_Utilisateur(resultSet.getString("roles"));
                                }

			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return utilisateur;

	}
        }
