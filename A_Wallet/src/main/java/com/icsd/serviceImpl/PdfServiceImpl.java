package com.icsd.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.icsd.model.Account;
import com.icsd.model.Customer;
import com.icsd.model.Transaction;
import com.icsd.model.TransactionType;
import com.icsd.repo.TransactionRepo;
import com.icsd.service.PdfService;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class PdfServiceImpl implements PdfService {
	
	private final TransactionRepo transactionRepo;

	@Override
	public byte[] generateCustomerPdf(List<Customer> customers) {
		
		try {
		ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
		PdfWriter writer = new PdfWriter(arrayOutputStream);
		PdfDocument pdfdocument= new PdfDocument(writer);
		Document document= new Document(pdfdocument);
		for (Customer customer : customers) {
			document.add(new Paragraph("Customer Id:"+customer.getCustomerId()));
			document.add(new Paragraph("Name:"+customer.getFirstName()+" "+customer.getLastName()));
			document.add(new Paragraph("Email:"+customer.getEmailId()));
			document.add(new Paragraph("Contact No:"+customer.getContactNo()));
			document.add(new Paragraph("Address:"+customer.getAddress().getAddressLine1()+","+customer.getAddress().getAddressLine2()+","+customer.getAddress().getCity()+","+customer.getAddress().getState()+"-"+customer.getAddress().getPincode()));
			document.add(new Paragraph("Registration Date:"+customer.getRegistrationDate().toString()));
			document.add(new Paragraph(" "));
			
			List<Account> accounts=customer.getAccounts();
			if(ObjectUtils.isEmpty(accounts)) {
				document.add(new Paragraph(" ****No accounts****"));
				document.add(new Paragraph(""));
			}
			else {
				for(Account account:accounts) {
					document.add(new Paragraph("Account Number:"+account.getId()));
                    document.add(new Paragraph("Account Type:"+account.getAccountType()));
                    document.add(new Paragraph("Opening Balance:"+account.getOpeningBalance()));
                    document.add(new Paragraph("Opening Date:"+account.getOpeningDate()));
                    document.add(new Paragraph("Description:"+account.getDescription()));
                    document.add(new Paragraph(" "));
                    
                    List<Transaction> transactions=transactionRepo.findCreditsByAccountId(account.getId(), TransactionType.CREDIT);
                    transactions.addAll(transactionRepo.findDebitsByAccountId(account.getId(), TransactionType.DEBIT));
                    if(ObjectUtils.isEmpty(transactions)) {
                    	document.add(new Paragraph("***No Transactions***** "));
                    	document.add(new Paragraph(" "));
                    }
                    else {
                    	 Table table = new Table(3);
                         table.addHeaderCell("Transaction ID");
                         table.addHeaderCell("Amount");
                         table.addHeaderCell("Date");
                         
                       
                         
                    for(Transaction transaction:transactions) {
                    	 table.addCell(String.valueOf(transaction.getTransactionId()));
                         table.addCell(String.valueOf(transaction.getAmount()));
                         table.addCell(transaction.getTransactionDate().toString());
                        
                         
                    	
                    }
                    document.add(table);
                    document.add(new Paragraph(""));
                    
                    }

				}
				document.add(new Paragraph(""));
			}
			
			
			
		}
		document.close();
		return arrayOutputStream.toByteArray();
		}
		catch (Exception e) {
		e.printStackTrace();	
		return null;
		}
		
	}

}
