package com.sudagoarth.bankService.services.way.delegate;

import com.sudagoarth.bankService.models.Client;
import com.sudagoarth.bankService.utils.AppLogger;
import com.sudagoarth.bankService.utils.EntityToMapConverter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component("taxiPaymentDelegate")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaxiPaymentDelegate implements JavaDelegate {

    AppLogger logger;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info(getClass(), "TaxiPaymentDelegate: Executing taxi payment...");

        // ✅ Convert Map → Client
        Map<String, Object> clientMap = (Map<String, Object>) delegateExecution.getVariable("client");
        Client client = EntityToMapConverter.mapToClient(clientMap);

        // ✅ Perform taxi cost deduction
        String taxiCostStr = (String) delegateExecution.getVariable("taxiCost");
        BigDecimal taxiCost = new BigDecimal(taxiCostStr);

        BigDecimal newBalance = client.getWallet().getMoneyCount().subtract(taxiCost);
        client.getWallet().setMoneyCount(newBalance);

        // ✅ Convert Client → Map and store it back
        delegateExecution.setVariable("client", EntityToMapConverter.convertClientToMap(client));

        logger.info(getClass(), "Client {} has paid {} for taxi. Remaining balance: {}", client.getName(), taxiCost, newBalance);
    }
}
