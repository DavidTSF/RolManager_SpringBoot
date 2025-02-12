package dev.davidvega.rolmanager.services;


import dev.davidvega.rolmanager.dtos.AuthDTO;
import dev.davidvega.rolmanager.dtos.LoginDTO;
import dev.davidvega.rolmanager.dtos.RegisterDTO;
import dev.davidvega.rolmanager.models.User;
import dev.davidvega.rolmanager.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthDTO login(LoginDTO login) throws Exception {
        try {
            authenticate(login.getUsername(), login.getPassword());

            User user = userRepository.findByUsername(login.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String token = jwtUtil.generateToken(user);
            return new AuthDTO(token);
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            System.out.println(e.getMessage());
            throw new BadCredentialsException("Incorrect username or password");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public AuthDTO register(RegisterDTO register) throws Exception {
        try {
            User user = createUserFromRegistration(register);
            User existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);
            if (existingUser != null) {
                throw new Exception("Username already exists");
            }

            user = userRepository.save(user);

            String token = jwtUtil.generateToken(user);
            return new AuthDTO(token);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }


    private User createUserFromRegistration(RegisterDTO register) {
        User user = new User();
        user.setUsername(register.getUsername());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setRole(User.UserRole.valueOf(register.getRole()));

        return user;
    }
}