package EdTech.Course.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import EdTech.Course.dto.UserDetailsResponse;
import EdTech.Course.feign.UserService;

@Service
public class RemoteUserDetailsService implements UserDetailsService {

//    @Autowired
//    private RestTemplate restTemplate;
    
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            UserDetailsResponse user = userService.getUserByEmail(username);
//          restTemplate.getForEntity(url, UserDetailsResponse.class);
            if (user != null) {
            	return new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(), // Optional: Ensure password is encoded
                        Arrays.asList(new SimpleGrantedAuthority(user.getRole()))
                );
            }
        } catch (HttpClientErrorException e) {
            throw new UsernameNotFoundException("User not found in remote service: " + username);
        }

        throw new UsernameNotFoundException("User not found");
    }
}
