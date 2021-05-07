package com.algaworks.algamoneyapi.core.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("algamoney")
@Getter
@Setter
public class AlgamoneyApiProperty {
	
	private String originPermitida = "http://localhost:8000";
	
	private final Seguranca seguranca = new Seguranca();
	
	@Getter
	@Setter
	public static class Seguranca {
		
		private boolean enableHttps;
		
	}
	
}
