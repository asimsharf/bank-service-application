package com.sudagoarth.bankService.services.deposit.delegate.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudagoarth.bankService.models.Client;
import com.sudagoarth.bankService.services.ValidationService;
import com.sudagoarth.bankService.utils.AppLogger;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("clientParticularValidationDelegate")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientParticularValidationDelegate implements JavaDelegate {

    ValidationService validationService;
    AppLogger logger;
    ObjectMapper objectMapper;

    @Override
    public void execute(DelegateExecution execution) {
        logger.info(getClass(), "Starting client criminal check", execution.getVariables());

        Object rawClient = execution.getVariable("client");
        if (rawClient == null) {
            throw new IllegalStateException("Client variable is missing");
        }

        Client client = objectMapper.convertValue(rawClient, Client.class);
        boolean isCriminal = validationService.isClientWantedByPolice(client);

        execution.setVariable("isCriminal", isCriminal);
        execution.setVariable("isValidUser", !isCriminal);

        logger.info(getClass(), "Client validation result: isCriminal={}, isValidUser={}", isCriminal, !isCriminal);
    }
}
