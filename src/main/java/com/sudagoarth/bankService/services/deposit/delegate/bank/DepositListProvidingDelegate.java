package com.sudagoarth.bankService.services.deposit.delegate.bank;

import com.sudagoarth.bankService.dtos.DepositDTO;
import com.sudagoarth.bankService.models.Deposit;
import com.sudagoarth.bankService.utils.AppLogger;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.List;
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

        // TODO: fetch deposits from database instead of constant if needed
        List<DepositDTO> depositDTOs = bankDeposits.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        delegateExecution.setVariable("bankDeposits", depositDTOs);
    }

    private DepositDTO toDTO(Deposit deposit) {
        return new DepositDTO(
                deposit.getName(),
                deposit.getMinimalSum(),
                deposit.getCurrentSum(),
                deposit.getOpenDate(),
                deposit.getCloseDate(),
                deposit.getPercentage(),
                deposit.getIsCapitalized(),
                deposit.getCurrency(),
                deposit.getTermInMonth()
        );
    }
}
