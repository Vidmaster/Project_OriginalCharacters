package edu.unomaha.oc.utilities;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.unomaha.oc.domain.User;

public class AuthUtilities {
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public int getActiveUser(HttpServletRequest request) {
		try {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user != null) {
				return user.getId();
			} else {
				return -1;
			}
		} catch (Exception ex) {
			return -1;
		}
	}
	
	public String encode(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}
	
	public boolean matches(String password, String encodedPassword) {
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
