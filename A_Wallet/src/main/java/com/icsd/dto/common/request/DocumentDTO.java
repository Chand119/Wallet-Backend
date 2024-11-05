package com.icsd.dto.common.request;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.icsd.custom.annotation.ValidateCustomerId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {

	@ValidateCustomerId
	private int customerId;
	private String documentName;
	@JsonIgnore
	private MultipartFile mfile;
}
