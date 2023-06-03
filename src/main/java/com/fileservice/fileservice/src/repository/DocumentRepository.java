package com.fileservice.fileservice.src.repository;

import com.fileservice.fileservice.src.model.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Integer> {
}
