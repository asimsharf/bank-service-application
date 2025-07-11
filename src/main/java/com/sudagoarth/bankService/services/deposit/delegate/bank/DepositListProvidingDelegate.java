package com.sudagoarth.bankService.services.deposit.delegate.bank;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static com.sudagoarth.bankService.utils.Constants.*;

@Slf4j
@Component("depositListProvidingDelegate")
public class DepositListProvidingDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Logger logger = Logger.getLogger(DepositListProvidingDelegate.class.getName());
        logger.info("DepositListProvidingDelegate: Executing deposit list providing process");

        // TODO: obtain the list of bankDeposits from DB
        delegateExecution.setVariable("bankDeposits", bankDeposits);

    }
}
