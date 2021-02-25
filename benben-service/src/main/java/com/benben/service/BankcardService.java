package com.benben.service;

import com.benben.service.exception.ServiceSystemException;
import com.benben.service.input.BankcardAddServiceInput;
import com.benben.service.input.BankcardUpdateServiceInput;
import com.benben.service.result.*;

import java.util.UUID;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
public interface BankcardService {

    void addBankcard(BankcardAddServiceInput input) throws ServiceSystemException;

    BankcardUpdateServiceResult updateBankcard(BankcardUpdateServiceInput input) throws ServiceSystemException;

    void deleteBankcard(String cardNo) throws ServiceSystemException;

    GetBankcardServiceResult getBankcardByNo(String cardNo) throws ServiceSystemException;

    BankcardListServiceResult getBankcardsByAccountIdAndCurrency(UUID accountId, String currency) throws ServiceSystemException;
}
