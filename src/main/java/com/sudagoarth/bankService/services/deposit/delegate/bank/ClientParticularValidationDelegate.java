package com.sudagoarth.bankService.services.deposit.delegate.bank;

import com.sudagoarth.bankService.services.ValidationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Slf4j
@RequiredArgsConstructor
@Component("clientParticularValidationDelegate")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientParticularValidationDelegate implements JavaDelegate {

    @Autowired
    private ValidationService validationService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("ClientParticularValidationDelegate: Validating client particulars");

    }
}
