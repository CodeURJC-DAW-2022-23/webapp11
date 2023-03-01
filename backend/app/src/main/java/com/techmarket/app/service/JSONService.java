package com.techmarket.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmarket.app.model.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

// It is not a spring service, but it is a service that returns a ResponseEntity<String>, I don't know where to put it
public class JSONService {

    @NotNull
    public static ResponseEntity<String> getStringResponseEntity(Page<Product> page) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("data", page.getContent());  // The results of the query are key "data"

        ObjectMapper mapper = new ObjectMapper();  // ObjectMapper is a jackson library class that converts java objects to json
        String json = mapper.writeValueAsString(map);  // Convert the map to a json string using jackson library

        return new ResponseEntity<>(json, HttpStatus.OK);  // Return the json string to the ajax call in the frontend, JavaScript will parse it to a JavaScript object and build the html
    }
}
