package com.sudagoarth.bankService.services.deposit.delegate.bank;

import com.sudagoarth.bankService.models.Client;
import com.sudagoarth.bankService.services.ValidationService;
import com.sudagoarth.bankService.utils.AppLogger;
import com.sudagoarth.bankService.utils.EntityToMapConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("clientFullValidationDelegate")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientFullValidationDelegate implements JavaDelegate {

    final ValidationService validationService;
    final AppLogger logger;

    @Override
    public void execute(DelegateExecution execution) {
        logger.info(getClass(), "Executing full client validation process", execution.getVariables());

        Object rawClient = execution.getVariable("client");
        if (!(rawClient instanceof Map)) {
            throw new IllegalStateException("Client variable must be of type Map<String, Object>");
        }

        Client client = EntityToMapConverter.mapToClient((Map<String, Object>) rawClient);

        boolean isCriminal = validationService.isClientWantedByPolice(client);
        boolean isBlacklisted = validationService.isClientBlacklisted(client);
        boolean isValidPassport = validationService.isValidPassport(client);

        execution.setVariable("isCriminal", isCriminal);
        execution.setVariable("isBlacklisted", isBlacklisted);
        execution.setVariable("isValidPassport", isValidPassport);

        boolean isValidUser = !isCriminal && !isBlacklisted && isValidPassport;
        execution.setVariable("isValidUser", isValidUser);

        logger.info(getClass(), "Validation result - isCriminal: {}, isBlacklisted: {}, isValidPassport: {}, isValidUser: {}", isCriminal, isBlacklisted, isValidPassport, isValidUser);
    }
}
