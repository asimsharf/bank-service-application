package com.sudagoarth.bankService.services.deposit.delegate.bank;

import com.sudagoarth.bankService.models.Deposit;
import com.sudagoarth.bankService.utils.AppLogger;
import com.sudagoarth.bankService.utils.EntityToMapConverter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sudagoarth.bankService.utils.Constants.bankDeposits;

@Component("depositListProvidingDelegate")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepositListProvidingDelegate implements JavaDelegate {

    AppLogger logger;

    @Override
    public void execute(DelegateExecution execution) {
        logger.info(getClass(), "Executing deposit list provider delegate");

        List<Map<String, Object>> depositList = bankDeposits.stream().map(EntityToMapConverter::convertDepositToMap).collect(Collectors.toList());

        execution.setVariable("bankDeposits", depositList);
        logger.info(getClass(), "Provided deposit list with {} items", depositList.size());
    }
}
