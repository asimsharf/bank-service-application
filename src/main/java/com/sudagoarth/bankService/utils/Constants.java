package com.sudagoarth.bankService.utils;

import com.sudagoarth.bankService.models.Client;
import com.sudagoarth.bankService.models.Passport;
import com.sudagoarth.bankService.models.Wallet;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

public class Constants {
    public static final String MAIN_DEPOSIT_CREDIT_PROCESS = "MainDepositCreditProcess";
    public static final Wallet DZMITRIY_WALLET = Wallet.builder().moneyCount(BigDecimal.valueOf(100.20)).build();
    public static final Passport DZMITRIY_PASSPORT = Passport.builder().series("AB").identicalNumber("123456").name("Dmitriy").surname("Suda").address("Minsk, Belarus").birthDate(LocalDate.of(1980, Month.JANUARY, 1)).validFrom(LocalDate.of(2000, Month.JANUARY, 1)).validTo(LocalDate.of(2030, Month.JANUARY, 1)).build();
    public static final Client DZMITRIY = Client.builder().id("1").name("Dmitriy").surname("Suda").address("Minsk, Belarus").phone("+375291234567").birthday(LocalDate.of(1980, Month.JANUARY, 1)).wallet(DZMITRIY_WALLET).passport(DZMITRIY_PASSPORT).build();
}
