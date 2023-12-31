package com.solovev.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.solovev.dto.ResponseResult;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Class that used to CRUD with student Servlet server;
 * Server should return Response Result objects in JSON
 *
 * @param <T> must be possible to deserialize this class from JSON with standard ObjectMapper
 */

public abstract class AbstractRepository<T> implements Repository<T> {

    private final String link;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private final Class<T> tClass;

    /**
     * made package private for tests
     */
    enum SupportedMethods {GET, PUT, POST, DELETE}

    /**
     * @param link        link to server
     * @param servletName Servlet to get access to for example /tv, /students /cars, etc
     */
    public AbstractRepository(String link, String servletName, Class<T> tClass) {
        this.link = link + servletName;
        this.tClass = tClass;
    }

    @Override
    public T add(T elem) throws IOException {
        try (InputStream reader = makeRequest(SupportedMethods.POST, elem)) {
            return streamProcessing(reader);
        }
    }

    @Override
    public T delete(int elemId) throws IOException {
        String request = "?id=" + elemId;
        try (InputStream reader = makeRequest(request, SupportedMethods.DELETE)) {
            return streamProcessing(reader);
        }
    }

    @Override
    public T takeData(int elemId) throws IOException {
        String request = "?id=" + elemId;
        try (InputStream reader = makeRequest(request, SupportedMethods.GET)) {
            return streamProcessing(reader);
        }
    }

    @Override
    public T replace(T newElem) throws IOException {
        try (InputStream reader = makeRequest(SupportedMethods.PUT, newElem)) {
            return streamProcessing(reader);
        }
    }

    /**
     * made public only for tests
     *
     * @param request request parameters
     * @param method  this method used only for GET and DELETE methods
     * @return stream with response result from the server
     * @throws IOException if IO exception occurs
     */
    public InputStream makeRequest(String request, SupportedMethods method) throws IOException {
        URL url = new URL(link + request);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(method.toString());

        if (httpURLConnection.getResponseCode() != 200) {
            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getErrorStream()))) {
                String error = bufferedReader.readLine();
                throw new IllegalArgumentException(error);
            }
        }
        return httpURLConnection.getInputStream();
    }

    /**
     * Order changed because if T is string there is a problem with method overloading
     *
     * @param method used for Put and Post methods
     * @param object to send to server as a JSON object
     * @return stream with response result from the server
     * @throws IOException if IO exception occurs
     */
    public InputStream makeRequest(SupportedMethods method, T object) throws IOException {
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(method.toString());
        httpURLConnection.setRequestProperty("Content-Type", "application/json;utf-8");
        httpURLConnection.setDoOutput(true);

        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream())) {
            this.objectMapper.writeValue(bufferedOutputStream, object);
            if (httpURLConnection.getResponseCode() != 200) {
                try (BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getErrorStream()))) {
                    String error = bufferedReader.readLine();
                    throw new IllegalArgumentException(error);
                }
            }
        }
        return httpURLConnection.getInputStream();
    }

    /**
     * checks if this response result in this stream contains data return this data, otherwise throws with message of the error
     *
     * @return Data or throws if got error message from server
     * @throws IOException if IO exception occurs
     */
    private T streamProcessing(InputStream reader) throws IOException {
        JavaType resultType =
                objectMapper.getTypeFactory().constructParametricType(ResponseResult.class, tClass);

        ResponseResult<T> responseResult = objectMapper.readValue(reader, resultType);
        T data = responseResult.getData();
        if (data != null) {
            return data;
        } else {
            throw new IllegalArgumentException(responseResult.getMessage());
        }
    }

    @Override
    public Collection<T> takeData() throws IOException {
        try (InputStream reader = makeRequest("", SupportedMethods.GET)) {

            // construct correct deserializer for response result class
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(Collection.class,tClass);
            JavaType type = objectMapper.getTypeFactory().constructParametricType(ResponseResult.class, collectionType);

            ResponseResult<Collection<T>> responseResult = objectMapper.readValue(reader, type);
            Collection<T> data = responseResult.getData();
            if (data != null) {
                return data;
            } else {
                throw new IllegalArgumentException(responseResult.getMessage());
            }
        }
    }

    @Override
    public boolean containsId(int elemId) throws IOException {
        try{
            takeData(elemId);
            return  true;
        } catch (IllegalArgumentException ignored){
            return false;
        }
    }
}
