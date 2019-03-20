package com.benben.service.result;

import com.benben.dao.entities.Cash;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
public class GetCashServiceResult extends CommonServiceResult {

    private Cash cash;
}
