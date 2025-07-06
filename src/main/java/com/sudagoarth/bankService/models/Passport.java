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
public class Passport {
    String series;
    String identicalNumber;
    String name;
    String surname;
    String address;
    LocalDate birthDate;
    LocalDate validFrom;
    LocalDate validTo;
}
