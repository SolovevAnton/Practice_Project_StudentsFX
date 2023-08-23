package com.solovev.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solovev.dto.ResponseResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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

    private enum SupportedMethods {GET, PUT, POST, DELETE}

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
     * made public only for test purposes!
     * @param request
     * @return
     * @throws IOException
     */
    public InputStream makeRequest(String request) throws IOException {
        URL url = new URL(link + request);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(SupportedMethods.GET.toString());
        httpURLConnection.setRequestProperty("Content-Type", "application/json;utf-8");

        if (httpURLConnection.getResponseCode() != 200) {
            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getErrorStream()))) {
                String error = bufferedReader.readLine();
                throw new IllegalArgumentException(error);
            }
        }
        return httpURLConnection.getInputStream();
    }

    @Override
    public boolean add(T elem) {
        return false;
    }

    @Override
    public T delete(int elemId) {
        return null;
    }

    @Override
    public Collection<T> takeData() throws IOException {
        try (InputStream reader = makeRequest("")) {
            ResponseResult<Collection<T>> result = objectMapper.readValue(reader, new TypeReference<>() {
            });
            Collection<T> data = result.getData();
            if (data  != null) {
                return data;
            } else {
                throw new IllegalArgumentException(result.getMessage());
            }
        }
    }

    @Override
    public T takeData(int elemId) throws IOException {
        String request = "?id=" + elemId;
        try (InputStream reader = makeRequest(request)) {
            ResponseResult<T> result = objectMapper.readValue(reader, new TypeReference<>() {
            });
            T data = result.getData();
            if (data  != null) {
                return data;
            } else {
                throw new IllegalArgumentException(result.getMessage());
            }
        }
    }

    @Override
    public boolean replace(T newElem) {
        return false;
    }

}
