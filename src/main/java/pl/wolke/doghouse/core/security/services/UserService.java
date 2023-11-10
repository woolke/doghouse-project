package pl.wolke.doghouse.core.security.services;

import org.springframework.stereotype.Service;
import pl.wolke.doghouse.modules.auth.models.Role;
import pl.wolke.doghouse.modules.auth.models.User;
import pl.wolke.doghouse.modules.commons.exception.ContentNotAllowedException;

import java.util.List;

@Service
public interface UserService {
    User getCurrentUser();

    List<User> getUsers();

    User getUser(Long id);

    void remove(Long userId) throws ContentNotAllowedException;

    List<String> getRoles();
}
