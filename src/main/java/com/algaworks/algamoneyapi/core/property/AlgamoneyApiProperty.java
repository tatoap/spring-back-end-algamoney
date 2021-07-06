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
	
	private final Mail mail = new Mail();
	
	private final S3 s3 = new S3();
	
	@Getter
	@Setter
	public static class Seguranca {
		
		private boolean enableHttps;
		
	}
	
	@Getter
	@Setter
	public static class Mail {
		
		private String host;
		
		private Integer port;
		
		private String username;
		
		private String password;
		
	}
	
	@Getter
	@Setter
	public static class S3 {
		
		private String accessKeyId;
		
		private String secretAccessKey;
		
		private String regiao;
		
		private String bucket;
	}
	
}
