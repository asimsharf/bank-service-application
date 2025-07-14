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
import java.util.HashMap;
import java.util.Map;

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
        delegateExecution.setVariable("client", clientToMap(client));

        logger.info(getClass(), "Client {} has paid {} for taxi. Remaining balance: {}", client.getName(), taxiCost, newBalance);
    }

    // Convert LocalDate fields to String
    public Map<String, Object> clientToMap(ClientDTO client) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", client.getId());
        result.put("name", client.getName());
        result.put("surname", client.getSurname());
        result.put("address", client.getAddress());
        result.put("phone", client.getPhone());
        result.put("birthday", client.getBirthday().toString()); // LocalDate → String
        result.put("wallet", Map.of("moneyCount", client.getWallet().getMoneyCount()));
        result.put("passport", Map.of("series", client.getPassport().getSeries(), "identicalNumber", client.getPassport().getIdenticalNumber(), "name", client.getPassport().getName(), "surname", client.getPassport().getSurname(), "address", client.getPassport().getAddress(), "birthDate", client.getPassport().getBirthDate().toString(), "validFrom", client.getPassport().getValidFrom().toString(), "validTo", client.getPassport().getValidTo().toString()));
        return result;
    }

}
