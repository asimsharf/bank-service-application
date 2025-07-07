package com.sudagoarth.bankService.utils;

import com.sudagoarth.bankService.models.Client;
import com.sudagoarth.bankService.models.Deposit;
import com.sudagoarth.bankService.models.Passport;
import com.sudagoarth.bankService.models.Wallet;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String MAIN_DEPOSIT_CREDIT_PROCESS_ID = "MainDepositCreditProcessID";

    public static final Wallet asimWallet = Wallet.builder()
            .moneyCount(BigDecimal.valueOf(100.20))
            .build();

    public static final Passport asimPassport = Passport.builder()
            .series("AB")
            .identicalNumber("123456")
            .name("Dmitriy").surname("Suda")
            .address("Minsk, Belarus")
//            .birthDate(LocalDate.of(1990, Month.JANUARY, 1))
//            .validFrom(LocalDate.of(2000, Month.JANUARY, 1))
//            .validTo(LocalDate.of(2030, Month.JANUARY, 1))
            .build();

    public static final Client asim = Client.builder()
            .id("1")
            .name("Dmitriy")
            .surname("Suda")
            .address("Minsk, Belarus")
            .phone("+375291234567")
//            .birthday(LocalDate.of(1988, Month.DECEMBER, 3))
            .wallet(asimWallet)
            .passport(asimPassport)
            .build();


    public static final Wallet esamWallet = Wallet.builder()
            .moneyCount(BigDecimal.valueOf(100.20))
            .build();

    public static final Client esam = Client.builder()
            .id("2")
            .name("Tikhon")
            .surname("Suda")
            .address("Minsk, Belarus")
            .phone("+375291234567")
//            .birthday(LocalDate.of(1990, Month.JANUARY, 1))
            .wallet(esamWallet)
            .passport(null)
            .build();

    public static final String SUDDEN_OPERATION_INTERRUPTION_ERROR = "SUDDEN_OPERATION_INTERRUPTION_ERROR";


    public static final Deposit earlySpring = Deposit.builder()
            .name("Early-Spring")
            .minimalSum(BigDecimal.valueOf(1000))
            .currentSum(BigDecimal.valueOf(1000))
//            .openDate(LocalDate.of(2023, Month.JANUARY, 1).atStartOfDay())
//            .closeDate(LocalDate.of(2024, Month.JANUARY, 1).atStartOfDay())
            .percentage(5.0)
            .isCapitalized(true)
            .currency("USD")
            .termInMonth(12)
            .build();

    public static final Deposit hotSummer = Deposit.builder()
            .name("Hot-Summer")
            .minimalSum(BigDecimal.valueOf(500))
            .currentSum(BigDecimal.valueOf(500))
//            .openDate(LocalDate.of(2023, Month.JUNE, 1).atStartOfDay())
//            .closeDate(LocalDate.of(2024, Month.JUNE, 1).atStartOfDay())
            .percentage(3.5)
            .isCapitalized(true)
            .currency("USD")
            .termInMonth(6)
            .build();

    public static final Deposit colorfulAutumn = Deposit.builder()
            .name("Colorful-Autumn")
            .minimalSum(BigDecimal.valueOf(2000))
            .currentSum(BigDecimal.valueOf(2000))
//            .openDate(LocalDate.of(2023, Month.SEPTEMBER, 1).atStartOfDay())
//            .closeDate(LocalDate.of(2024, Month.SEPTEMBER, 1).atStartOfDay())
            .percentage(4.0)
            .isCapitalized(false)
            .currency("USD")
            .termInMonth(12)
            .build();

    public static final List<Deposit> bankDeposits = List.of(
            earlySpring,
            hotSummer,
            colorfulAutumn
    );
}
