package com.sudagoarth.bankService.services.deposit.delegate.bank;

import com.sudagoarth.bankService.models.Client;
import com.sudagoarth.bankService.models.Passport;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static com.sudagoarth.bankService.utils.Constants.*;

@Component("clientExistingCheckingDelegate")
public class ClientExistingCheckingDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Logger logger = Logger.getLogger(DepositListProvidingDelegate.class.getName());
        logger.info("ClientExistingCheckingDelegate: Executing client existing checking process");

        boolean isExistingUser = false;

        var client = (Client) delegateExecution.getVariable("client");
        var passport = client.getPassport();

        // TODO: call to DB for checking client existence
        isExistingUser = bankAlreadyClientsInfo.stream().anyMatch(info -> matchClientInfo(info, passport));

        if (isExistingUser) {
            logger.info("Client with passport {} is an existing user");
        } else {
            logger.info("Client with passport {} is NOT an existing user");
        }

        delegateExecution.setVariable("isExistingUser", isExistingUser);
    }

    private boolean matchClientInfo(Passport info, Passport passport) {
        return info.getIdenticalNumber().equals(passport.getIdenticalNumber()) && info.getName().equals(passport.getName()) && info.getSurname().equals(passport.getSurname()) && info.getBirthDate().equals(passport.getBirthDate());
    }
}
