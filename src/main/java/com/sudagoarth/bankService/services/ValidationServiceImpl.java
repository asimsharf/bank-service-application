package com.sudagoarth.bankService.services;

import com.sudagoarth.bankService.models.Client;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;

import java.time.LocalDate;
import java.util.Objects;

import static com.sudagoarth.bankService.utils.Constants.*;

@Slf4j
@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public boolean isClientWantedByPolice(Client client) {

        policeWantedClients.stream()
                .filter(policeClient -> policeClient.getPassport().getIdenticalNumber().equals(client.getPassport().getIdenticalNumber()))
                .findFirst()
                .ifPresent(policeClient -> log.info("Client with passport {} is wanted by police", client.getPassport().getIdenticalNumber()));
        return policeWantedClients.stream()
                .anyMatch(policeClient -> policeClient.getPassport().getIdenticalNumber().equals(client.getPassport().getIdenticalNumber()));
    }

    @Override
    public boolean isClientBlacklisted(Client client) {
        blacklistedClients.stream()
                .filter(blacklistedClient -> blacklistedClient.getPassport().getIdenticalNumber().equals(client.getPassport().getIdenticalNumber()))
                .findFirst()
                .ifPresent(blacklistedClient -> log.info("Client with passport {} is blacklisted", client.getPassport().getIdenticalNumber()));
        return blacklistedClients.stream()
                .anyMatch(blacklistedClient -> blacklistedClient.getPassport().getIdenticalNumber().equals(client.getPassport().getIdenticalNumber()));
    }

    @Override
    public boolean isValidPassport(Client client) {
        if (client.getPassport() != null && client.getPassport().getIdenticalNumber() != null) {
            log.info("Client with passport {} has a valid passport", client.getPassport().getIdenticalNumber());
            return true;
        }
        if (Objects.requireNonNull(client.getPassport()).getValidTo().isBefore(LocalDate.now())) {
            log.info("Client with passport {} has an expired passport", client.getPassport().getIdenticalNumber());
            return false;
        }
        return false;
    }
}
