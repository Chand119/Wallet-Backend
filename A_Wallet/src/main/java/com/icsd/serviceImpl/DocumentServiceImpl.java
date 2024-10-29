package com.icsd.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icsd.dto.common.request.DocumentDTO;
import com.icsd.model.Document;
import com.icsd.repo.CustomerRepo;
import com.icsd.repo.DocumentRepo;
import com.icsd.service.DocumentService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {
	@Autowired
	DocumentRepo dr;
	
	@Autowired
	CustomerRepo cr;
	
//location	C:\Users\HP\Documents\workspace-spring-tool-suite-4-4.24.0.RELEASE\A_Wallet\src\main\resources\static\images
	private final  String upload_dir="C:\\Users\\HP\\Documents\\workspace-spring-tool-suite-4-4.24.0.RELEASE\\A_Wallet\\src\\main\\resources\\static\\images\\";

	@Override
	public Document saveDoc(DocumentDTO docDTO) {
		Document existedDoc=dr.isDocPresent(docDTO.getCustomerId(), docDTO.getDocumentName());
		Document createdDoc;
		
		if(existedDoc!=null) {
		
			 existedDoc.setDeleted(true);
			 
		}
		
			Document doc1=Document.builder().customer(cr.findById(docDTO.getCustomerId()).get())
			.documentName(docDTO.getDocumentName())
			.filePath(upload_dir+docDTO.getMfile().getOriginalFilename())
			.fileName(docDTO.getMfile().getOriginalFilename())
			.fileType(docDTO.getMfile().getContentType()).build();
			 createdDoc=dr.save(doc1);
			 try {
					log.info("going to put in system");
					docDTO.getMfile().transferTo(new File(upload_dir+docDTO.getMfile().getOriginalFilename()));
				} catch (IllegalStateException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return createdDoc;
	}

	@Override
	public List<String> getImagesByCustomerId(int customerId) {
		
		List<String> lst=new ArrayList<>();
		List<Document> lst2=dr.getImagesByCustomerId(customerId);
		for (Document document : lst2) {
			lst.add(document.getFilePath());
		}
		
		return lst;
	}

	@Override
	public boolean deleteByFile(String fileName,int customerId) {
		Document obj=dr.findByFileNameAndCustomer_CustomerId(fileName,customerId);
		System.out.println(obj);
		if(obj==null) {
			return false;
		}
		obj.setDeleted(true);
		dr.save(obj);
		return true;
	}

	@Override
	public boolean checkDoc(String docName, int customerId) {
		Document doc=dr.findByDocumentNameAndCustomer_CustomerId(docName,customerId);
		if(doc==null) {
			return false;
		}
				return true;
	}

}
