package EdTech.Course.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import EdTech.Course.config.FeingConfig;
import EdTech.Course.dto.UserDetailsResponse;


@FeignClient(name = "USER-SERVICE", configuration = FeingConfig.class)
public interface UserService {
	
	@GetMapping("/user/{id}")
	public Object getUserById(@RequestHeader("Authorization") String authorization, @PathVariable Long id);
	
	@GetMapping("/user/email/{email}")
    public UserDetailsResponse getUserByEmail(@PathVariable String email);

}
