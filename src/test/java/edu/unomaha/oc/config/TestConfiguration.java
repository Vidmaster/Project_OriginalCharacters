package edu.unomaha.oc.config;

import javax.sql.DataSource;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.unomaha.oc.database.CharacterDao;
import edu.unomaha.oc.database.CommentDao;
import edu.unomaha.oc.database.ContributionDao;
import edu.unomaha.oc.database.InvitationDao;
import edu.unomaha.oc.database.JdbcCommentDao;
import edu.unomaha.oc.database.JdbcInvitationDao;
import edu.unomaha.oc.database.JdbcUserDao;
import edu.unomaha.oc.database.StoryDao;
import edu.unomaha.oc.database.UserDao;
import edu.unomaha.oc.utilities.AuthUtilities;

public class TestConfiguration {

	private String username = "root";
	
	private String password = "root";
	
	private String url = "jdbc:mysql://localhost:3306/oc?autoReconnect=true";
	
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
		return Mockito.mock(StoryDao.class);
	}
	
	@Bean
	public UserDao userDao() {
		return Mockito.mock(UserDao.class);
	}
	
	@Bean
	public CharacterDao characterDao() {
		return Mockito.mock(CharacterDao.class);
	}
	
	@Bean
	public ContributionDao contributionDao() {
		return Mockito.mock(ContributionDao.class);
	}
	
	@Bean
	public CommentDao commentDao() {
		return new JdbcCommentDao();
	}
	
	@Bean
	public InvitationDao invitationDao() {
		return new JdbcInvitationDao();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return Mockito.mock(JdbcUserDao.class);
	}
	
	@Bean
	public AuthUtilities auth() {
		return Mockito.mock(AuthUtilities.class);
	}
	
}
