package com.icsd.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.icsd.custom.annotation.ValidateCustomerId;
import com.icsd.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomerIdValidator implements ConstraintValidator<ValidateCustomerId, Integer> {
	 final CustomerService customerService;

	@Override
	public boolean isValid(Integer customerId, ConstraintValidatorContext context) {
		
		
		if(customerId==null || customerId<=0)
		{
			return false;
		}
		return customerService.isValidCustomerId(customerId);
	}
	

}
