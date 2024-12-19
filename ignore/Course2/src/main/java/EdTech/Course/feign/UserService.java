package EdTech.Course.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import EdTech.Course.config.FeignConfig;

@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserService {

	@GetMapping("/user/{id}")
	public Object getUserById(@RequestHeader("Authorization") String authorization, @PathVariable Long id);

}