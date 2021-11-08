package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TransferService {

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferService(String url){
        this.baseUrl = url; //this is localhost:8080
    }

    public User[] getAllUsers(String token) {
        User[] users = null;
        try {
            ResponseEntity<User[]> responseEntity = restTemplate.exchange(baseUrl + "getUsers", HttpMethod.GET, getHttpEntity(token), User[].class);
            users = responseEntity.getBody();
        }
        catch (RestClientResponseException e) { //error message from server
            System.out.println("Error returned from server: " + e.getRawStatusCode() + ": " + e.getStatusText());
        }
        catch (ResourceAccessException e) { //couldn't reach server
            System.out.println("Error: Couldn't reach server.");
        }
        return users;
    }

    public Transfer createTransfer(Transfer transfer, String token) {
        Transfer newTransfer = new Transfer();
        try {
            newTransfer = restTemplate.postForObject(baseUrl + "createTransfer", getTransferEntity(transfer, token), Transfer.class);
        }
        catch (RestClientResponseException e) { //error message from server
            System.out.println("Error returned from server: " + e.getRawStatusCode() + ": " + e.getStatusText());
        }
        catch (ResourceAccessException e) { //couldn't reach server
            System.out.println("Error: Couldn't reach server.");
        }
        return newTransfer;
    }

    public Transfer makeTransferObject(int userId, BigDecimal amount){ //set id, type, status
        Transfer transfer = new Transfer();
        transfer.setAccountFrom(1003); //find actual accountId from current user
        transfer.setAccountTo(userId);
        transfer.setAmount(amount);
        return transfer; //figure out error returned from server: 500
        // test for nonexistent toAccount and balance
    }

    private HttpEntity<?> getHttpEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity(headers);
        return entity;
    }

    private HttpEntity<?> getTransferEntity(Transfer transfer, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity(transfer, headers);
        return entity;
    }
}
