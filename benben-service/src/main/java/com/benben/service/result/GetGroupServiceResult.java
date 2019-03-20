package com.benben.service.result;

import com.benben.dao.entities.Group;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
public class GetGroupServiceResult extends CommonServiceResult {

    private Group group;
}
