package com.ar.cac.IntegradorFinalGrupo5.services;

import com.ar.cac.IntegradorFinalGrupo5.entities.User;
import com.ar.cac.IntegradorFinalGrupo5.entities.dtos.UserDto;
import com.ar.cac.IntegradorFinalGrupo5.mappers.UserMapper;
import com.ar.cac.IntegradorFinalGrupo5.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;

    // Constructor Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Methods


    public List<UserDto> getUsers(){

        return userRepository.findAll().stream()
                .map(UserMapper::userToDto)
                .collect(Collectors.toList());

    }

    public UserDto getUserById(Long id){
        User user = userRepository.findById(id).get();
        return UserMapper.userToDto(user);
    }


    public UserDto createUser(UserDto userDto){

        User user = UserMapper.dtoTouser(userDto);

        // VERIFICACION DE DATOS DUPLICADOS
        if( !existsEmail(user) ){
            User entitySaved = userRepository.save(user);
            userDto = UserMapper.userToDto(entitySaved);
            return userDto;

        }

        // Devuelvo error en campo con error
        user.setEmail("ERROR: Mail existente");
        user.setDni("");
        user.setAddress("");
        user.setUsername("");
        user.setPassword("");

        return UserMapper.userToDto(user);

    }

    public String deleteUser(Long id){
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            return "El usuario con id: " + id + " ha sido eliminado";
        } else {
            return "El usuario con id: " + id + ", no ha sido eliminado";
        }
    }

    public UserDto updateUser(Long id, UserDto userDto) {

        // Mapeo de usedto a user
        User user = UserMapper.dtoTouser(userDto);

        if (userRepository.existsById(id)){
            User userToModify = userRepository.findById(id).get();

            // VALIDACIONES
            // Username
            if (userDto.getUsername() != null){
                userToModify.setUsername(user.getUsername());
            }

            // Mail
            if (user.getEmail() != null){
                userToModify.setEmail(user.getEmail());
            }

            // Password
            if (user.getPassword() != null){
                userToModify.setPassword(user.getPassword());
            }

            // Dni
            if (user.getDni() != null){
                userToModify.setDni(user.getDni());
            }

            // Address
            if (user.getAddress() != null){
                userToModify.setAddress(user.getAddress());
            }

            // Birthday
            if (userDto.getBirthday_date() != null){
                userToModify.setBirthday_date(user.getBirthday_date());
            }

            // VERIFICACION DE DATOS DUPLICADOS
            if( !existsEmail(user) ){
                User userModified = userRepository.save(userToModify);
                return UserMapper.userToDto(userModified);
            }

        }

        return null;
    }





    public boolean existsEmail(User user){

        if( userRepository.findByEmail(user.getEmail()) != null ){
            return true;
        }

        return false;
    }

}
