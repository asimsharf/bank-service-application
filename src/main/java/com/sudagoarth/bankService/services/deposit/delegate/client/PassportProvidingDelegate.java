package com.sudagoarth.bankService.services.deposit.delegate.client;

import com.sudagoarth.bankService.models.Client;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.sudagoarth.bankService.utils.Constants.*;

@Slf4j
@Component("passportProvidingDelegate")
public class PassportProvidingDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Logger logger = LoggerFactory.getLogger(PassportProvidingDelegate.class);
        logger.info("PassportProvidingDelegate: Executing passport providing process");

        var client = (Client) delegateExecution.getVariable("client");

        if (Objects.isNull(client.getPassport())) {
            throw new BpmnError(SUDDEN_OPERATION_INTERRUPTION_ERROR, "The Passport should be present in the client object");
        }

        logger.info("Client: {} has provided the passport", client.getName());

    }
}
