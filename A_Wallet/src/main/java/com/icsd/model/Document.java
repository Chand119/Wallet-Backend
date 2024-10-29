package com.icsd.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myseq")
	@SequenceGenerator(name = "myseq", sequenceName = "myseq", allocationSize = 1)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="CustomerFK")
	private Customer customer;
	
	private String fileName;
	
	private String filePath;
	
	private String fileType;
	
	private boolean isDeleted=false;
	
	private String documentName;
	
	

}
