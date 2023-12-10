package com.ar.cac.IntegradorFinalGrupo5.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {

    private Long id;
    private Long origin;
    private Long target;
    private Date date;
    private BigDecimal amount;

}
