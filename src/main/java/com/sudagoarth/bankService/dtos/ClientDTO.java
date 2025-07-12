package com.sudagoarth.bankService.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO implements Serializable {
    String id;
    String name;
    String surname;
    String address;
    String phone;
    LocalDate birthday;
    WalletDTO wallet;
    PassportDTO passport;
}