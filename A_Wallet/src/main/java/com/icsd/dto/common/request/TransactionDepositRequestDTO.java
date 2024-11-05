package com.icsd.dto.common.request;



import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


import com.icsd.custom.annotation.ValidateCustomerId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//txtCustid: 1
//selAccountNo: 1
//txtAmount: 10000
//txtDesc: desc
//selfromAcc: 1
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDepositRequestDTO {

	@Min(value=1,message="customer id should not be 0 ")
	@ValidateCustomerId
	int customerId;
	
	@Min(value=1,message ="acount number shoulde not be 0 ")
	int accountNumber;
	
	@Min(value = 1 ,message = "amount should be bw 1-10000")
	@Max(value=10000 ,message = "amount should be between 1-10000")
	double amount;
	
	@NotBlank (message="description  should not be blank")
	String description;
	
	@Min(value=1,message="account number should not be 0 ")
	int fromAccountNumber;
	
	
	
	
	
	
}
