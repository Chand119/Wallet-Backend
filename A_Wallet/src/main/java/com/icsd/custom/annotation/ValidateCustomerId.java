package com.icsd.custom.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.icsd.validation.CustomerIdValidator;

@Target({ElementType.PARAMETER,ElementType.FIELD,ElementType.METHOD})
@Constraint(validatedBy=CustomerIdValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateCustomerId {
	
	String message() default "Invalid CustomerId";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
