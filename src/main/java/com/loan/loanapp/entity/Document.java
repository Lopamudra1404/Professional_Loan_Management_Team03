package com.loan.loanapp.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer documentId;
	private String documentNo;
	private DocumentType documentType;
	
	
	public Document() {
		super();
	}

	

	public Document(Integer documentId, String documentNo, DocumentType documentType) {
		super();
		this.documentId = documentId;
		this.documentNo = documentNo;
		this.documentType = documentType;
	}



	public Integer getDocumentId() {
		return documentId;
	}



	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}



	public String getDocumentNo() {
		return documentNo;
	}



	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}



	public DocumentType getDocumentType() {
		return documentType;
	}



	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}



	
}