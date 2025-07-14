package com.sudagoarth.bankService.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassportDTO implements Serializable {

    @JsonProperty("series")
    String series;

    @JsonProperty("identicalNumber")
    String identicalNumber;

    @JsonProperty("name")
    String name;

    @JsonProperty("surname")
    String surname;

    @JsonProperty("address")
    String address;

    @JsonProperty("birthDate")
    LocalDate birthDate;

    @JsonProperty("validFrom")
    LocalDate validFrom;

    @JsonProperty("validTo")
    LocalDate validTo;
}