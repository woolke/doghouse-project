package pl.wolke.doghouse.core.security.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import pl.wolke.doghouse.modules.auth.RoleRepository;
import pl.wolke.doghouse.modules.auth.UserRepository;
import pl.wolke.doghouse.modules.auth.models.ERole;
import pl.wolke.doghouse.modules.auth.models.Role;
import pl.wolke.doghouse.modules.auth.models.User;
import pl.wolke.doghouse.modules.commons.exception.ApiError;
import pl.wolke.doghouse.modules.commons.exception.ContentNotAllowedException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public User getCurrentUser() {
        UserDetailsImpl principal;
        try {
            principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            principal = new pl.wolke.doghouse.core.security.services.UserDetailsImpl(null, "guest", null, null, null);
        }
        return userRepository.findByUsername(principal.getUsername()).orElse(null);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
    }

    @Override
    public List<String> getRoles() {
        return roleRepository.findAll().stream()
                .map(Role::getName)
                .map(ERole::name)
                .toList();
    }

    @Override
    public void remove(Long userId) throws ContentNotAllowedException {
        User user = getUser(userId);
        if (user.getRoles().stream().map(Role::getName).toList().contains(ERole.ROLE_ADMIN)) {
            ObjectError error = new ObjectError("NOT_ALLOWED", "Can't remove ADMIN");
            throw ContentNotAllowedException.createWith(Arrays.asList(error));
        }
        userRepository.deleteById(userId);

    }
}
