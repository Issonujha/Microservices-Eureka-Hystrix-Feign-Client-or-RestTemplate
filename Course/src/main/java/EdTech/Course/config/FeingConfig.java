package EdTech.Course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeingConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate requestTemplate) {
				// Replace with actual token retrieval logic
				String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzamhhOTU2M0BnbWFpbC5jb20iLCJpYXQiOjE3MzQ2Mzg1NjcsImV4cCI6MTczNDcyNDk2N30.HGKax0WgYWhcltB-L9NopusOarjsUgibd-u1UCkZ4sb4gNZORucuZU1VXauHUMRYtH4n8WZuCHJJ53yDz7TTlQ";
				requestTemplate.header("Authorization", token);
			}
		};
	}
	
}
