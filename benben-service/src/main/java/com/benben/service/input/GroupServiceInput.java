package com.benben.service.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
@NoArgsConstructor
public class GroupServiceInput {

    private int groupNo;

    private String groupName;

    private boolean isAdminGroup;

    private List<String> apiList;
}
