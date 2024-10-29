package com.icsd.serviceImpl;

import java.time.LocalDate;
import java.util.List;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.icsd.model.Customer;
import com.icsd.repo.CustomerRepo;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

	
	private final JavaMailSender mailsender;
	
	
	private final CustomerRepo cr;
	
	
	//@Scheduled(cron = "0 0 0 * * *")
	//@Scheduled(fixedRate = 5000)
	
	public void sendExpiryEmail() {
		log.info("in mail sender");
		
   List<Customer> lst=cr.getCustomerByExpiryDate(LocalDate.now(), LocalDate.now().plusDays(1));
   
		
   if(ObjectUtils.isEmpty(lst)) {
	   log.info("String is Empty!!!!!!");
	   return;
   }
   
   for (Customer cust:lst) {
	   if(cust.getExpiryDate()==LocalDate.now()) {
		   SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(cust.getEmailId()); 
	        message.setSubject("");
	        message.setText("This Email is to inform Mr/Mrs "+ cust.getFirstName()+" that your account is going to expire today  please upgrade it to enjoy the features !!!");
	        mailsender.send(message);
	   }
	   else {
		   SimpleMailMessage message=new SimpleMailMessage();
		   message.setTo(cust.getEmailId());
		   message.setSubject("Account Expiry!!!");
		   message.setText("This is Email is to inform Mr/Mrs "+cust.getFirstName()+" that your account is going to expire tommorrow please upgrade it to enjoy the features!!!");
		   mailsender.send(message);
	   } 
	
}
	}
}
