package com.benben.dao.entities;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="group_table")
@Proxy(lazy = false)
@Data
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "group_no")
    private int groupNo;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "secret_key")
    private String secretKey;

    @Column(name = "is_admin_group")
    private boolean isAdminGroup;

    @Column(name = "api_list")
    private String apiList;

    @Column(name = "insert_time")
    private Date insertTime;

    @Column(name = "update_time")
    private Date updateTime;
}
