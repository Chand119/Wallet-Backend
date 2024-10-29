package com.icsd.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Address")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Address {
	@SequenceGenerator(name="generator", sequenceName = "addId_seq",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "generator")
	@Id
	private int addressId;
	
	private String addressLine1;
	
	private String addressLine2;
 
	private String city;
	
	private String state;
	
	private String pincode;

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2
				+ ", city=" + city + ", state=" + state + ", pincode=" + pincode + "]";
	}
	


}
