package EdTech.Course.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import EdTech.Course.dto.Payment;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentService {
	
	@PostMapping("/payment")
    public Object createPayment(@RequestBody Payment payment);

}
