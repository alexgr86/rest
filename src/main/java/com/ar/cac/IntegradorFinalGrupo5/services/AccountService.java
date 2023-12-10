package com.ar.cac.IntegradorFinalGrupo5.services;

import com.ar.cac.IntegradorFinalGrupo5.entities.Account;
import com.ar.cac.IntegradorFinalGrupo5.entities.User;
import com.ar.cac.IntegradorFinalGrupo5.entities.dtos.AccountDto;
import com.ar.cac.IntegradorFinalGrupo5.exceptions.AccountFailValidationExeption;
import com.ar.cac.IntegradorFinalGrupo5.exceptions.AccountNotFoundExeption;
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

        // Busco ultimo id creado
        Long accountId = accountRepository.getLastIdCreated().getId();

        // Genero alias inicial
        account.setAlias(createAlias(accountId + 1));

        // Genero cuenta inicial
        account.setCbu(createCbu(accountId + 1));

        if(user != null){

            Account accountSaved = accountRepository.save(account);
            accountSaved.setOwner(user);

            return AccountMapper.accountToDto(accountSaved);
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

    public AccountDto updateAccount(Long id, AccountDto accountDto) {

        Account accountToModify = accountRepository
                .findById(id)
                .orElseThrow( ()->
                        new AccountNotFoundExeption(HttpStatus.NOT_FOUND, "Account not found id: " + id));


        if (accountDto.getType() == null &&
                accountDto.getCbu() == null &&
                accountDto.getAlias() == null &&
                accountDto.getAmount() == null) {
            throw new AccountFailValidationExeption(HttpStatus.BAD_REQUEST, "At least one parameter is required");
        }
        else{
            accountToModify.setAlias(accountDto.getAlias());
        }

        // Logica del Patch
        if (accountDto.getAlias() != null) {
            accountToModify.setAlias(accountDto.getAlias());
        }

        if (accountDto.getType() != null) {
            accountToModify.setType(accountDto.getType());
        }

        if (accountDto.getCbu() != null) {
            accountToModify.setCbu(accountDto.getCbu());
        }

        if (accountDto.getAmount() != null) {
            accountToModify.setAmount(accountDto.getAmount());
        }

        Account accountModified = accountRepository.save(accountToModify);
        return AccountMapper.accountToDto(accountModified);

    }

    // FUNCIONES INTERNAS
    private boolean accountExist(Account account){

        if(accountRepository.findByCbu(account.getCbu()) != null){
            return true;
        }else{
            return false;
        }

    }

    private String createAlias(Long id){

        return "alias.created.automated." + id;
    }

    private String createCbu(Long id){

        return "0000-001-0000-00" + id;
    }

}
