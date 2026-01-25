package com.sigfe.backend.service;

import com.sigfe.backend.dto.user.*;
import com.sigfe.backend.model.User;
import com.sigfe.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Criar usuário
    public UserResponseDTO create(UserCreateDTO dto) {

        if (userRepository.existsByUsername(dto.username())) {
            throw new RuntimeException("Username já está em uso");
        }

        User user = new User(
                dto.name(),
                dto.username(),
                passwordEncoder.encode(dto.password())
        );

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getUsername()
        );
    }

    // Listar todos os usuários
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getUsername()
                ))
                .toList(); // Java 16+
    }
}
