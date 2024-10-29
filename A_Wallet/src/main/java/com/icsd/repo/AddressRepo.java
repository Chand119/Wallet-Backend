package com.icsd.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icsd.model.Address;

public interface AddressRepo extends JpaRepository<Address, Integer> {
	 public List<Address> findByAddressLine2IsNull();
	  
	 public List<Address> findByAddressLine2IsNotNull();

}
