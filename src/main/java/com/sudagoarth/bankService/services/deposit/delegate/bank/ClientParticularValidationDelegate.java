package com.sudagoarth.bankService.services.deposit.delegate.bank;

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

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info(getClass(), "Starting client criminal check", execution.getVariables());

        Client client = (Client) execution.getVariable("client");
        boolean isCriminal = validationService.isClientWantedByPolice(client);

        execution.setVariable("isCriminal", isCriminal);
        execution.setVariable("isValidUser", !isCriminal);

        logger.info(getClass(), "Client validation: isCriminal={}, isValidUser={}", isCriminal, !isCriminal);
    }
}
