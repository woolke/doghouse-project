package pl.wolke.doghouse.modules.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wolke.doghouse.core.security.jwt.JwtUtils;
import pl.wolke.doghouse.core.security.services.UserDetailsImpl;
import pl.wolke.doghouse.modules.auth.models.ERole;
import pl.wolke.doghouse.modules.auth.models.Role;
import pl.wolke.doghouse.modules.auth.models.User;
import pl.wolke.doghouse.modules.auth.response.MessageResponse;
import pl.wolke.doghouse.modules.auth.response.UserInfoResponse;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<UserInfoResponse> authenticateUser(String username, String password) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    public ResponseEntity<MessageResponse> registerUser(String username, String password, String email, Collection<String> roles) {
        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(username, email, encoder.encode(password));

        Set<Role> rolesSet = mapToRoles(roles);

        user.setRoles(rolesSet);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<MessageResponse> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    public void changePassword(Long userId, String password) {
        userRepository.findById(userId).ifPresent(user -> changePassword(user, password));
    }


    @Override
    public void setRoles(User user, List<String> roles) {
        if (user.getRoles().stream().map(Role::getName).toList().contains(ERole.ROLE_ADMIN)) {
            throw new IllegalArgumentException("Can't change roles for ADMIN");
        }
        user.setRoles(mapToRoles(roles));
        userRepository.save(user);
    }

    private void changePassword(User user, String password) {
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }

    private Set<Role> mapToRoles(Collection<String> roles) {
        Set<Role> rolesSet = new HashSet<>();

        if (roles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            rolesSet.add(userRole);
        } else {
            roles.forEach(role -> {
                try {
                    roleRepository.findByName(ERole.valueOf(role)).ifPresent(rolesSet::add);
                } catch (Exception e) {
                }
            });
        }
        return rolesSet;
    }
}
