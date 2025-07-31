package com.sudagoarth.bankService.utils;

import com.sudagoarth.bankService.models.Client;
import com.sudagoarth.bankService.models.Deposit;
import com.sudagoarth.bankService.models.Passport;
import com.sudagoarth.bankService.models.Wallet;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class EntityToMapConverter {

    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    // ==================== CLIENT → MAP ====================

    public static Map<String, Object> convertClientToMap(Client client) {
        Map<String, Object> map = new HashMap<>();

        map.put("clientId", client.getClientId());
        map.put("id", client.getId());
        map.put("name", client.getName());
        map.put("surname", client.getSurname());
        map.put("address", client.getAddress());
        map.put("phone", client.getPhone());
        map.put("birthday", formatDate(client.getBirthday()));

        if (client.getWallet() != null) {
            map.put("wallet", convertWalletToMap(client.getWallet()));
        }

        if (client.getPassport() != null) {
            map.put("passport", convertPassportToMap(client.getPassport()));
        }

        return map;
    }

    private static Map<String, Object> convertWalletToMap(Wallet wallet) {
        Map<String, Object> map = new HashMap<>();
        map.put("walletId", wallet.getWalletId());
        map.put("moneyCount", wallet.getMoneyCount());
        return map;
    }

    private static Map<String, Object> convertPassportToMap(Passport passport) {
        Map<String, Object> map = new HashMap<>();
        map.put("passportId", passport.getPassportId());
        map.put("series", passport.getSeries());
        map.put("identicalNumber", passport.getIdenticalNumber());
        map.put("name", passport.getName());
        map.put("surname", passport.getSurname());
        map.put("address", passport.getAddress());
        map.put("birthDate", formatDate(passport.getBirthDate()));
        map.put("validFrom", formatDate(passport.getValidFrom()));
        map.put("validTo", formatDate(passport.getValidTo()));
        return map;
    }

    private static String formatDate(LocalDate date) {
        return (date != null) ? ISO_DATE.format(date) : null;
    }

    // ==================== MAP → CLIENT ====================

    @SuppressWarnings("unchecked")
    public static Client mapToClient(Map<String, Object> map) {
        Client.ClientBuilder builder = Client.builder()
                .clientId(map.containsKey("clientId") ? toLong(map.get("clientId")) : null)
                .id((String) map.get("id"))
                .name((String) map.get("name"))
                .surname((String) map.get("surname"))
                .address((String) map.get("address"))
                .phone((String) map.get("phone"))
                .birthday(parseDate((String) map.get("birthday")));

        // Wallet
        Map<String, Object> walletMap = (Map<String, Object>) map.get("wallet");
        if (walletMap != null) {
            builder.wallet(
                    Wallet.builder()
                            .walletId(walletMap.containsKey("walletId") ? toLong(walletMap.get("walletId")) : null)
                            .moneyCount(new BigDecimal(walletMap.get("moneyCount").toString()))
                            .build()
            );
        }

        // Passport
        Map<String, Object> passportMap = (Map<String, Object>) map.get("passport");
        if (passportMap != null) {
            builder.passport(
                    Passport.builder()
                            .passportId(passportMap.containsKey("passportId") ? toLong(passportMap.get("passportId")) : null)
                            .series((String) passportMap.get("series"))
                            .identicalNumber((String) passportMap.get("identicalNumber"))
                            .name((String) passportMap.get("name"))
                            .surname((String) passportMap.get("surname"))
                            .address((String) passportMap.get("address"))
                            .birthDate(parseDate((String) passportMap.get("birthDate")))
                            .validFrom(parseDate((String) passportMap.get("validFrom")))
                            .validTo(parseDate((String) passportMap.get("validTo")))
                            .build()
            );
        }

        return builder.build();
    }

    private static LocalDate parseDate(String date) {
        return (date != null && !date.isBlank()) ? LocalDate.parse(date, ISO_DATE) : null;
    }

    private static Long toLong(Object value) {
        if (value == null) return null;
        if (value instanceof Number) return ((Number) value).longValue();
        return Long.parseLong(value.toString());
    }

    // ==================== DEPOSIT → MAP ====================

    public static Map<String, Object> convertDepositToMap(Deposit deposit) {
        Map<String, Object> map = new HashMap<>();

        map.put("name", deposit.getName());
        map.put("minimalSum", deposit.getMinimalSum());
        map.put("currentSum", deposit.getCurrentSum());
        map.put("openDate", formatDate(deposit.getOpenDate().toLocalDate()));
        map.put("closeDate", formatDate(deposit.getCloseDate().toLocalDate()));
        map.put("percentage", deposit.getPercentage());
        map.put("isCapitalized", deposit.getIsCapitalized());
        map.put("currency", deposit.getCurrency());
        map.put("termInMonth", deposit.getTermInMonth());

        return map;
    }
}
