package ru.nsu.backendmodule.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.backendmodule.model.User;
import ru.nsu.backendmodule.repository.UserRepository;
import ru.nsu.backendshared.model.UserAuthenticationToken;
import ru.nsu.backendshared.model.UserAuthorities;

import java.util.ArrayList;

public class UserAuthService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserAuthService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.passwordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserAuthenticationToken authenticate(String username, String password) throws UsernameNotFoundException, BadCredentialsException {
        var user = userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exists"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Password does not match");
        }

        return createAuthToken(user, true);
    }

    @Transactional(readOnly = true)
    public UserAuthenticationToken authenticate(String uuid) throws BadCredentialsException{
        var user = userRepository.findById(uuid)
                .orElseThrow(() -> new BadCredentialsException("User uuid does not exists"));

        return createAuthToken(user, !user.isRegistered());
    }

    private UserAuthenticationToken createAuthToken(User user, boolean authenticated) {
        var authorities = new ArrayList<String>();

        if (authenticated) {
            authorities.add(UserAuthorities.AUTHENTICATED);
        }

        if (user.isRegistered()) {
            authorities.add(UserAuthorities.REGISTERED);
        }

        if (user.isVerified()) {
            authorities.add(UserAuthorities.VERIFIED);
        }

        return new UserAuthenticationToken(user.getId(), authorities);
    }
}
