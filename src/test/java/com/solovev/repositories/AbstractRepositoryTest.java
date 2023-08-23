package com.solovev.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solovev.dto.ResponseResult;
import com.solovev.model.Student;
import com.solovev.testUtilClasses.TestRepo;
import com.solovev.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AbstractRepositoryTest {

    @Test
    public void takeDataWithId() throws IOException {
        String resultedString = "return for id = 1";
        ResponseResult<String> resultToReturn = new ResponseResult<>();
        resultToReturn.setData(resultedString );
        String returnString = objectMapper.writeValueAsString(resultToReturn);
        doReturn(new ByteArrayInputStream(returnString.getBytes())).when(repo).makeRequest("?id=1");

        assertEquals(resultedString,repo.takeData(2));
    }

    @Test
    public void testTakeData() {
        fail();
    }
    @Spy
    private TestRepo repo;
    private ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
}