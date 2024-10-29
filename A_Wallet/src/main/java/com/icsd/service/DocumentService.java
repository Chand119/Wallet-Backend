package com.icsd.service;

import java.util.List;

import com.icsd.dto.common.request.DocumentDTO;
import com.icsd.model.Document;

public interface DocumentService {
	public Document saveDoc(DocumentDTO docDTO);
	public List<String> getImagesByCustomerId(int customerId);
	public boolean deleteByFile(String fileName,int customerId);
	public boolean checkDoc(String docName,int customerId);

}
