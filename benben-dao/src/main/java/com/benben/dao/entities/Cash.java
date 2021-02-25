package com.benben.dao.entities;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="cash_table")
@Proxy(lazy = false)
@Data
public class Cash {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "account_id")
	private String accountId;
	
	@Column(name = "currency")
	private String currency;

	@Column(name = "balance")
	private BigDecimal balance;

	@Column(name = "insert_time")
	private Date insertTime;

	@Column(name = "update_time")
	private Date updateTime;
}
