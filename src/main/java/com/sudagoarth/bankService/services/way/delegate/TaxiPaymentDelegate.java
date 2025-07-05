package com.sudagoarth.bankService.services.way.delegate;

import com.sudagoarth.bankService.models.Client;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("taxiPaymentDelegate") // <-- Important!
public class TaxiPaymentDelegate implements JavaDelegate {
    private static final Logger logger = LoggerFactory.getLogger(TaxiPaymentDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info("Taxi payment delegate executed");

        Client client = (Client) delegateExecution.getVariable("client");
        String taxiCostStr = (String) delegateExecution.getVariable("taxiCost");

        if (client == null || taxiCostStr == null) {
            throw new IllegalArgumentException("Required variables 'client' or 'taxiCost' not found.");
        }

        BigDecimal taxiCost = new BigDecimal(taxiCostStr);
        BigDecimal walletBalance = client.getWallet().getBalance();
        BigDecimal updatedBalance = walletBalance.subtract(taxiCost);

        logger.info("Money on wallet before payment: {}", walletBalance);
        logger.info("Taxi cost: {}", taxiCost);

        if (updatedBalance.compareTo(BigDecimal.ZERO) < 0) {
            logger.error("Not enough money on wallet to pay for taxi");
            throw new RuntimeException("Not enough money on wallet to pay for taxi");
        }

        client.getWallet().setBalance(updatedBalance);
        delegateExecution.setVariable("client", client);

        logger.info("Taxi payment successful, new wallet balance: {}", updatedBalance);
    }
}
