package ru.nokkov.blps_lab.security;

import org.springframework.security.authentication.jaas.AuthorityGranter;
import ru.nokkov.blps_lab.user.User;
import ru.nokkov.blps_lab.user.UserRepository;

import java.security.Principal;
import java.util.Set;

public class UserRepositoryAuthorityGranter implements AuthorityGranter {

    private final UserRepository userRepository;

    public UserRepositoryAuthorityGranter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<String> grant(Principal principal) {
        User user = userRepository.findUserByUsername(principal.getName());
        return user.getRoles();
    }
}
