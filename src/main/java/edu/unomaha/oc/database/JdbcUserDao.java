package edu.unomaha.oc.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.unomaha.oc.domain.User;

public class JdbcUserDao implements UserDao, UserDetailsService {
	private static final String ALL_COLUMNS = "id, username, email, description, facebookId, password, enabled";
	private static final String ALL_COLUMNS_NO_PASSWORD = "id, username, email, description, facebookId, '[PROTECTED]' AS password, enabled";
	private static final String DEFAULT_ROLE = "ROLE_USER";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DataSource dataSource;
	
	/**
	 * This method is required for spring security to lookup the user from the database by username or email address
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("loadUserByUsername(): username=" + username);
		
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("username", username);
		
		User user = template.queryForObject("SELECT " + ALL_COLUMNS + " FROM users WHERE username=:username OR email=:username", paramMap, new UserRowMapper());
		
		logger.debug("Returning user: " + user);
		return user;
	}

	public List<User> searchByUsername(String username) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("username", '%' + username + '%');
		
		List<User> users = template.query("SELECT " + ALL_COLUMNS_NO_PASSWORD + " FROM users WHERE username LIKE ':username'", paramMap, new UserRowMapper()); 
		
		return users;
	}
	
	public User readUser(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		
		User user = template.queryForObject("SELECT " + ALL_COLUMNS_NO_PASSWORD + " FROM users WHERE id=:id", paramMap, new UserRowMapper());
		return user;
	}
	
	public User readUserWithPass(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		
		User user = template.queryForObject("SELECT " + ALL_COLUMNS + " FROM users WHERE id=:id", paramMap, new UserRowMapper());
		return user;
	}
	
	
	public Number createUser(User user) {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		
		paramMap.addValue("username", user.getUsername());
		paramMap.addValue("password", user.getPassword());
		paramMap.addValue("email", user.getEmail());
		paramMap.addValue("description", user.getDescription());
		paramMap.addValue("enabled", true);
		paramMap.addValue("role", DEFAULT_ROLE);
		
		try {
			NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			template.update("INSERT INTO users (username, password, email, description, enabled) VALUES (:username, :password, :email, :description, :enabled)", paramMap, keyHolder);
			template.update("INSERT INTO authorities (username, authority) VALUES (:username, :role)", paramMap);
			
			logger.debug("Created user with id=" + keyHolder.getKey());
			
			return keyHolder.getKey();
		} catch (Exception ex) {
			Map<String,Object> params = paramMap.getValues();
			params.remove("password");
			
			logger.error("Error creating user: Params=" + params + " Message=" + ex.getMessage());
			throw ex;
		}
	}
	
	public void updateUser(int id, User user) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		paramMap.addValue("email", user.getEmail());
		paramMap.addValue("description", user.getDescription());
		template.update("UPDATE users SET description=:description, email=:email WHERE id=:id ", paramMap);
	}
	
	public void deleteUser(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		
		template.update("UPDATE users set enabled=false WHERE id=:id ", paramMap);
	}

	@Override
	public void updateUserPassword(int id, String password) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		paramMap.addValue("password", password);
		
		template.update("UPDATE users SET password=:password WHERE id=:id", paramMap);
	}
	
}
