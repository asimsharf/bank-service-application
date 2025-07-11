package com.sudagoarth.bankService.services.deposit.delegate.bank;

import com.sudagoarth.bankService.models.Client;
import com.sudagoarth.bankService.services.ValidationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component("clientFullValidationDelegate")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientFullValidationDelegate implements JavaDelegate {

    @Autowired
    private ValidationService validationService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("ClientFullValidationDelegate: Validating client particulars");

        //1. Check if client wanted by police ?
        //2. Is client in the bank blacklist ?
        //3. Is client passport valid ?
        var client = (Client) delegateExecution.getVariable("client");

        var isCriminal = validationService.isClientWantedByPolice(client);
        var isBlacklisted = validationService.isClientBlacklisted(client);
        var isValidPassport = validationService.isValidPassport(client);

        delegateExecution.setVariable("isCriminal", isCriminal);
        delegateExecution.setVariable("isBlacklisted", isBlacklisted);
        delegateExecution.setVariable("isValidPassport", isValidPassport);

        if (isCriminal || isBlacklisted || !isValidPassport) {
            delegateExecution.setVariable("isValidUser", false);
            log.info("Client validation failed: isCriminal={}, isBlacklisted={}, isValidPassport={}", isCriminal, isBlacklisted, isValidPassport);
        } else {
            delegateExecution.setVariable("isValidUser", true);
            log.info("Client validation passed: isCriminal={}, isBlacklisted={}, isValidPassport={}", isCriminal, isBlacklisted, isValidPassport);
        }

    }
}
