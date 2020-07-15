package com.javaguru.onlineshop.user.login;

import com.javaguru.onlineshop.exceptions.NotFoundException;
import com.javaguru.onlineshop.user.UserRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserLoginService implements UserDetailsService {

    private final UserLoginRepo repo;

    public UserLoginService(UserLoginRepo repo) {
        this.repo = repo;
    }

    public UserLoginDTO findByID(Long id) {
        UserLogin found = repo.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return new UserLoginDTO(found.getId(),
                found.getUsername(),
                found.getPassword(),
                found.isActive(),
                found.getRoles());
    }

    public UserLoginDTO save(UserLoginDTO dto) {
        UserLogin login = new UserLogin();
        login.setUsername(dto.getUsername());
        login.setPassword(dto.getPassword());
        login.setActive(true);
        login.setRoles(Collections.singleton(UserRoles.USER));
        repo.save(login);
        return new UserLoginDTO(login.getId(),
                login.getUsername(),
                login.getPassword(),
                login.isActive(),
                login.getRoles());
    }

    public UserLoginDTO editUser(Long id, UserLoginDTO dto) {
        UserLogin found = repo.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        found.setUsername(dto.getUsername());
        found.setPassword(dto.getPassword());
        found.setActive(dto.isActive());
        found.setRoles(dto.getRoles());
        repo.save(found);
        return new UserLoginDTO(found.getId(),
                found.getUsername(),
                found.getPassword(),
                found.isActive(),
                found.getRoles());
    }

    public void deleteUser(Long id) {
        repo.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        repo.deleteById(id);
    }

    public UserLoginDTO findByLogin(String login) {
        UserLogin user = repo.findByUsername(login);
        if (user == null) {
            return null;
        }
        return new UserLoginDTO(user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.isActive(),
                user.getRoles());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<UserRoles> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
    }
}
