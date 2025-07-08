package com.sudagoarth.bankService.services;

import com.sudagoarth.bankService.models.Client;

public interface ValidationService {
    boolean isClientWantedByPolice(Client client);

    boolean isClientBlacklisted(Client client);

    boolean isValidPassport(Client client);

}
