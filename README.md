# Microservices-Eureka-Hystrix-Feign-Client-or-RestTemplate
In microservices, Eureka enables dynamic service discovery by allowing services to register and locate each other without hardcoding URLs.

# Microservices Architecture with Eureka, Hystrix, Feign Client, and RestTemplate

This project demonstrates a **Microservices** architecture using **Eureka**, **Hystrix**, **Feign Client**, and **RestTemplate** to build scalable, resilient, and easily communicable services.

## Technologies Used:
- **Eureka**: Service Discovery tool for dynamic service registration and discovery.
- **Feign Client**: Declarative HTTP client for simplified inter-service communication.
- **Hystrix**: Fault tolerance through circuit-breaking and fallback mechanisms.
- **RestTemplate**: Low-level, synchronous HTTP client for custom HTTP requests.

## How They Work Together

### **Eureka** - Service Discovery
Eureka acts as a **Service Registry** where microservices register themselves. Other services can find and communicate with registered services dynamically. 

```java
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}




Feign Client - Simplified HTTP Communication
Feign allows you to define HTTP requests declaratively, removing the need for boilerplate code like RestTemplate. It integrates with Eureka for automatic service discovery.

java
Copy code
@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/users/{id}")
    User getUserById(@PathVariable("id") Long id);
}
Hystrix - Fault Tolerance and Circuit Breaking
Hystrix protects your microservices by providing circuit-breaking, fallback mechanisms, and graceful degradation. This ensures that the system remains operational even if one service fails.

java
Copy code
@HystrixCommand(fallbackMethod = "defaultUser")
public User getUser(Long id) {
    return restTemplate.getForObject("http://user-service/users/{id}", User.class, id);
}

public User defaultUser(Long id) {
    return new User(id, "Fallback User");
}
RestTemplate - Low-Level HTTP Requests
RestTemplate is used when you need more control over HTTP requests, including custom headers or more specific configurations.

java
Copy code
RestTemplate restTemplate = new RestTemplate();
User user = restTemplate.getForObject("http://user-service/users/{id}", User.class, id);
Benefits
Scalability: Easily scale services independently as demand grows.
Fault Tolerance: Ensure resilience with Hystrix circuit-breakers and fallback methods.
Simplified Communication: Use Feign Client to reduce boilerplate HTTP request code.
Resilience: Handle failures gracefully and keep services operational even during issues.
Project Setup
Running the Application
Clone this repository:
Copy code
git clone https://github.com/yourusername/microservices-example.git

Run All the Services one by one and then
Navigate into the project directory:

The services will be available at:
Eureka Server: http://localhost:8761
User Service: http://localhost:8083
Payment Service: http://localhost:8085
Course Application: http://localhost:8082


---

### Key Features:
- **Eureka**: Helps with dynamic service discovery.
- **Feign Client**: Reduces boilerplate code in service-to-service communication.
- **Hystrix**: Adds resilience with fault-tolerant mechanisms.
- **RestTemplate**: Provides control over custom HTTP requests.

This `README.md` includes everything from the architecture description, technology used, code snippets, setup instructions, and licensing information in a professional format. You can adjust the project link and any specific configurations as per your project.
![Screenshot 2024-12-20 013434](https://github.com/user-attachments/assets/c9b867ba-8675-4010-be7e-21fcecec0654)
![Screenshot 2024-12-20 013555](https://github.com/user-attachments/assets/f7d3541e-cc48-4fab-96f1-f558fd9c0614)
![Screenshot 2024-12-20 013624](https://github.com/user-attachments/assets/2e98ef02-4da2-469e-94f5-03172d9787ea)
![Screenshot 2024-12-20 013709](https://github.com/user-attachments/assets/b48cedea-21e6-4b67-a3ed-f53019f3dae4)
![Screenshot 2024-12-20 013739](https://github.com/user-attachments/assets/e7f44ac4-a565-489f-9aaa-ba307d2d700b)
![Screenshot 2024-12-20 013759](https://github.com/user-attachments/assets/8d779c91-ce02-4a0d-8dd3-6f56ac6533f0)
![Screenshot 2024-12-20 013817](https://github.com/user-attachments/assets/704d4960-98f1-48fc-aee2-fedd1a0a6989)
![Screenshot 2024-12-20 013847](https://github.com/user-attachments/assets/3f98a80d-69f0-49ef-8449-d27317484a69)
