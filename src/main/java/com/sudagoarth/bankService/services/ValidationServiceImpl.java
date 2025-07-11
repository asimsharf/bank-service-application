package com.sudagoarth.bankService.services;

import com.sudagoarth.bankService.models.Client;
import com.sudagoarth.bankService.utils.AppLogger;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

import static com.sudagoarth.bankService.utils.Constants.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ValidationServiceImpl implements ValidationService {

    AppLogger logger;

    @Override
    public boolean isClientWantedByPolice(Client client) {
        boolean result = policeWantedClients.stream().anyMatch(c -> c.getPassport().getIdenticalNumber().equals(client.getPassport().getIdenticalNumber()));

        if (result) {
            logger.info(getClass(), "Client with passport {} is wanted by police", client.getPassport().getIdenticalNumber());
        }
        return result;
    }

    @Override
    public boolean isClientBlacklisted(Client client) {
        boolean result = blacklistedClients.stream().anyMatch(c -> c.getPassport().getIdenticalNumber().equals(client.getPassport().getIdenticalNumber()));

        if (result) {
            logger.info(getClass(), "Client with passport {} is blacklisted", client.getPassport().getIdenticalNumber());
        }
        return result;
    }

    @Override
    public boolean isValidPassport(Client client) {
        if (client.getPassport() == null || client.getPassport().getIdenticalNumber() == null) {
            logger.info(getClass(), "Client has no valid passport data.");
            return false;
        }

        if (client.getPassport().getValidTo().isBefore(LocalDate.now())) {
            logger.info(getClass(), "Client with passport {} has an expired passport", client.getPassport().getIdenticalNumber());
            return false;
        }

        logger.info(getClass(), "Client with passport {} has a valid passport", client.getPassport().getIdenticalNumber());
        return true;
    }
}
