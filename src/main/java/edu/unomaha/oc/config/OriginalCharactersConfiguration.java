package edu.unomaha.oc.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.unomaha.oc.database.CharacterDao;
import edu.unomaha.oc.database.CommentDao;
import edu.unomaha.oc.database.ContributionDao;
import edu.unomaha.oc.database.InvitationDao;
import edu.unomaha.oc.database.JdbcCharacterDao;
import edu.unomaha.oc.database.JdbcCommentDao;
import edu.unomaha.oc.database.JdbcContributionDao;
import edu.unomaha.oc.database.JdbcInvitationDao;
import edu.unomaha.oc.database.JdbcStoryDao;
import edu.unomaha.oc.database.JdbcUserDao;
import edu.unomaha.oc.database.StoryDao;
import edu.unomaha.oc.database.UserDao;

/**
 *	This class houses application configuration information for the Spring application.
 *  All methods annotated with @Bean are injected by the spring framework's dependency injection mechanisms
 *   at runtime. Alternate configurations can be swapped in for testing purposes, which would allow using
 *   a mocked DataSource or mocked Dao objects to return fake data and remove the dependency on an external MySQL database.
 */
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
	
	@Bean
	public ContributionDao contributionDao() {
		return new JdbcContributionDao();
	}
	
	@Bean
	public CommentDao commentDao() {
		return new JdbcCommentDao();
	}
	
	@Bean
	public InvitationDao invitationDao() {
		return new JdbcInvitationDao();
	}
	
}
