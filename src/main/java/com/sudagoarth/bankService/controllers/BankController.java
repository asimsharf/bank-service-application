package com.sudagoarth.bankService.controllers;

import com.sudagoarth.bankService.models.Client;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ProcessEngines;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.sudagoarth.bankService.utils.Constants.*;


@Slf4j
@RestController
@RequestMapping("/api/bank")
public class BankController {

    @PostMapping("/start/{businessKey}")
    public ResponseEntity<String> startBankProcess(@PathVariable("businessKey") String businessKey) {
        log.info("Received request to start bank process with business key: {}", businessKey);

        if (StringUtils.isEmpty(businessKey)) {
            return ResponseEntity.badRequest().body("Business key cannot be empty or null");
        }

        ProcessEngines.getDefaultProcessEngine()
                .getRuntimeService()
                .createProcessInstanceByKey(MAIN_DEPOSIT_CREDIT_PROCESS_ID)
                .businessKey(businessKey)
//                .setVariables(prepareVariables(asim))
                .setVariables(prepareVariables(asim))
                .executeWithVariablesInReturn();
        return ResponseEntity.ok(String.format("Bank process with business key %s has been started successfully", businessKey));
    }

    private Map<String, Object> prepareVariables(Client client) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("client", client);
        return variables;
    }
}
