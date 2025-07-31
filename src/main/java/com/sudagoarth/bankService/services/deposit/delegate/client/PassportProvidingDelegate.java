package com.sudagoarth.bankService.services.deposit.delegate.client;

import com.sudagoarth.bankService.models.Client;
import com.sudagoarth.bankService.utils.AppLogger;
import com.sudagoarth.bankService.utils.EntityToMapConverter;
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
    public void execute(DelegateExecution execution) throws Exception {
        logger.info(getClass(), "Executing passport providing process");

        @SuppressWarnings("unchecked") Map<String, Object> clientMap = (Map<String, Object>) execution.getVariable("client");

        if (clientMap == null) {
            throw new BpmnError(SUDDEN_OPERATION_INTERRUPTION_ERROR, "Client variable is missing from process context");
        }

        Client client = EntityToMapConverter.mapToClient(clientMap);

        if (Objects.isNull(client.getPassport())) {
            throw new BpmnError(SUDDEN_OPERATION_INTERRUPTION_ERROR, "Passport must be provided for client verification");
        }

        logger.info(getClass(), "Client {} has provided a valid passport", client.getName());
    }
}
