package com.benben.controller;

import com.benben.annotation.BenbenApiEndpoint;
import com.benben.global.ApiRequestMapping;
import com.benben.global.constants.ApiName;
import com.benben.global.constants.BenbenConstants;
import com.benben.global.utils.DateUtils;
import com.benben.logging.LoggerFormat;
import com.benben.logging.Loggers;
import com.benben.request.CashDeleteRequest;
import com.benben.request.CashRequest;
import com.benben.request.CashGetRequest;
import com.benben.response.CashAddResponse;
import com.benben.response.CashGetResponse;
import com.benben.response.CommonUpdateResponse;
import com.benben.global.http.ResponseStatus;
import com.benben.service.CashService;
import com.benben.service.input.CashServiceInput;
import com.benben.service.result.GetCashServiceResult;
import com.benben.service.result.SimpleServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@CrossOrigin
@RestController
public class CashController {

    @Autowired
    private CashService service;

    /**
     * add a new group
     *
     * @param request
     * @return
     */
    @RequestMapping(value = ApiRequestMapping.CASH_ADD_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.ADD_CASH)
    public ResponseEntity<CashAddResponse> addCash(@Valid @RequestBody CashRequest request) {

        CashServiceInput serviceInput = new CashServiceInput();
        serviceInput.setUsername(request.getUsername());
        serviceInput.setCurrency(request.getCurrency());
        serviceInput.setBalance(request.getBalance());

        SimpleServiceResult serviceResult = this.service.addCash(serviceInput);
        CashAddResponse response = new CashAddResponse();
        response.setAddTime(DateUtils.getDateInISOFormat(serviceResult.getTime()));

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, ResponseStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = ApiRequestMapping.CASH_GET_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.GET_CASH)
    public ResponseEntity<CashGetResponse> getCash(@Valid @RequestBody CashGetRequest request) {

        GetCashServiceResult serviceResult = this.service.getCashsByAccountIdAndCurrency(request.getUsername(),
                request.getCurrency());
        CashGetResponse response = new CashGetResponse();
        response.setAccountId(serviceResult.getCash().getAccountId());
        response.setCurrency(serviceResult.getCash().getCurrency());
        response.setBalance(serviceResult.getCash().getBalance());
        response.setInsertTime(DateUtils.getDateInISOFormat(serviceResult.getCash().getInsertTime()));
        response.setUpdateTime(DateUtils.getDateInISOFormat(serviceResult.getCash().getUpdateTime()));

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, ResponseStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ApiRequestMapping.CASH_UPDATE_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.UPDATE_CASH)
    public ResponseEntity<CommonUpdateResponse> updateCash(@Valid @RequestBody CashRequest request) {
        CashServiceInput serviceInput = new CashServiceInput();
        serviceInput.setUsername(request.getUsername());
        serviceInput.setCurrency(request.getCurrency());
        serviceInput.setBalance(request.getBalance());

        SimpleServiceResult serviceResult = this.service.updateCash(serviceInput);

        CommonUpdateResponse response = new CommonUpdateResponse();
        response.setUpdateTime(DateUtils.getDateInISOFormat(serviceResult.getTime()));

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, ResponseStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ApiRequestMapping.CASH_DELETE_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.DELETE_CASH)
    public ResponseEntity<CommonUpdateResponse> deleteCash(@Valid @RequestBody CashDeleteRequest request) {
        SimpleServiceResult serviceResult = this.service.deleteCash(request.getUsername(),
                request.getCurrency());

        CommonUpdateResponse response = new CommonUpdateResponse();
        response.setUpdateTime(DateUtils.getDateInISOFormat(serviceResult.getTime()));

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, ResponseStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
