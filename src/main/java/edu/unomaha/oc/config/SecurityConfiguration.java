package edu.unomaha.oc.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import edu.unomaha.oc.database.JdbcUserDao;
import edu.unomaha.oc.utilities.AuthUtilities;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private static String[] PERMITTED_URLS = {"/", "/index.html", "/webjars/**", "/js/**", "/font-awesome/**", 
			"/css/**", "/views/**", "/img/**", "/#!/**", "/login","/api/dashboard", "/api/stories"};
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic()
			.and()
				.authorizeRequests().antMatchers(PERMITTED_URLS).permitAll().anyRequest().authenticated()
			.and()
				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new JdbcUserDao();
	}
	
	@Bean
	public AuthUtilities auth() {
		return new AuthUtilities();
	}
	
//  @Autowired
//  OAuth2ClientContext oauth2ClientContext;
//	
//	@Bean
//  public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
//      FilterRegistrationBean registration = new FilterRegistrationBean();
//      registration.setFilter(filter);
//      registration.setOrder(-100);
//      return registration;
//  }
//  
//  @Bean
//  @ConfigurationProperties("facebook.client")
//  public AuthorizationCodeResourceDetails facebook() {
//      return new AuthorizationCodeResourceDetails();
//  }
//
//  @Bean
//  @ConfigurationProperties("facebook.resource")
//  public ResourceServerProperties facebookResource() {
//      return new ResourceServerProperties();
//  }
//	
//  @Bean
//  public Filter ssoFilter() {
//      OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter(
//              "/login/facebook");
//      OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
//      facebookFilter.setRestTemplate(facebookTemplate);
//      facebookFilter.setTokenServices(
//              new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId()));
//      return facebookFilter;
//  }
//  
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
	   // TODO: Figure out the login stuff here
//  	http.antMatcher("#!/**").authorizeRequests().
//      		antMatchers("/", "/login**", "/webjars/**", "/css/**", "/fonts/**", "/img/**", "/scripts/**", "/font-awesome/**").permitAll().anyRequest()
//              .authenticated().and().exceptionHandling()
//              .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("#!/login")).and().logout()
//              .logoutSuccessUrl("/").permitAll().and().csrf()
//              .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//              .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
//  }
}
