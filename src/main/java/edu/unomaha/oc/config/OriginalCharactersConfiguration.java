package edu.unomaha.oc.config;

import javax.servlet.Filter;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
//import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import edu.unomaha.oc.database.JdbcStoryDao;
import edu.unomaha.oc.database.StoryDao;

@Configuration
public class OriginalCharactersConfiguration { // extends WebSecurityConfigurerAdapter {
	
//    @Autowired
//    OAuth2ClientContext oauth2ClientContext;
//	
	@Value("${DB_USER}")
	private String username;
	
	@Value("${DB_PASSWORD}")
	private String password;
	
	@Value("${DB_URL}")
	private String url;
	
	@Bean
	public DataSource getDataSource() {
		System.out.println("Creating datasource bean");
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
	
//	@Bean
//    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(filter);
//        registration.setOrder(-100);
//        return registration;
//    }
//    
//    @Bean
//    @ConfigurationProperties("facebook.client")
//    public AuthorizationCodeResourceDetails facebook() {
//        return new AuthorizationCodeResourceDetails();
//    }
//
//    @Bean
//    @ConfigurationProperties("facebook.resource")
//    public ResourceServerProperties facebookResource() {
//        return new ResourceServerProperties();
//    }
//	
//    @Bean
//    public Filter ssoFilter() {
//        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter(
//                "/login/facebook");
//        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
//        facebookFilter.setRestTemplate(facebookTemplate);
//        facebookFilter.setTokenServices(
//                new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId()));
//        return facebookFilter;
//    }
//	
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//    	http.antMatcher("/**").authorizeRequests().
//        		antMatchers("/", "/login**", "/webjars/**", "/css/**", "/fonts/**", "/img/**", "/scripts/**", "/font-awesome/**").permitAll().anyRequest()
//                .authenticated().and().exceptionHandling()
//                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and().logout()
//                .logoutSuccessUrl("/").permitAll().and().csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
//    }
}
