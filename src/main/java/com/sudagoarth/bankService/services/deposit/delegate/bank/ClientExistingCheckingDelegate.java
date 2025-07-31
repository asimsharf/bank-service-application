package com.sudagoarth.bankService.services.deposit.delegate.bank;

import com.sudagoarth.bankService.models.Client;
import com.sudagoarth.bankService.models.Passport;
import com.sudagoarth.bankService.utils.AppLogger;
import com.sudagoarth.bankService.utils.EntityToMapConverter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static com.sudagoarth.bankService.utils.Constants.bankAlreadyClientsInfo;

@Component("clientExistingCheckingDelegate")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientExistingCheckingDelegate implements JavaDelegate {

    final AppLogger logger;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info(getClass(), "Executing client existence check delegate", execution.getVariables());

        Object rawClient = execution.getVariable("client");
        if (!(rawClient instanceof Map)) {
            throw new IllegalStateException("Expected client variable as Map<String, Object>");
        }

        Client client = EntityToMapConverter.mapToClient((Map<String, Object>) rawClient);

        Passport passport = client.getPassport();
        if (passport == null) {
            logger.warn(getClass(), "Client has no passport info, skipping existence check");
            execution.setVariable("isExistingUser", false);
            return;
        }

        boolean isExistingUser = bankAlreadyClientsInfo.stream().anyMatch(info -> matchClientInfo(info, passport));

        logger.info(getClass(), "Client status check completed. Passport: {}, Existing: {}", passport, isExistingUser);

        execution.setVariable("isExistingUser", isExistingUser);
    }

    private boolean matchClientInfo(Passport known, Passport candidate) {
        return Objects.equals(known.getIdenticalNumber(), candidate.getIdenticalNumber()) && Objects.equals(known.getName(), candidate.getName()) && Objects.equals(known.getSurname(), candidate.getSurname());
    }
}
