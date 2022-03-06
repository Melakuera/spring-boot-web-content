package kg.melakuera.springwebcontent.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kg.melakuera.springwebcontent.service.AppUserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final AppUserService appUserService;
	
	public WebSecurityConfig(@Lazy AppUserService appUserService) {
		this.appUserService = appUserService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/", "/register").permitAll()
				.antMatchers("/resources/**").permitAll()
			.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/login").permitAll()
			.and()
				.logout().permitAll()
				.logoutSuccessUrl("/")
				//.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}	
	
	@Bean
	protected DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(appUserService);
		
		return provider;
	}
	
	@Bean
	protected BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
}