package com.fileservice.fileservice.src.controller;

import com.fileservice.fileservice.src.model.Document;
import com.fileservice.fileservice.src.repository.DocumentRepository;
import com.fileservice.fileservice.src.services.storage.DocumentService;
import com.fileservice.fileservice.src.services.storage.file.validator.FileValidatorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;


@RestController
public class FileServiceController {

    @Autowired
    File rootFolder;

    @Autowired
    DocumentService documentService;

    @Autowired
    FileValidatorInterface myFileValidator;


    @Autowired
    DocumentRepository documentRepository;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<Document> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        return documentService.save(file);
    }

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public Iterable<Document> getAllFiles(){
        return documentRepository.findAll();
    }

    @RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getFile(@PathVariable Integer fileId) throws IOException {
        return documentService.getFileByID(fileId);
    }

}