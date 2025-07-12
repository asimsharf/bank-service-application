package com.sudagoarth.bankService.services.way.delegate;

import com.sudagoarth.bankService.dtos.ClientDTO;
import com.sudagoarth.bankService.models.Client;
import com.sudagoarth.bankService.utils.AppLogger;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("taxiPaymentDelegate")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaxiPaymentDelegate implements JavaDelegate {

    AppLogger logger;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info(getClass(), "TaxiPaymentDelegate: Executing taxi payment...");

        ClientDTO client = (ClientDTO) delegateExecution.getVariable("client");
        var taxiCostStr = (String) delegateExecution.getVariable("taxiCost");
        var taxiCost = new BigDecimal(taxiCostStr);

        var newBalance = client.getWallet().getMoneyCount().subtract(taxiCost);
        client.getWallet().setMoneyCount(newBalance);
        delegateExecution.setVariable("client", client);

        logger.info(getClass(), "Client {} has paid {} for taxi. Remaining balance: {}", client.getName(), taxiCost, newBalance);
    }
}
