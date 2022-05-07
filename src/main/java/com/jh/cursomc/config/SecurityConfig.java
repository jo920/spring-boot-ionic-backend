package com.jh.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Autowired
	private Environment env;
	
    private static final String[]PUBLIC_MATCHERS_GET = {  //Criei um vetor para mostrar o que deve estar liberado para o Spring Security
			
			"/produtos/**",
			"/categorias/**"
	};
	
	
	
	
	private static final String[]PUBLIC_MATCHERS = {  
			
			"/h2-console/**"
	};
	
	
	
	// esse metodo config significa que tudo que estiver no PUBLIC_MATCHERS esta permitido, no entanto, para os que nao
	//tiverem incluidos precisa de autenticação.
	@Override
	protected void configure(HttpSecurity http) throws Exception{                                  
		if(Arrays.asList(env.getActiveProfiles()).contains("test")){
			
			http.headers().frameOptions().disable();
		}		
		http.cors().and().csrf().disable();
		http.authorizeRequests()
		    .antMatchers(PUBLIC_MATCHERS).permitAll()
		    .antMatchers(HttpMethod.GET,PUBLIC_MATCHERS_GET).permitAll()
		    .anyRequest().authenticated();
		
	}	
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**",new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	}
	
}
