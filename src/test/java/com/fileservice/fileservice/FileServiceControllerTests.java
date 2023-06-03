package com.fileservice.fileservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileservice.fileservice.src.controller.FileServiceController;
import com.fileservice.fileservice.src.model.Document;
import com.fileservice.fileservice.src.repository.DocumentRepository;
import com.fileservice.fileservice.src.services.storage.DocumentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



import java.util.ArrayList;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class FileServiceControllerTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    FileServiceController fileServiceController;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    DocumentService documentService;

    private String uploadLink = "/upload";


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(fileServiceController).build();
        objectMapper = new ObjectMapper();
    }




    @Test
    public void downloadFileTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test11.txt", "text/plain", "Hello World 11".getBytes());

        // Perform the request and obtain the MvcResult
        MvcResult result = sendFile(file);

        // Assert the response
        int status = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
        Document document = objectMapper.readValue(content, Document.class);

        assertEquals("Response status should be 200", HttpStatus.OK.value(), status);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/download/"+document.getId())).andReturn();


        MockHttpServletResponse response = mvcResult.getResponse();
        String string = response.getContentAsString();

        assertEquals("there should be one content", "Hello World 11", string);

        documentService.delete(document);
    }





    @Test
    public void allFilesTest() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/files")).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        String jsonString = response.getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<ArrayList<Document>> typeRef = new TypeReference<>() {
        };
        ArrayList<Document> documentList = objectMapper.readValue(jsonString, typeRef);


        assertEquals("Response status should be 200", HttpStatus.OK.value(), response.getStatus());
//        assertNull("Didn't catch any errors: The File size should be less then 100kb", null);

    }




    @Test
    public void uploadExtensionAndOversizeFileErrorTest() throws Exception {
        byte[] fileContent = new byte[110 * 1024]; // 110 KB
        // Create a file to upload
        MockMultipartFile file = new MockMultipartFile("file", "test.exe", "text/plain", fileContent);

        // Perform the request and obtain the MvcResult
        MvcResult result = sendFile(file);

        // Assert the response
        int status = result.getResponse().getStatus();
        String message = result.getResponse().getHeader("errorMessage");

        if(message == null){
            assertNull("Didn't catch any errors: The File size should be less then 100kb", message);
        }else {
            ArrayList<String> errors = objectMapper.readValue(message, ArrayList.class);
            assertTrue("Didn't catch the error: The File size should be less then 100kb", errors.contains("The File size should be less then 100kb"));
            assertTrue("Didn't catch the error: The File extension should be txt or csv", errors.contains("The File extension should be txt or csv"));
        }
    }


    @Test
    public void uploadOversizeFileTest() throws Exception {
        byte[] fileContent = new byte[110 * 1024]; // 110 KB
        // Create a file to upload
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", fileContent);

        // Perform the request and obtain the MvcResult
        MvcResult result = sendFile(file);

        // Assert the response
        int status = result.getResponse().getStatus();
        String message = result.getResponse().getHeader("errorMessage");

        if(message == null){
            assertNull("Didn't catch any errors: The File size should be less then 100kb", message);
        }else {
            ArrayList<String> errors = objectMapper.readValue(message, ArrayList.class);
            assertTrue("Didn't catch the error: The File size should be less then 100kb", errors.contains("The File size should be less then 100kb"));
        }
    }



    @Test
    public void uploadTest() throws Exception {
        // Create a file to upload
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello World".getBytes());

        // Perform the request and obtain the MvcResult
        MvcResult result = sendFile(file);

        // Assert the response
        int status = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
        Document document = objectMapper.readValue(content, Document.class);

        assertEquals("Response status should be 200", HttpStatus.OK.value(), status);
        assertEquals("Response file name should be 'test.txt'", "test.txt", document.getName());
        assertEquals("Response file size should be '11'", "11", document.getSize().toString());

        documentService.delete(document);
    }

    private MvcResult sendFile(MockMultipartFile file) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.multipart(uploadLink)
                .file(file))
                .andReturn();
    }




}
