package com.benben.service.result;

import com.benben.dao.entities.Group;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
public class GroupListServiceResult extends CommonServiceResult {

    private List<Group> groupList;
}
