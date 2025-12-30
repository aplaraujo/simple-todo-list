package io.github.com.aplaraujo.services;

import io.github.com.aplaraujo.entities.Role;
import io.github.com.aplaraujo.entities.User;
import io.github.com.aplaraujo.entities.enums.RoleEnum;
import io.github.com.aplaraujo.repositories.RoleRepository;
import io.github.com.aplaraujo.repositories.UserRepository;
import io.github.com.aplaraujo.security.UserDetailsImpl;
import io.github.com.aplaraujo.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("E-mail não encontrado!"));
        return UserDetailsImpl.build(user);
    }

    public String addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        if (user.getAuthority() != null && !user.getAuthority().isEmpty()) {
            for (String auth : user.getAuthority()) {
                try {
                    RoleEnum roleEnum = RoleEnum.valueOf(auth);

                    Role role = roleRepository.findByAuthority(roleEnum)
                            .orElseGet(() -> {
                                Role newRole = new Role();
                                newRole.setAuthority(roleEnum);
                                return roleRepository.save(newRole);
                            });
                    roles.add(role);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid role: " + auth);
                }
            }
        }
        user.setRoles(roles);
        userRepository.save(user);
        return "User added successfully!";
    }
}
