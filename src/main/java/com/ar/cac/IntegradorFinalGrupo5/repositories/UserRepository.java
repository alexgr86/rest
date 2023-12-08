package com.ar.cac.IntegradorFinalGrupo5.repositories;

import com.ar.cac.IntegradorFinalGrupo5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    //Busqueda por email
    User findByEmail(String email);

}
