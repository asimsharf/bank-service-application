package com.sudagoarth.bankService.controllers;

import com.sudagoarth.bankService.dtos.ClientDTO;
import com.sudagoarth.bankService.dtos.PassportDTO;
import com.sudagoarth.bankService.dtos.WalletDTO;
import com.sudagoarth.bankService.models.Client;
import com.sudagoarth.bankService.utils.AppLogger;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.sudagoarth.bankService.utils.Constants.MAIN_DEPOSIT_CREDIT_PROCESS_ID;
import static com.sudagoarth.bankService.utils.Constants.asim;

@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class BankController {

    AppLogger logger;

    @PostMapping("/start/{businessKey}")
    public ResponseEntity<String> startBankProcess(@PathVariable("businessKey") String businessKey) {
        logger.info(getClass(), "Received request to start bank process", "businessKey", businessKey);

        if (StringUtils.isBlank(businessKey)) {
            return ResponseEntity.badRequest().body("Business key cannot be empty or null");
        }

        ProcessEngines.getDefaultProcessEngine()
                .getRuntimeService()
                .createProcessInstanceByKey(MAIN_DEPOSIT_CREDIT_PROCESS_ID)
                .businessKey(businessKey)
                .setVariables(prepareVariables(asim))
                .executeWithVariablesInReturn();

        logger.info(getClass(), "Bank process started successfully", "businessKey", businessKey);
        return ResponseEntity.ok("Bank process with business key " + businessKey + " has been started successfully");
    }

    private Map<String, Object> prepareVariables(Client client) {
        Map<String, Object> variables = new HashMap<>();
        ClientDTO clientDTO = toDTO(client);

        ObjectValue typedClient = Variables
                .objectValue(clientDTO)
                .serializationDataFormat(Variables.SerializationDataFormats.JAVA)
                .create();

        variables.put("client", typedClient);
        return variables;
    }

    private ClientDTO toDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .address(client.getAddress())
                .phone(client.getPhone())
                .birthday(client.getBirthday())
                .wallet(WalletDTO.builder().moneyCount(client.getWallet().getMoneyCount()).build())
                .passport(client.getPassport() != null ? PassportDTO.builder()
                        .series(client.getPassport().getSeries())
                        .identicalNumber(client.getPassport().getIdenticalNumber())
                        .name(client.getPassport().getName())
                        .surname(client.getPassport().getSurname())
                        .address(client.getPassport().getAddress())
                        .birthDate(client.getPassport().getBirthDate())
                        .validFrom(client.getPassport().getValidFrom())
                        .validTo(client.getPassport().getValidTo())
                        .build() : null)
                .build();
    }

}
