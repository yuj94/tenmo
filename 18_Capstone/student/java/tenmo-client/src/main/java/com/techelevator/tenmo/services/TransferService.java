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
            System.out.println("Error returned from server: " + e.getRawStatusCode());
        }
        catch (ResourceAccessException e) { //couldn't reach server
            System.out.println("Error: Couldn't reach server.");
        }
        return users;
    }

    public Transfer[] getAllTransfers(String token) {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> responseEntity = restTemplate.exchange(baseUrl + "getTransfers", HttpMethod.GET, getHttpEntity(token), Transfer[].class);
            transfers = responseEntity.getBody();
        }
        catch (RestClientResponseException e) { //error message from server
            System.out.println("Error returned from server: " + e.getRawStatusCode());
        }
        catch (ResourceAccessException e) { //couldn't reach server
            System.out.println("Error: Couldn't reach server.");
        }
        return transfers;
    }

    public Transfer getTransfer(int transferId, String token) {
        Transfer transfer = null;
        try {
            ResponseEntity<Transfer> responseEntity = restTemplate.exchange(baseUrl + "getTransfers/" + transferId, HttpMethod.GET, getHttpEntity(token), Transfer.class);
            transfer = responseEntity.getBody();
        }
        catch (RestClientResponseException e) { //error message from server
            System.out.println("Error returned from server: " + e.getRawStatusCode());
        }
        catch (ResourceAccessException e) { //couldn't reach server
            System.out.println("Error: Couldn't reach server.");
        }
        return transfer;
    }

    public Transfer createTransfer(Transfer transfer, String token) {
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "createTransfer", HttpMethod.POST, getTransferEntity(transfer, token), Transfer.class);
            return response.getBody();
        }
        catch (RestClientResponseException e) { //error message from server
            System.out.println("Error returned from server: " + e.getRawStatusCode());
        }
        catch (ResourceAccessException e) { //couldn't reach server
            System.out.println("Error: Couldn't reach server.");
        }
        return null;
    }

    public Transfer makeTransferObject(int userId, BigDecimal amount){ //set transfer id, transfer type, transfer status
        Transfer transfer = new Transfer();
        transfer.setAccountTo(userId);
        transfer.setAmount(amount);
        return transfer;
    }

    private HttpEntity<?> getHttpEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity(headers);
        return entity;
    }

    private HttpEntity<Transfer> getTransferEntity(Transfer transfer, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Transfer> entity = new HttpEntity(transfer, headers);
        return entity;
    }

}
