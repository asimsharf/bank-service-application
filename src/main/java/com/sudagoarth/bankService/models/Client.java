package com.sudagoarth.bankService.models;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Client {

    String id;
    String name;
    String surname;
    String address;
    String phone;
    LocalDate birthday;
    Wallet wallet;
    Passport passport;
}
