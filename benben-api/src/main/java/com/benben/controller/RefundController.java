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
public class RefundController {

    @Autowired
    private UserService service;

    @RequestMapping(value = ApiRequestMapping.REFUND_ADD_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.REGISTER_USER)
    public ResponseEntity<BenbenResponse> addRefund(UserRequest request) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ApiRequestMapping.REFUND_DELETE_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.REGISTER_USER)
    public ResponseEntity<BenbenResponse> deleteRefund(UserRequest request) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ApiRequestMapping.REFUND_UPDATE_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.REGISTER_USER)
    public ResponseEntity<BenbenResponse> udpateRefund(UserRequest request) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ApiRequestMapping.REFUND_LIST_REQUEST_MAPPING, method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.REGISTER_USER)
    public ResponseEntity<BenbenResponse> listRefund(UserRequest request) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
