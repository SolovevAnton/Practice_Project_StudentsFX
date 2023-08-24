package com.solovev.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solovev.dto.ResponseResult;
import com.solovev.model.Student;
import com.solovev.testUtilClasses.TestRepo;
import com.solovev.util.Constants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.*;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AbstractRepositoryTest {

    @Test
    public void takeDataWithId() throws IOException {
        String resultedStringId1 = "return for id = 1";
        String resultedStringId1000 = "return for id = 1000";
        requestResponseObjectFactory("?id=1",resultedStringId1);
        requestResponseObjectFactory("?id=1000",resultedStringId1000);

        assertEquals(resultedStringId1,repo.takeData(1));
        assertEquals(resultedStringId1000,repo.takeData(1000));
    }
    @Test
    public void dataIdErrors() throws IOException {
        String resultStringNotFound= "\"Cannot find object with this ID: 0";
        requestResponseMessageFactory("?id=0",resultStringNotFound);

        assertThrows(IllegalArgumentException.class, () -> repo.takeData(0));
    }

    @Test
    public void testTakeData() throws IOException {
        Collection<String> objectCollection = List.of("Sting 1","String 2","String 3");
        ResponseResult<Collection<String>> resultToReturn = new ResponseResult<>();
        resultToReturn.setData(objectCollection);
        String returnString = objectMapper.writeValueAsString(resultToReturn);
        doReturn(new ByteArrayInputStream(returnString.getBytes())).when(repo).makeRequest("");

        assertEquals(objectCollection,repo.takeData());
    }
    @Spy
    private TestRepo repo;

    /**
     * makes mock return some OBJECT result based on the request
     * @param request sting with request part
     * @param responseResultObject object to be returned
     */
    private void requestResponseObjectFactory(String request,String responseResultObject) throws IOException {
        ResponseResult<String> resultToReturn = new ResponseResult<>();
        resultToReturn.setData(responseResultObject);
        String returnString = objectMapper.writeValueAsString(resultToReturn);
        doReturn(new ByteArrayInputStream(returnString.getBytes())).when(repo).makeRequest(request);
    }
    /**
     * makes mock return some Message result based on the request
     * @param request sting with request part
     * @param responseResultMessage object to be returned
     */
    private void requestResponseMessageFactory(String request,String responseResultMessage) throws IOException {
        ResponseResult<String> resultToReturn = new ResponseResult<>();
        resultToReturn.setMessage(responseResultMessage);
        String returnString = objectMapper.writeValueAsString(resultToReturn);
        doReturn(new ByteArrayInputStream(returnString.getBytes())).when(repo).makeRequest(request);
    }
    private ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
}