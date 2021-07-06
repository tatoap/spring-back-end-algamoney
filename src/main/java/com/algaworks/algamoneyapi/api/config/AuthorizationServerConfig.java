package com.algaworks.algamoneyapi.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.algaworks.algamoneyapi.token.CustomTokenEnhancer;

@SuppressWarnings("deprecation")
@Profile("oauth-security")
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("angular")
			.secret("$2a$10$n.dYvSCqVDkFFmKBRu9nL.ja7L8d6T0.GjRT58E0EB2dWvYmvqDlu")
			.scopes("read", "write")
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(1800)
			.refreshTokenValiditySeconds(3600 * 24)
		.and()
			.withClient("mobile")
			.secret("$2a$10$n.dYvSCqVDkFFmKBRu9nL.ja7L8d6T0.GjRT58E0EB2dWvYmvqDlu")
			.scopes("read")
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(1800)
			.refreshTokenValiditySeconds(3600 * 24);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
		
		endpoints
			.tokenStore(tokenStore())
			//.accessTokenConverter(this.jwtAccessTokenConverter())
			.tokenEnhancer(tokenEnhancerChain)
			.reuseRefreshTokens(false)
			.userDetailsService(this.userDetailsService)
			.authenticationManager(this.authenticationManager);
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey("3032885ba9cd6621bcc4e7d6b6c35c2b");
		return jwtAccessTokenConverter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}
	
}
