package com.icsd.dto.common.request;

import java.time.LocalDate;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.icsd.custom.annotation.ValidateCustomerId;
import com.icsd.model.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDTO {

//	@NotBlank (message="account number should not be blank")
//	@NotNull(message="account number should not be null")
//	private String accountNumber;
//	
	@NotBlank(message="customer id should not be blank")
	@NotNull(message="customer id should not be null")
	@ValidateCustomerId
	private int customerId;
	
	private AccountType accountType;
	
	@Min(value = 1 ,message = "opening balance should be bw 1-1000000")
	@Max(value=1000000 ,message = "opening balance should be between 1-1000000")
	
	private String openingBalance;
	
		   
	private LocalDate   openingDate=LocalDate.now();//openingDate
	
	private String description="desc";
	
	
}
