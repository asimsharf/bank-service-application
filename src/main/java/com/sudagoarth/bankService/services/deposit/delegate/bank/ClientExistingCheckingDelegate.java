package com.sudagoarth.bankService.services.deposit.delegate.bank;

import com.sudagoarth.bankService.dtos.ClientDTO;
import com.sudagoarth.bankService.dtos.PassportDTO;
import com.sudagoarth.bankService.models.Passport;
import com.sudagoarth.bankService.utils.AppLogger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.sudagoarth.bankService.utils.Constants.*;

@Component("clientExistingCheckingDelegate")
public class ClientExistingCheckingDelegate implements JavaDelegate {

    @Autowired
    private AppLogger logger;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info(getClass(), "Executing client existence check delegate", delegateExecution.getVariables());

        ClientDTO client = (ClientDTO) delegateExecution.getVariable("client");
        PassportDTO passport = client.getPassport();

        boolean isExistingUser = bankAlreadyClientsInfo.stream().anyMatch(info -> matchClientInfo(info, passport));


        logger.info(getClass(), "Client status: {}, Passport: {}, Existing: {}", isExistingUser ? "existing" : "new", passport, isExistingUser);


        delegateExecution.setVariable("isExistingUser", isExistingUser);
    }

    private boolean matchClientInfo(Passport info, PassportDTO passport) {
        return info.getIdenticalNumber().equals(passport.getIdenticalNumber()) && info.getName().equals(passport.getName()) && info.getSurname().equals(passport.getSurname());
    }
}
