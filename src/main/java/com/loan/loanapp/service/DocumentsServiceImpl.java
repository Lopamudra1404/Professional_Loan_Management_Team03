package com.loan.loanapp.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loan.loanapp.dao.CustomerRepository;
import com.loan.loanapp.dao.DocumentsRepository;
import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.entity.Document;
import com.loan.loanapp.exception.CustomerException;
import com.loan.loanapp.exception.DocumentException;

@Service
public class DocumentsServiceImpl implements DocumentService {

	@Autowired
	DocumentsRepository documentsRepo;

	@Autowired
	CustomerRepository customerRepo;

	@Override
	public Customer addDocument(Document newDocument, Integer customerId) throws CustomerException {
		Optional<Customer> customerOpt = this.customerRepo.findById(customerId);
		if (!customerOpt.isPresent())
			throw new CustomerException("Customer not present for this id: " + customerId);
		newDocument = this.documentsRepo.save(newDocument);
		Customer customer = customerOpt.get();
		customer.getDocuments().add(newDocument);
		return this.customerRepo.save(customer);
	}

	@Override
	public Document getDocumentById(Integer id) throws DocumentException {

		Optional<Document> documentOpt = this.documentsRepo.findById(id);
		if (!documentOpt.isPresent())
			throw new DocumentException("Documents doesn't exist for id: " + id);
		return documentOpt.get();
	}

	@Override
	public String deleteDocumentById(Integer id) throws DocumentException {

		Optional<Document> documentOpt = this.documentsRepo.findById(id);
		if (!documentOpt.isPresent())
			throw new DocumentException("Document doesn't exist to delete for id: " + id);
		this.documentsRepo.deleteById(id);
		Document document = documentOpt.get();
		this.documentsRepo.delete(document);
		return "Successfully deleted";

	}

	@Override
	public Customer deleteDocumentsByCustomerId(Integer customerId, Integer documentId)
			throws DocumentException, CustomerException {

		Optional<Customer> customerOpt = this.customerRepo.findById(customerId);
		if (!customerOpt.isPresent())
			throw new CustomerException("Customer does not exist: " + customerId);
		Customer customer = customerOpt.get();
		List<Document> documentsList = customer.getDocuments();
		Optional<Document> documentOpt = this.documentsRepo.findById(documentId);
		if (!documentOpt.isPresent())
			throw new DocumentException("Documents does not exist");
		Document deleteDocument = documentOpt.get();
		if (!documentsList.contains(deleteDocument))
			throw new CustomerException("Document does not exist for the customer");
		documentsList.remove(deleteDocument);
		customer = this.customerRepo.save(customer);
		return customer;
	}

	@Override
	public Collection<Document> getAllDocuments() {

		return this.documentsRepo.findAll();
	}

}