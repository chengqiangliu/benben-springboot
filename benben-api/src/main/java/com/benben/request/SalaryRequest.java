package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * SalaryRequest
 */
@Setter
@Getter
public class SalaryRequest extends BaseRequest {

	/** salary_id. */
	private Integer salaryId;

	/** card_id. */
	private String cardId;

	/** detail. */
	private String detail;

	/** yearmonth. */
	@Min(197001)
	@Max(999912)
	private Integer yearMonth;

	/** salary_num. */
	@Digits(integer=9, fraction=2)
	private BigDecimal salaryNum;

	/** payroll_type. */
	@Min(1)
	@Max(2)
	private Integer payrollType;

	/** owner_id. */
	private String owner;

	/** currency_id. */
	private Integer currency;
}
