package com.benben.controller;

import com.benben.annotation.BenbenApiEndpoint;
import com.benben.global.ApiRequestMapping;
import com.benben.global.constants.ApiName;
import com.benben.global.constants.BenbenConstants;
import com.benben.logging.LoggerFormat;
import com.benben.logging.Loggers;
import com.benben.request.UserRequest;
import com.benben.response.BenbenResponse;
import com.benben.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@CrossOrigin
@RestController
public class DebtController {

    @Autowired
    private UserService service;

    @RequestMapping(value = ApiRequestMapping.DEBT_ADD_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.REGISTER_USER)
    public ResponseEntity<BenbenResponse> addDebt(UserRequest request) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ApiRequestMapping.DEBT_DELETE_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.REGISTER_USER)
    public ResponseEntity<BenbenResponse> deleteDebt(UserRequest request) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ApiRequestMapping.DEBT_UPDATE_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.REGISTER_USER)
    public ResponseEntity<BenbenResponse> udpateDebt(UserRequest request) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ApiRequestMapping.DEBT_LIST_REQUEST_MAPPING, method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.REGISTER_USER)
    public ResponseEntity<BenbenResponse> listDebt(UserRequest request) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
