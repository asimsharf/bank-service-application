package com.sudagoarth.bankService.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepositDTO implements Serializable {
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
