package com.loan.loanapp.service;

import java.util.Collection;

import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.entity.Document;
import com.loan.loanapp.exception.CustomerException;
import com.loan.loanapp.exception.DocumentException;

public interface DocumentService {

	Customer addDocument(Document newDocument, Integer customerId) throws CustomerException;

	Document getDocumentById(Integer id) throws DocumentException;

	String deleteDocumentById(Integer id) throws DocumentException;

	Customer deleteDocumentsByCustomerId(Integer customerId, Integer documentId)
			throws DocumentException, CustomerException;

	Collection<Document> getAllDocuments();

}