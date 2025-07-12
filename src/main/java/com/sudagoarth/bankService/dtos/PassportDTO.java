package com.sudagoarth.bankService.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassportDTO implements Serializable {
    String series;
    String identicalNumber;
    String name;
    String surname;
    String address;
    LocalDate birthDate;
    LocalDate validFrom;
    LocalDate validTo;
}