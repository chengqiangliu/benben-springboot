package com.benben.dao.exception;

import com.benben.dao.error.BenbenDaoError;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
public class DaoException extends RuntimeException {

    private String errorCode;

    private String errorMessage;

    public DaoException(BenbenDaoError daoError) {
        super();
        this.errorCode = daoError.getCode();
        this.errorMessage = daoError.getMessage();
    }

    public DaoException(BenbenDaoError daoError, Exception ex) {
        super();
        this.errorCode = daoError.getCode();
        this.errorMessage = daoError.getMessage() + ex.getMessage();
    }
}
