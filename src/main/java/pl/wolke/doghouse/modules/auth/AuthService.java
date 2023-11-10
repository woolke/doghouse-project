package pl.wolke.doghouse.modules.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wolke.doghouse.modules.auth.models.Role;
import pl.wolke.doghouse.modules.auth.models.User;
import pl.wolke.doghouse.modules.auth.request.LoginRequest;
import pl.wolke.doghouse.modules.auth.request.SignupRequest;
import pl.wolke.doghouse.modules.auth.response.MessageResponse;
import pl.wolke.doghouse.modules.auth.response.UserInfoResponse;

import java.util.Collection;
import java.util.List;

@Service
public interface AuthService {
    ResponseEntity<UserInfoResponse> authenticateUser(String username, String password);

    ResponseEntity<MessageResponse> registerUser(String username, String password, String email, Collection<String> roles);

    ResponseEntity<MessageResponse> logoutUser();

    void changePassword (Long id, String password);

    void setRoles(User user, List<String> roles);

}
