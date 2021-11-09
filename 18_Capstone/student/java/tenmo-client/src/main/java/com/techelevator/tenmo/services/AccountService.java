package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public AccountService(String url){
        this.baseUrl = url; //this is localhost:8080
    }

    public BigDecimal getBalance(String token) {
        BigDecimal balance = BigDecimal.ZERO;
        try {
            ResponseEntity<BigDecimal> responseEntity = restTemplate.exchange(baseUrl + "getAccountBalance", HttpMethod.GET, getHttpEntity(token), BigDecimal.class);
            balance = responseEntity.getBody();
        }
        catch (RestClientResponseException e) { //error message from server
            System.out.println("Error returned from server: " + e.getRawStatusCode());
        }
        catch (ResourceAccessException e) { //couldn't reach server
            System.out.println("Error: Couldn't reach server.");
        }
        return balance;
    }

    private HttpEntity<?> getHttpEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity(headers);
        return entity;
    }

}
