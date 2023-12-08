package com.ar.cac.IntegradorFinalGrupo5.mappers;

import com.ar.cac.IntegradorFinalGrupo5.entities.Account;
import com.ar.cac.IntegradorFinalGrupo5.entities.dtos.AccountDto;

public class AccountMapper {

    public static Account dtoToAccount(AccountDto accountDto){

        Account account = new Account();
        account.setId(accountDto.getId());
        account.setType(accountDto.getType());
        account.setCbu(accountDto.getCbu());
        account.setAlias(accountDto.getAlias());
        account.setAmount(accountDto.getAmount());
        account.setOwner(accountDto.getOwner());

        return account;
    }

    public static AccountDto accountToDto(Account account){

        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setType(account.getType());
        accountDto.setCbu(account.getCbu());
        accountDto.setAlias(account.getAlias());
        accountDto.setAmount(account.getAmount());
        accountDto.setOwner(account.getOwner());

        return accountDto;
    }



}
