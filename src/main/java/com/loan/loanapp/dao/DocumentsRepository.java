package com.loan.loanapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.loanapp.entity.Document;

public interface DocumentsRepository extends JpaRepository<Document, Integer> {

}
