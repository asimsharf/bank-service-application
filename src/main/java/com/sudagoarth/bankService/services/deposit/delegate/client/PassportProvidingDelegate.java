package com.sudagoarth.bankService.services.deposit.delegate.client;

import com.sudagoarth.bankService.dtos.ClientDTO;
import com.sudagoarth.bankService.models.Client;
import com.sudagoarth.bankService.utils.AppLogger;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.sudagoarth.bankService.utils.Constants.*;

@Component("passportProvidingDelegate")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PassportProvidingDelegate implements JavaDelegate {

    AppLogger logger;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info(getClass(), "PassportProvidingDelegate: Executing passport providing process");

        ClientDTO client = (ClientDTO) delegateExecution.getVariable("client");

        if (Objects.isNull(client.getPassport())) {
            throw new BpmnError(SUDDEN_OPERATION_INTERRUPTION_ERROR, "The Passport should be present in the client object");
        }

        logger.info(getClass(), "Client {} has provided the passport", client.getName());
    }
}
