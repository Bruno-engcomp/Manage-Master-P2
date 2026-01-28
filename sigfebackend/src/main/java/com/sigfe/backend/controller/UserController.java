package com.sigfe.backend.controller;

import com.sigfe.backend.dto.user.*;
import com.sigfe.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin (origins = "*") // Permite que o front acesse o back
public class UserController {

    @Autowired
    private UserService userService;

    // POST /users
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO create(@RequestBody UserCreateDTO dto) {
        return userService.create(dto);
    }

    // GET /users
    @GetMapping
    public List<UserResponseDTO> findAll() {
        return userService.findAll();
    }
}
