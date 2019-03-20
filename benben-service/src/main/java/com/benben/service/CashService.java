package com.benben.service;

import com.benben.service.exception.ServiceSystemException;
import com.benben.service.input.CashServiceInput;
import com.benben.service.result.GetCashServiceResult;
import com.benben.service.result.SimpleServiceResult;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
public interface CashService {

    SimpleServiceResult addCash(CashServiceInput input) throws ServiceSystemException;

    SimpleServiceResult updateCash(CashServiceInput input) throws ServiceSystemException;

    SimpleServiceResult deleteCash(String username, String currency) throws ServiceSystemException;

    GetCashServiceResult getCashsByAccountIdAndCurrency(String accountId, String currency) throws ServiceSystemException;
}
