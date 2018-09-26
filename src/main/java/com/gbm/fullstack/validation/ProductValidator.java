package com.gbm.fullstack.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.gbm.fullstack.dto.ProductDTO;

public class ProductValidator  implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ProductDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "message.name", "Product name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "message.description", "Product description is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "message.password", "Product price is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity", "message.quantity", "Product quantity is required.");
		
	}

}
