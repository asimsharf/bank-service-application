package com.sudagoarth.bankService.controllers;

import com.sudagoarth.bankService.utils.AppLogger;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ProcessEngines;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.sudagoarth.bankService.utils.Constants.MAIN_DEPOSIT_CREDIT_PROCESS_ID;
import static com.sudagoarth.bankService.utils.Constants.asim;
import static com.sudagoarth.bankService.utils.EntityToMapConverter.convertClientToMap;

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

        Map<String, Object> variables = new HashMap<>();

        Map<String, Object> clientAsMap = convertClientToMap(asim);

        variables.put("client", clientAsMap);

        ProcessEngines.getDefaultProcessEngine().getRuntimeService().createProcessInstanceByKey(MAIN_DEPOSIT_CREDIT_PROCESS_ID).businessKey(businessKey).setVariables(variables).executeWithVariablesInReturn();

        logger.info(getClass(), "Bank process started successfully", "businessKey", businessKey);
        return ResponseEntity.ok("Bank process with business key " + businessKey + " has been started successfully");
    }
}
