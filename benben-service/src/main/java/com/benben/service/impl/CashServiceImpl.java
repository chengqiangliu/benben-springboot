package com.benben.service.impl;

import com.benben.dao.entities.Cash;
import com.benben.dao.repositories.CashRepository;
import com.benben.dao.repositories.UserRepository;
import com.benben.service.CashService;
import com.benben.service.error.BenbenServiceError;
import com.benben.service.exception.ServiceSystemException;
import com.benben.service.input.CashServiceInput;
import com.benben.service.result.GetCashServiceResult;
import com.benben.service.result.SimpleServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Date;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Service
public class CashServiceImpl implements CashService {

    @Autowired
    private CashRepository cashRepository;

    @Autowired
    private UserRepository userRepository;

    private Clock clock = Clock.systemUTC();

    @Override
    public SimpleServiceResult addCash(CashServiceInput input) throws ServiceSystemException {
        String accountId = this.getAccountId(input.getUsername());
        boolean existsFlg = this.cashRepository.existsByAccountIdAndCurrency(accountId, input.getCurrency());
        if (existsFlg) {
            throw new ServiceSystemException(BenbenServiceError.CASH_ALREADY_EXISTS);
        }

        Date currentTime = new Date(this.clock.millis());
        Cash cash = new Cash();
        cash.setAccountId(accountId.toString());
        cash.setCurrency(input.getCurrency());
        cash.setBalance(input.getBalance());
        cash.setInsertTime(currentTime);
        cash.setUpdateTime(currentTime);
        this.cashRepository.save(cash);

        SimpleServiceResult result = new SimpleServiceResult();
        result.setTime(currentTime);
        return result;
    }

    @Override
    public SimpleServiceResult updateCash(CashServiceInput input) throws ServiceSystemException {
        String accountId = this.getAccountId(input.getUsername());
        Cash cash = this.cashRepository.findByAccountIdAndCurrency(accountId, input.getCurrency());
        if (cash == null) {
            throw new ServiceSystemException(BenbenServiceError.CASH_IS_NOT_FOUND);
        }
        Date currentTime = new Date(this.clock.millis());
        cash.setBalance(input.getBalance());
        cash.setUpdateTime(currentTime);
        this.cashRepository.save(cash);

        SimpleServiceResult result = new SimpleServiceResult();
        result.setTime(currentTime);
        return result;
    }

    @Override
    public SimpleServiceResult deleteCash(String username, String currency) throws ServiceSystemException {
        String accountId = this.getAccountId(username);
        long deletedCount = this.cashRepository.deleteByAccountIdAndCurrency(accountId, currency);
        if (deletedCount == 0) {
            throw new ServiceSystemException(BenbenServiceError.CASH_IS_NOT_FOUND);
        }
        Date currentTime = new Date(this.clock.millis());
        SimpleServiceResult result = new SimpleServiceResult();
        result.setTime(currentTime);
        return result;
    }

    @Override
    public GetCashServiceResult getCashsByAccountIdAndCurrency(String username, String currency) throws ServiceSystemException {
        String accountId = this.getAccountId(username);
        Cash cash = this.cashRepository.findByAccountIdAndCurrency(accountId, currency);
        if (cash == null) {
            throw new ServiceSystemException(BenbenServiceError.CASH_IS_NOT_FOUND);
        }

        GetCashServiceResult result = new GetCashServiceResult();
        result.setCash(cash);
        return result;
    }

    private String getAccountId(String username) {
        String accountId = this.userRepository.findAccountIdByUsername(username);
        if (accountId == null) {
            throw new ServiceSystemException(BenbenServiceError.USER_IS_NOT_FOUND);
        }
        return accountId;
    }
}
