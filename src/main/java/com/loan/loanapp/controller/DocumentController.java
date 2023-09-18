package com.loan.loanapp.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.entity.Document;
import com.loan.loanapp.exception.CustomerException;
import com.loan.loanapp.exception.DocumentException;
import com.loan.loanapp.service.DocumentService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentController {
	@Autowired
	DocumentService documentService;

	@PostMapping("/customer/document/{customerId}")
	public Customer addDocument(@RequestBody Document newDocument, @PathVariable Integer customerId)
			throws CustomerException {

		return this.documentService.addDocument(newDocument, customerId);
	}

	@GetMapping("/document/display/{id}")
	public Document getProductById(@PathVariable("id") Integer id) throws DocumentException {
		return this.documentService.getDocumentById(id);
	}

	@GetMapping("/documents")
	public Collection<Document> getAllDocuments() {
		Collection<Document> documentCollection = this.documentService.getAllDocuments();
		return documentCollection;
	}

}