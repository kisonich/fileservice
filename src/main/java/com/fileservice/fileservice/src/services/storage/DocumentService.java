package com.fileservice.fileservice.src.services.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileservice.fileservice.src.model.Document;
import com.fileservice.fileservice.src.repository.DocumentRepository;
import com.fileservice.fileservice.src.services.storage.file.validator.FileValidatorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    File rootFolder;

    @Autowired
    FileValidatorInterface myFileValidator;

    public ResponseEntity<byte[]> getFileByID(Integer fileId) throws IOException {
        Optional<Document> fileDocument = documentRepository.findById(fileId);
        if (fileDocument.isPresent()) {
            Document document = fileDocument.get();
            String fullName = document.getFullName();
            File file = new File(fullName);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/plain"));
            headers.setContentDispositionFormData("attachment", document.getName());
            headers.setContentLength(fileContent.length);
            return new ResponseEntity<byte[]>(fileContent, headers, HttpStatus.OK);
        }
        return new ResponseEntity<byte[]>(null, null, HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<Document> save(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        //Validation
        ArrayList<String> errors = myFileValidator.validate(file);
        if(errors.size() > 0){
            String errorMessage = objectMapper.writeValueAsString(errors);
            HttpHeaders headers = new HttpHeaders();
            headers.add("errorMessage", errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).build();
        }
        //Create new document from file
        Document document = this.createDocumentFromFile(file);
        documentRepository.save(document);
        ResponseEntity<Document> responseEntity = ResponseEntity.ok(document);
        return responseEntity;
    }

    public Document createDocumentFromFile(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        String filename = file.getOriginalFilename();
        String[] parts = filename.split("\\.");
        String extension = parts[parts.length-1];
        String fullName = rootFolder.getPath() + "/"+ uuid;

        file.transferTo(Path.of(fullName));

        Document document = new Document();
        document.setName(filename)
                .setSize(file.getSize())
                .setFullName(fullName);
        return document;
    }




    public void delete(Document document) throws IOException {
        Files.delete(Path.of(document.getFullName()));
        documentRepository.delete(document);
    }
}
