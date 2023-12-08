package com.ar.cac.IntegradorFinalGrupo5.mappers;

import com.ar.cac.IntegradorFinalGrupo5.entities.User;
import com.ar.cac.IntegradorFinalGrupo5.entities.dtos.UserDto;
import java.time.LocalDateTime;

public class UserMapper {

    public static User dtoTouser(UserDto dto){
        User userTemp = new User();
        userTemp.setUsername(dto.getUsername());
        userTemp.setPassword(dto.getPassword());
        userTemp.setAddress(dto.getAddress());
        userTemp.setDni(dto.getDni());
        userTemp.setBirthday_date(dto.getBirthday_date());
        userTemp.setEmail(dto.getEmail());
        userTemp.setCreated_at(LocalDateTime.now());
        userTemp.setUpdated_at(LocalDateTime.now());
        return userTemp;
    }

    public static UserDto userToDto(User user){
        UserDto dtoTemp = new UserDto();
        dtoTemp.setUsername(user.getUsername());

        if(user.getPassword().length() > 0 ){
            dtoTemp.setPassword("**********");
        }else{
            dtoTemp.setPassword("");
        }

        dtoTemp.setId(user.getId());
        dtoTemp.setAddress(user.getAddress());
        dtoTemp.setEmail(user.getEmail());
        dtoTemp.setBirthday_date(user.getBirthday_date());
        dtoTemp.setDni(user.getDni());
        dtoTemp.setCreated_at(user.getCreated_at());
        dtoTemp.setUpdated_at(user.getUpdated_at());
        return dtoTemp;
    }


}
