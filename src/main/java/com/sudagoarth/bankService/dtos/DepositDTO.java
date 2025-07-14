package com.sudagoarth.bankService.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepositDTO implements Serializable {

    @JsonProperty("name")
    String name;

    @JsonProperty("minimalSum")
    BigDecimal minimalSum;

    @JsonProperty("currentSum")
    BigDecimal currentSum;

    @JsonProperty("openDate")
    OffsetDateTime openDate;

    @JsonProperty("closeDate")
    OffsetDateTime closeDate;

    @JsonProperty("percentage")
    Double percentage;

    @JsonProperty("isCapitalized")
    Boolean isCapitalized;

    @JsonProperty("currency")
    String currency;

    @JsonProperty("termInMonth")
    Integer termInMonth;
}