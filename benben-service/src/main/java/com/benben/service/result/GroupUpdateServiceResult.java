package com.benben.service.result;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
public class GroupUpdateServiceResult extends CommonServiceResult {

    private String groupName;

    private Date updateTime;
}
