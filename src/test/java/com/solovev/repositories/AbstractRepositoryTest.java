package com.solovev.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solovev.dto.ResponseResult;
import com.solovev.testUtilClasses.TestRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AbstractRepositoryTest {

    @Test
    public void takeDataWithId() throws IOException {
        String resultedStringId1 = "return for id = 1";
        String resultedStringId1000 = "return for id = 1000";
        requestResponseObjectFactory("?id=1", resultedStringId1, AbstractRepository.SupportedMethods.GET);
        requestResponseObjectFactory("?id=1000", resultedStringId1000, AbstractRepository.SupportedMethods.GET);

        assertEquals(resultedStringId1, repo.takeData(1));
        assertEquals(resultedStringId1000, repo.takeData(1000));
    }

    @Test
    public void dataIdErrors() throws IOException {
        String resultStringNotFound = "\"Cannot find object with this ID: 0";
        requestResponseMessageFactory("?id=0", resultStringNotFound, AbstractRepository.SupportedMethods.GET);

        assertThrows(IllegalArgumentException.class, () -> repo.takeData(0));
    }

    @Test
    public void testTakeData() throws IOException {
        Collection<String> objectCollection = List.of("Sting 1", "String 2", "String 3");
        ResponseResult<Collection<String>> resultToReturn = new ResponseResult<>();
        resultToReturn.setData(objectCollection);
        String returnString = objectMapper.writeValueAsString(resultToReturn);
        doReturn(new ByteArrayInputStream(returnString.getBytes())).when(repo).makeRequest("", AbstractRepository.SupportedMethods.GET);

        assertEquals(objectCollection, repo.takeData());
    }

    @Test
    public void addTest() throws IOException {
        String toAdd = "STRING TO ADD";
        String corruptedString = "string results in an error message";

        requestObjectResponseObjectFactory(toAdd, toAdd, AbstractRepository.SupportedMethods.POST);
        requestObjectResponseMessageFactory(corruptedString, "some sql exception message", AbstractRepository.SupportedMethods.POST);

        assertEquals(toAdd,repo.add(toAdd));
        assertThrows(IllegalArgumentException.class,()-> repo.add(corruptedString));

    }
    @Test
    public void deleteTest() throws IOException {
        String toDelete = "String to delete"; //it is count as having id 1
        String requestSuccessfullyDelete = "?id=1";
        String requestCannotDelete = "?id=0";

        requestResponseObjectFactory(requestSuccessfullyDelete,toDelete, AbstractRepository.SupportedMethods.DELETE);
        requestResponseMessageFactory(requestCannotDelete,"Cannot find object with this ID", AbstractRepository.SupportedMethods.DELETE);

        assertEquals(toDelete,repo.delete(1));
        assertThrows(IllegalArgumentException.class,()-> repo.delete(0));

    }
    @Test
    public void replaceTest() throws IOException {
        String toBeReplaced = "String to be replaced";
        String toReplaceWith = "String to replace with";
        String corruptedData = "String with corrupted data";

        requestObjectResponseObjectFactory(toReplaceWith,toBeReplaced, AbstractRepository.SupportedMethods.PUT);
        requestObjectResponseMessageFactory(corruptedData,"Cannot find object with this ID", AbstractRepository.SupportedMethods.PUT);

        assertEquals(toBeReplaced,repo.replace(toReplaceWith));
        assertThrows(IllegalArgumentException.class,()-> repo.replace(corruptedData));
    }
    @Spy
    private TestRepo repo;

    /**
     * makes mock return some OBJECT result based on the request
     *
     * @param request              sting with request part
     * @param responseResultObject object to be returned
     */
    private void requestResponseObjectFactory(String request, String responseResultObject, AbstractRepository.SupportedMethods method) throws IOException {
        ResponseResult<String> resultToReturn = new ResponseResult<>();
        resultToReturn.setData(responseResultObject);
        String returnString = objectMapper.writeValueAsString(resultToReturn);
        doReturn(new ByteArrayInputStream(returnString.getBytes())).when(repo).makeRequest(request, method);
    }

    /**
     * makes mock return some OBJECT result based on the request
     *
     * @param responseResultObject object to be returned
     */
    private void requestObjectResponseObjectFactory(String objectToPutInRequest, String  responseResultObject, AbstractRepository.SupportedMethods method) throws IOException {
        ResponseResult<String> resultToReturn = new ResponseResult<>();
        resultToReturn.setData(responseResultObject);
        String returnString = objectMapper.writeValueAsString(resultToReturn);
        doReturn(new ByteArrayInputStream(returnString.getBytes())).when(repo).makeRequest(method, objectToPutInRequest);
    }

    /**
     * makes mock return some Message result based on the request
     *
     * @param request               sting with request part
     * @param responseResultMessage object to be returned
     */
    private void requestResponseMessageFactory(String request, String responseResultMessage, AbstractRepository.SupportedMethods method) throws IOException {
        ResponseResult<String> resultToReturn = new ResponseResult<>();
        resultToReturn.setMessage(responseResultMessage);
        String returnString = objectMapper.writeValueAsString(resultToReturn);
        doReturn(new ByteArrayInputStream(returnString.getBytes())).when(repo).makeRequest(request, method);
    }

    private void requestObjectResponseMessageFactory(String objectToPutInRequest, String responseResultMessage, AbstractRepository.SupportedMethods method) throws IOException {
        ResponseResult<String> resultToReturn = new ResponseResult<>();
        resultToReturn.setMessage(responseResultMessage);
        String returnString = objectMapper.writeValueAsString(resultToReturn);
        doReturn(new ByteArrayInputStream(returnString.getBytes())).when(repo).makeRequest(method, objectToPutInRequest);
    }
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
}