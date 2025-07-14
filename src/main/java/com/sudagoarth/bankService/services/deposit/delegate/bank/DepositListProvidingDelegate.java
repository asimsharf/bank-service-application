package com.sudagoarth.bankService.services.deposit.delegate.bank;

import com.sudagoarth.bankService.models.Deposit;
import com.sudagoarth.bankService.utils.AppLogger;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sudagoarth.bankService.utils.Constants.*;

@Component("depositListProvidingDelegate")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepositListProvidingDelegate implements JavaDelegate {

    AppLogger logger;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info(getClass(), "DepositListProvidingDelegate: Executing deposit list providing process");

        // Convert deposit objects to JSON-serializable maps
        List<Map<String, Object>> depositMaps = bankDeposits.stream().map(this::toMap).collect(Collectors.toList());

        // Set Camunda variable with a serializable list of maps
        delegateExecution.setVariable("bankDeposits", depositMaps);
    }

    private Map<String, Object> toMap(Deposit deposit) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", deposit.getName());
        map.put("minimalSum", deposit.getMinimalSum());
        map.put("currentSum", deposit.getCurrentSum());
        map.put("openDate", deposit.getOpenDate().toString());   // Ensure it's a String
        map.put("closeDate", deposit.getCloseDate().toString()); // Ensure it's a String
        map.put("percentage", deposit.getPercentage());
        map.put("isCapitalized", deposit.getIsCapitalized());
        map.put("currency", deposit.getCurrency());
        map.put("termInMonth", deposit.getTermInMonth());
        return map;
    }
}
