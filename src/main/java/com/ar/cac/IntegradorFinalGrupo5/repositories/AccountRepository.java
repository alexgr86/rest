package com.ar.cac.IntegradorFinalGrupo5.repositories;

import com.ar.cac.IntegradorFinalGrupo5.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account,Long> {

    Account findByCbu(String cbu);


    @Query(value = "SELECT * FROM cuentas c ORDER BY c.id DESC LIMIT 1;", nativeQuery = true)
    Account getLastIdCreated();
}
