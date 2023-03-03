package com.techmarket.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmarket.app.model.Message;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.Purchase;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// It is not a spring service, but it is a service that returns a ResponseEntity<String>, I don't know where to put it
public class JSONService {

    @NotNull
    public static ResponseEntity<String> getProductStringResponseEntity(Page<Product> page) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("data", page.getContent());  // The results of the query are key "data"

        ObjectMapper mapper = new ObjectMapper();  // ObjectMapper is a jackson library class that converts java objects to json
        String json = mapper.writeValueAsString(map);  // Convert the map to a json string using jackson library

        return new ResponseEntity<>(json, HttpStatus.OK);  // Return the json string to the ajax call in the frontend, JavaScript will parse it to a JavaScript object and build the html
    }

    @NotNull
    public static ResponseEntity<String> getPurchaseStringResponseEntity(Page<Purchase> page) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("data", page.getContent());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @NotNull
    public static ResponseEntity<String> getMessageStringResponseEntity(Page<Message> page) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("data", page.getContent());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    // Create the sublist from the start index to the end index, and then create a Page object from the sublist
    // This is how we decided to implement the pagination of lists we get directly from the User model
    @NotNull
    public static ResponseEntity<String> getStringResponseEntity(@RequestParam("start") int start, List<Product> productList) throws JsonProcessingException {
        int end = Math.min(start + 10, productList.size());
        productList = productList.subList(start, end);
        Page<Product> page = new PageImpl<>(productList, PageRequest.of(0, 10), productList.size());


        return JSONService.getProductStringResponseEntity(page);
    }
}
