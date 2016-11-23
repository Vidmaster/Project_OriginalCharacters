package edu.unomaha.oc.utilities;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.unomaha.oc.domain.User;

public class AuthUtilities {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
	
	public boolean isAuthorized(long ownerId) {
		try {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user != null) {
					long userId = user.getId();
					if (userId == ownerId) {
						return true;
					} else {
						return false;
					}
				} else {
				  return false;
				}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return false;
		}
	}
}
