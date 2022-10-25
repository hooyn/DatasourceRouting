package hooyn.routing_datasource.controller;

import hooyn.routing_datasource.domain.User;
import hooyn.routing_datasource.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("")
    public ResponseEntity<?> insertUser(@RequestBody String username) {
        User user = User.builder().userName(username).build();
        userRepository.save(user);
        return new ResponseEntity<>("Success Insert User Data", HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> selectUser() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
