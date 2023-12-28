package pl.wolke.doghouse.modules.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.wolke.doghouse.core.security.services.UserService;
import pl.wolke.doghouse.modules.auth.AuthService;
import pl.wolke.doghouse.modules.auth.models.User;
import pl.wolke.doghouse.modules.commons.exception.ContentNotAllowedException;
import pl.wolke.doghouse.modules.user.models.ChangePasswordRequest;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/users")
class UserController {

    private final UserService userService;

    private final AuthService authService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getRoles() {
        return ResponseEntity.ok(userService.getRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) throws ContentNotAllowedException {
        userService.remove(id);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest req) {
        authService.changePassword(id, req.getPassword());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<User> setRole(@PathVariable Long id, @RequestBody List<String> roles) {
        authService.setRoles(userService.getUser(id), roles);
        return ResponseEntity.ok(userService.getUser(id));
    }



}
