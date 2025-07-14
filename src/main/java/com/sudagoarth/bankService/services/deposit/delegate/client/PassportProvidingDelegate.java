package com.sudagoarth.bankService.services.deposit.delegate.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sudagoarth.bankService.dtos.ClientDTO;
import com.sudagoarth.bankService.utils.AppLogger;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static com.sudagoarth.bankService.utils.Constants.SUDDEN_OPERATION_INTERRUPTION_ERROR;

@Component("passportProvidingDelegate")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PassportProvidingDelegate implements JavaDelegate {

    AppLogger logger;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info(getClass(), "PassportProvidingDelegate: Executing passport providing process");

        // Get the variable as a Map (because it was stored that way)
        Map<String, Object> clientMap = (Map<String, Object>) delegateExecution.getVariable("client");

        // Convert map back to ClientDTO using Jackson
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        ClientDTO client = mapper.convertValue(clientMap, ClientDTO.class);

        // Business logic
        if (Objects.isNull(client.getPassport())) {
            throw new BpmnError(SUDDEN_OPERATION_INTERRUPTION_ERROR, "The Passport should be present in the client object");
        }

        logger.info(getClass(), "Client {} has provided the passport", client.getName());
    }
}
