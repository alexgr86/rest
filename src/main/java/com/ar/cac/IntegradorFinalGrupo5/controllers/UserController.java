package com.ar.cac.IntegradorFinalGrupo5.controllers;

import com.ar.cac.IntegradorFinalGrupo5.entities.User;
import com.ar.cac.IntegradorFinalGrupo5.entities.dtos.UserDto;
import com.ar.cac.IntegradorFinalGrupo5.repositories.UserRepository;
import com.ar.cac.IntegradorFinalGrupo5.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UserRepository userRepository;
    UserService userService;

    UserController(UserRepository userRepository, UserService userService){
        this.userRepository = userRepository;
        this.userService = userService;
    }


    // GET: Obtener listado de usuarios
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.
                status(HttpStatus.OK).
                body(userService.getUsers());
    }


    // GET: Obtener usuario por id
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }

    // POST: Crear usuario
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(userService.createUser(userDto));
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto user){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUser(id, user));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.deleteUser(id));
    }
}
