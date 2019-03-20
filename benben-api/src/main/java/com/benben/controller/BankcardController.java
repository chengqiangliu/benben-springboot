package com.benben.controller;

import com.benben.annotation.BenbenApiEndpoint;
import com.benben.global.ApiRequestMapping;
import com.benben.global.constants.ApiName;
import com.benben.global.constants.BankcardType;
import com.benben.global.constants.BenbenConstants;
import com.benben.global.utils.DateUtils;
import com.benben.logging.LoggerFormat;
import com.benben.logging.Loggers;
import com.benben.request.BankcardRequest;
import com.benben.request.UserRequest;
import com.benben.response.BankcardAddResponse;
import com.benben.response.BenbenResponse;
import com.benben.service.BankcardService;
import com.benben.service.UserService;
import com.benben.service.input.BankcardAddServiceInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@CrossOrigin
@RestController
public class BankcardController {

    @Autowired
    private UserService userService;

    @Autowired
    private BankcardService bankcardService;

    @RequestMapping(value = ApiRequestMapping.BANKCARD_ADD_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.ADD_BANKCARD)
    public ResponseEntity<BankcardAddResponse> addBankcard(@Valid @RequestBody BankcardRequest request) {

        BankcardAddServiceInput serviceInput = new BankcardAddServiceInput();
        serviceInput.setAccountId(request.getAccountId());
        serviceInput.setBankType(request.getBankType());
        serviceInput.setCardType(BankcardType.valueOf(request.getCardType()));
        serviceInput.setCardNo(request.getCardNo());
        serviceInput.setCreditSum(request.getCreditSum());
        serviceInput.setBalance(request.getCardBalance());
        serviceInput.setIssueDate(request.getIssueDate());
        this.bankcardService.addBankcard(serviceInput);

        BankcardAddResponse response = new BankcardAddResponse();
        response.setCardNo(serviceInput.getCardNo());
        response.setIssueDate(DateUtils.getDateInISOFormat(serviceInput.getIssueDate()));

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ApiRequestMapping.BANKCARD_DELETE_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.REGISTER_USER)
    public ResponseEntity<BenbenResponse> deleteBankcard(UserRequest request) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ApiRequestMapping.BANKCARD_UPDATE_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.REGISTER_USER)
    public ResponseEntity<BenbenResponse> udpateBankcard(UserRequest request) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ApiRequestMapping.BANKCARD_LIST_REQUEST_MAPPING, method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.REGISTER_USER)
    public ResponseEntity<BenbenResponse> listBankcard(UserRequest request) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
