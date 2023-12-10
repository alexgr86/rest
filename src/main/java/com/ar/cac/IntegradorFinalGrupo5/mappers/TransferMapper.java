package com.ar.cac.IntegradorFinalGrupo5.mappers;

import com.ar.cac.IntegradorFinalGrupo5.entities.Transfer;
import com.ar.cac.IntegradorFinalGrupo5.entities.dtos.TransferDto;

public class TransferMapper {

    public static Transfer dtoToTransfer(TransferDto transferDto){

        Transfer transfer = new Transfer();

        transferDto.setId(transfer.getId());
        transferDto.setOrigin(transfer.getOrigin());
        transferDto.setTarget(transfer.getTarget());
        transferDto.setDate(transfer.getDate());
        transferDto.setAmount(transfer.getAmount());

        return transfer;
    }

    public static TransferDto transferToDto(Transfer transfer){

        TransferDto transferDto = new TransferDto();

        transferDto.setId(transfer.getId());
        transferDto.setOrigin(transfer.getOrigin());
        transferDto.setTarget(transfer.getTarget());
        transferDto.setDate(transfer.getDate());
        transferDto.setAmount(transfer.getAmount());

        return transferDto;
    }

}
