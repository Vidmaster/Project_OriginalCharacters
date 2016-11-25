package edu.unomaha.oc.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.unomaha.oc.database.CharacterDao;
import edu.unomaha.oc.database.JdbcCharacterDao;
import edu.unomaha.oc.database.JdbcStoryDao;
import edu.unomaha.oc.database.JdbcUserDao;
import edu.unomaha.oc.database.StoryDao;
import edu.unomaha.oc.database.UserDao;

@Configuration
public class OriginalCharactersConfiguration { 
	
	@Value("${DB_USER}")
	private String username;
	
	@Value("${DB_PASSWORD}")
	private String password;
	
	@Value("${DB_URL}")
	private String url;
	
	@Bean
	public DataSource getDataSource() {
		return DataSourceBuilder.create()
				.driverClassName("com.mysql.jdbc.Driver")
				.username(username)
				.password(password)
				.url(url)
				.build();
	}
	
	@Bean
	public StoryDao storyDao() {
		return new JdbcStoryDao();
	}
	
	@Bean
	public UserDao userDao() {
		return new JdbcUserDao();
	}
	
	@Bean
	public CharacterDao characterDao() {
		return new JdbcCharacterDao();
	}
	
}
