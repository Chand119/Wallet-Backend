package com.icsd.dto.common.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;

import com.icsd.model.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//
//"data": {
//    "firstName": "rohan",
//    "lastName": "kumar",
//    "emailId": "rb@rb.com",
//    "contactNo": "987654321",
//    "gender": "MALE",
//    "password": "icsd",
//    "confirmPassword": "icsd",
//    "addressLine1": "delhi",
//    "addressLine2": "sec 1123",
//    "city": "delhi",
//    "pincode": "132103",
//    "state": "delhis"
//}
@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomerRequestDTO {
	@NotBlank(message = "First name should not be blank")
	@NotNull(message="First name should not be null")
	@Size(min = 2,max = 60,message = "Name should be between 2 to 60")
	public String firstName;
	
	@NotBlank(message = "Last name should not be blank")
	@NotNull(message = "Last name should not be null")
	public String lastName;
	
	@NotBlank(message = "emailId name should not be blank")
	@NotNull(message="emailId name should not be null")
	@Email(message ="not valid email format ")
	private String emailId;
	
	@NotBlank(message = "contactNo should not be blank")
	@NotNull(message="contactNo should not be null")
	private String contactNo;
	
	private Gender gender;
	
	@NotBlank(message = "Password should not be blank")
	@NotNull(message="Password should not be null")
	//@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
	private String  password;
	
	@NotBlank(message = "confirmPassword name should not be blank")
	@NotNull(message="confirmPassword name should not be null")
	private String confirmPassword;
	
	@NotBlank(message = "addressLine1 name should not be blank")
	@NotNull(message="addressLine1 name should not be null")
	private String addressLine1;
	
	@NotBlank(message = "addressLine2 name should not be blank")
	@NotNull(message="addressLine2  should not be null")
	private String addressLine2;
	
	@NotBlank(message = "city should not be blank")
	@NotNull(message="city should not be null")
	private String city;
	
	@NotBlank(message = "State should not be blank")
	@NotNull(message="State should not be null")
	private String State;
	
	@NotBlank(message = "pincode should not be blank")
	@NotNull(message="pincode should not be null")
	private String pincode;

	@Override
	public String toString() {
		return "CustomerRequestDTO [firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ ", contactNo=" + contactNo + ", gender=" + gender + ", password=" + password + ", confirmPassword="
				+ confirmPassword + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", city="
				+ city + ", State=" + State + ", pincode=" + pincode + "]";
	}
	
	



}
