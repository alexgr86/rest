package com.ar.cac.IntegradorFinalGrupo5.controllers;

import com.ar.cac.IntegradorFinalGrupo5.entities.dtos.TransferDto;
import com.ar.cac.IntegradorFinalGrupo5.services.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService service){
        this.transferService = service;
    }

    @GetMapping
    public ResponseEntity<List<TransferDto>> getTransfers(){
        List<TransferDto> transfersDto = transferService.getTransfers();
        return ResponseEntity.status(HttpStatus.OK).body(transfersDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransferDto> getTransferById(@PathVariable Long id){
        TransferDto transfer = transferService.getTransferById(id);
        return ResponseEntity.status(HttpStatus.OK).body(transfer);
    }

    @PostMapping
    public ResponseEntity<TransferDto> performTransfer(@RequestBody TransferDto transferDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transferService.executeTransfer(transferDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TransferDto> updateTransfer(@PathVariable Long id
            , @RequestBody TransferDto transferDto)
    {

        return ResponseEntity.status(HttpStatus.OK)
                .body(transferService.updateTransfer(id, transferDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TransferDto> deleteTransfer(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(transferService.deleteTransfer(id));
    }


}
