package com.sudagoarth.bankService.services.way.delegate;

import com.sudagoarth.bankService.models.Client;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("taxiPaymentDelegate")
public class TaxiPaymentDelegate implements JavaDelegate {
    private static final Logger logger = LoggerFactory.getLogger(TaxiPaymentDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info("The TaxiPaymentDelegate has executed ...");
        var client = (Client) delegateExecution.getVariable("client");
        var taxiCost = (String) delegateExecution.getVariable("taxiCost");
        var moneyOnWallet = client.getWallet().getMoneyCount().subtract(new BigDecimal(taxiCost));
        client.getWallet().setMoneyCount(moneyOnWallet);
        delegateExecution.setVariable("client", client);
        logger.info("Client {} has paid for taxi ride. Remaining wallet balance: {}", client.getName(), moneyOnWallet);
    }
}
