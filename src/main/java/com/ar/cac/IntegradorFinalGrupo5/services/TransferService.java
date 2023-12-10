package com.ar.cac.IntegradorFinalGrupo5.services;

import com.ar.cac.IntegradorFinalGrupo5.entities.Account;
import com.ar.cac.IntegradorFinalGrupo5.entities.Transfer;
import com.ar.cac.IntegradorFinalGrupo5.entities.dtos.TransferDto;
import com.ar.cac.IntegradorFinalGrupo5.exceptions.AccountNotFoundExeption;
import com.ar.cac.IntegradorFinalGrupo5.exceptions.InsufficientFoundsException;
import com.ar.cac.IntegradorFinalGrupo5.exceptions.TransferNotFoundException;
import com.ar.cac.IntegradorFinalGrupo5.mappers.TransferMapper;
import com.ar.cac.IntegradorFinalGrupo5.repositories.AccountRepository;
import com.ar.cac.IntegradorFinalGrupo5.repositories.TransferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;

    public TransferService(TransferRepository transferRepository, AccountRepository accountRepository){
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }


    public List<TransferDto> getTransfers(){
        List<Transfer> transfers = transferRepository.findAll();
        return transfers.stream()
                .map(TransferMapper::transferToDto)
                .collect(Collectors.toList());
    }

    public TransferDto getTransferById(Long id){
        Transfer transfer = transferRepository.findById(id).orElseThrow(() ->
                new TransferNotFoundException(HttpStatus.NOT_FOUND, "Transfer not found with id: " + id));
        return TransferMapper.transferToDto(transfer);
    }

    public TransferDto updateTransfer(Long id, TransferDto transferDto){

        Transfer transfer = transferRepository.findById(id).orElseThrow(() ->
                new TransferNotFoundException(HttpStatus.NOT_FOUND, "Transfer not found with id: " + id));

        Transfer updatedTransfer = TransferMapper.dtoToTransfer(transferDto);
        updatedTransfer.setId(transfer.getId());

        return TransferMapper.transferToDto(transferRepository.save(updatedTransfer));

    }

    public TransferDto deleteTransfer(Long id){

        // Busqueda de transferencia a eliminar
        Transfer transfer = transferRepository.findById(id).orElseThrow( ()->
                new TransferNotFoundException(HttpStatus.NOT_FOUND, "Transfer not found with id: " + id));

        // Eliminacion de transferencia
        transferRepository.deleteById(id);

        return TransferMapper.transferToDto(transfer);
    }

    @Transactional
    public TransferDto executeTransfer(TransferDto transferDto) {

        // Comprobar si las cuentas de origen y destino existen
        Account originAccount = accountRepository.findById(transferDto.getOrigin())
                .orElseThrow(() -> new AccountNotFoundExeption(HttpStatus.NOT_FOUND, "Account not found with id: " + transferDto.getOrigin().toString()));
        Account destinationAccount = accountRepository.findById(transferDto.getTarget())
                .orElseThrow(() -> new AccountNotFoundExeption(HttpStatus.NOT_FOUND,"Account not found with id: " + transferDto.getTarget()));

        // Comprobar si la cuenta de origen tiene fondos suficientes
        if (originAccount.getAmount().compareTo(transferDto.getAmount()) < 0) {
            throw new InsufficientFoundsException(HttpStatus.CONFLICT, "Insufficient funds in the account with id: " + transferDto.getOrigin());
        }

        // Realizar la transferencia
        originAccount.setAmount(originAccount.getAmount().subtract(transferDto.getAmount()));
        destinationAccount.setAmount(destinationAccount.getAmount().add(transferDto.getAmount()));

        // Guardar las cuentas actualizadas
        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);

        // Crear la transferencia y guardarla en la base de datos
        Transfer transfer = new Transfer();
        transfer.setOrigin(originAccount.getId());
        transfer.setTarget(destinationAccount.getId());
        transfer.setAmount(transferDto.getAmount());
        transfer = transferRepository.save(transfer);

        // Devolver el DTO de la transferencia realizada
        return TransferMapper.transferToDto(transfer);
    }

}
