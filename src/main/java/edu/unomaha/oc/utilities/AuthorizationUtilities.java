package edu.unomaha.oc.utilities;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

public class AuthorizationUtilities {

	public static int getActiveUser(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return -1;
	}
	
	public static String getSalt() {
		StringKeyGenerator kg = KeyGenerators.string();
		String salt = kg.generateKey();
		return salt;
	}
	
	public static String encode(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}
	
	public static boolean matches(String password, String encodedPassword) {
		return new BCryptPasswordEncoder().matches(password, encodedPassword);
	}
	
}
