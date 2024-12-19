package EdTech.Course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignConfig {
	
	@Bean
	public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
			public void apply(RequestTemplate requestTemplate) {
				// Replace with actual token retrieval logic
				String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzamhhOTU2M0BnbWFpbC5jb20iLCJpYXQiOjE3MzQ2MTk5NTcsImV4cCI6MTczNDcwNjM1N30.MkZvIqFKK289mDkz8bbAchsFHV4yT6tdjbRAfpinjDlE8PnDBlsAYgmhLXYV7w5INoWkMT25ZvA85A1RFCihgg";
				requestTemplate.header("Authorization", token);
			}
        };
    }

}
