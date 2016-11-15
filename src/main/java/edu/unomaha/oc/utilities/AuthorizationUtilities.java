package edu.unomaha.oc.utilities;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// TODO: Rename this to AuthenticationUtilities or general AuthUtilities
public class AuthorizationUtilities {

	public static int getActiveUser(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return -1;
	}
	
	public static String encode(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}
	
	public static boolean matches(String password, String encodedPassword) {
		return new BCryptPasswordEncoder().matches(password, encodedPassword);
	}
	
}
