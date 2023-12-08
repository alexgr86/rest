package com.ar.cac.IntegradorFinalGrupo5.services;

import com.ar.cac.IntegradorFinalGrupo5.entities.Account;
import com.ar.cac.IntegradorFinalGrupo5.entities.User;
import com.ar.cac.IntegradorFinalGrupo5.entities.dtos.AccountDto;
import com.ar.cac.IntegradorFinalGrupo5.exceptions.UserNotExistsException;
import com.ar.cac.IntegradorFinalGrupo5.mappers.AccountMapper;
import com.ar.cac.IntegradorFinalGrupo5.repositories.AccountRepository;
import com.ar.cac.IntegradorFinalGrupo5.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    AccountRepository accountRepository;
    UserRepository userRepository;

    private AccountService(AccountRepository accountRepository, UserRepository userRepository){

        this.accountRepository = accountRepository;
        this.userRepository = userRepository;

    }

    public List<AccountDto> getAccounts(){

        return accountRepository.findAll().stream()
                .map(AccountMapper::accountToDto)
                .collect(Collectors.toList());
    }

    public AccountDto getAccountById(Long id){
        return AccountMapper
                .accountToDto(accountRepository.getReferenceById(id));
    }

    public AccountDto createAccount(AccountDto accountDto){

        Account account = AccountMapper.dtoToAccount(accountDto);

        // Validacion de usuario propietario de la cuenta
        User user = userRepository
                .findById(account.getOwner().getId())
                .orElseThrow(()-> new UserNotExistsException
                        (HttpStatus.NOT_FOUND,
                                "Account " + account.getOwner().getId() + " not found"));



        if(user != null){
            accountRepository.save(account);
            return accountDto;
        }else{
            return null;
        }

    }

    public AccountDto deleteAccount(Long id){

        Account account = accountRepository.findById(id).get();
        if(account != null){
            return AccountMapper.accountToDto(account);
        }
        else{
            return null;
        }
    }





    // FUNCIONES INTERNAS
    private boolean accountExist(Account account){

        if(accountRepository.findByCbu(account.getCbu()) != null){
            return true;
        }else{
            return false;
        }

    }


}
