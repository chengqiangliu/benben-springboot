package com.benben.service.impl;

import com.benben.dao.entities.Bankcard;
import com.benben.dao.repositories.BankcardRepository;
import com.benben.service.BankcardService;
import com.benben.service.error.BenbenServiceError;
import com.benben.service.exception.ServiceSystemException;
import com.benben.service.input.BankcardAddServiceInput;
import com.benben.service.input.BankcardUpdateServiceInput;
import com.benben.service.result.BankcardListServiceResult;
import com.benben.service.result.BankcardUpdateServiceResult;
import com.benben.service.result.GetBankcardServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Service
public class BankcardServiceImpl implements BankcardService {

    @Autowired
    private BankcardRepository bankcardRepository;

    private Clock clock = Clock.systemUTC();

    @Override
    public void addBankcard(BankcardAddServiceInput input) throws ServiceSystemException {
        boolean existsFlg = this.bankcardRepository.existsByCardNo(input.getCardNo());
        if (existsFlg) {
            throw new ServiceSystemException(BenbenServiceError.BANKCARD_ALREADY_EXISTS);
        }

        Date currentTime = new Date(this.clock.millis());
        Bankcard bankcard = new Bankcard();
        bankcard.setCardNo(input.getCardNo());
        bankcard.setCardType(input.getCardType().getName());
        bankcard.setBankType(input.getBankType());
        bankcard.setAccountId(input.getAccountId());
        bankcard.setCurrency(input.getCurrency());
        bankcard.setCreditSum(input.getCreditSum());
        bankcard.setBalance(input.getBalance());
        bankcard.setIssueDate(input.getIssueDate());
        bankcard.setInsertTime(currentTime);
        bankcard.setUpdateTime(currentTime);
        this.bankcardRepository.save(bankcard);
    }

    @Override
    public BankcardUpdateServiceResult updateBankcard(BankcardUpdateServiceInput input) throws ServiceSystemException {
        Bankcard bankcard = this.bankcardRepository.findByCardNo(input.getCardNo());
        if (bankcard == null) {
            throw new ServiceSystemException(BenbenServiceError.BANKCARD_IS_NOT_FOUND);
        }
        Date currentTime = new Date(this.clock.millis());
        bankcard.setCreditSum(input.getCreditSum());
        bankcard.setBalance(input.getBalance());
        bankcard.setBankType(input.getBankType());
        bankcard.setCardType(input.getCardType().getName());
        bankcard.setUpdateTime(currentTime);
        this.bankcardRepository.save(bankcard);

        BankcardUpdateServiceResult result = new BankcardUpdateServiceResult();
        result.setUpdateTime(currentTime);
        return result;
    }

    @Override
    public void deleteBankcard(String cardNo) throws ServiceSystemException {
        long deletedCount = this.bankcardRepository.deleteByCardNo(cardNo);
        if (deletedCount == 0) {
            throw new ServiceSystemException(BenbenServiceError.GROUP_IS_NOT_FOUND);
        }
    }

    @Override
    public GetBankcardServiceResult getBankcardByNo(String cardNo) throws ServiceSystemException {
        Bankcard bankcard = this.bankcardRepository.findByCardNo(cardNo);
        if (bankcard == null) {
            throw new ServiceSystemException(BenbenServiceError.BANKCARD_IS_NOT_FOUND);
        }

        GetBankcardServiceResult result = new GetBankcardServiceResult();
        result.setBankcard(bankcard);
        return result;
    }

    @Override
    public BankcardListServiceResult getBankcardsByAccountIdAndCurrency(UUID accountId, String currency) throws ServiceSystemException {
        List<Bankcard> bankcardList = this.bankcardRepository.findAllByAccountIdAndCurrency(accountId, currency);
        BankcardListServiceResult serviceResult = new BankcardListServiceResult();
        serviceResult.setBankcardList(bankcardList);
        return serviceResult;
    }
}
