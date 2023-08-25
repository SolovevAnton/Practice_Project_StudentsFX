package com.solovev.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solovev.dto.ResponseResult;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;

/**
 * Class that used to CRUD with student Servlet server;
 * Server should return Response Result objects in JSON
 *
 * @param <T> must be possible to deserialize this class from JSON with standard ObjectMapper
 */

public abstract class AbstractRepository<T> implements Repository<T> {

    private final Class<T> thisClass;
    private final String link;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    /**
     * made package private for test
     */
    enum SupportedMethods {GET, PUT, POST, DELETE}

    /**
     * @param thisClass   class T that is used in this repo
     * @param link        link to server
     * @param servletName Servlet to get access to for example /tv, /students /cars, etc
     */
    public AbstractRepository(Class<T> thisClass, String link, String servletName) {
        this.thisClass = thisClass;
        this.link = link + servletName;
    }

    /**
     * checks if this response result in this stream contains data return this data, otherwise throws with message of the error
     *
     * @return Data or throws if got error message from server
     * @throws IOException
     */
    private <U> U streamProcessing(InputStream reader) throws IOException {
        ResponseResult<U> responseResult = objectMapper.readValue(reader, new TypeReference<>() {
        });
        U data = responseResult.getData();
        if (data != null) {
            return data;
        } else {
            throw new IllegalArgumentException(responseResult.getMessage());
        }
    }


    /**
     * made public only for tests
     *
     * @param request
     * @param method  this method used only for GET and DELETE methods
     * @return
     * @throws IOException
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
     * Order changed because if T is string there is a problem with method overloading //todo how to avoid this stuff?
     *
     * @param method
     * @param object
     * @return
     * @throws IOException
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
    public Collection<T> takeData() throws IOException {
        try (InputStream reader = makeRequest("", SupportedMethods.GET)) {
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
    public boolean replace(T newElem) {
        return false;
    }

}
