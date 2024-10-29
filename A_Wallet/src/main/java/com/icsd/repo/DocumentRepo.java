package com.icsd.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.icsd.model.Document;

public interface DocumentRepo extends JpaRepository<Document, Integer> {
	@Query("select d from Document d  where d.customer.customerId=:customerId and d.documentName=:documentName and d.isDeleted=false")
	public Document isDocPresent(@Param("customerId") int customerId,@Param("documentName") String documentName);
	
	@Query("select d from Document d where d.customer.customerId=:customerId and d.isDeleted=false")
	public List<Document> getImagesByCustomerId(@Param("customerId") int customerId);
	
	@Query("select d from Document d where d.customer.customerId=:customerId and d.fileName=:fileName and d.isDeleted=false")
	public Document findByFileNameAndCustomer_CustomerId(@Param("fileName") String fileName,@Param("customerId") int customerId);
	
	@Query("select d from Document d where d.customer.customerId=:customerId and d.documentName=:documentName and d.isDeleted=false")
	public Document findByDocumentNameAndCustomer_CustomerId(@Param("documentName")String documentName,@Param("customerId") int customerId);
	
	//public boolean deleteByFilePath(String filePath);

}
