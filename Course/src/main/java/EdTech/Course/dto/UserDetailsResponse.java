package EdTech.Course.dto;

import lombok.Data;

@Data
public class UserDetailsResponse {
    private String username;
    private String email;
    private String password;
    private String role;
}

