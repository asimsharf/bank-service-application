package com.sudagoarth.bankService.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Deposit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long depositId;

    String name;
    BigDecimal minimalSum;
    BigDecimal currentSum;
    OffsetDateTime openDate;
    OffsetDateTime closeDate;
    Double percentage;
    Boolean isCapitalized;
    String currency;
    Integer termInMonth;
}