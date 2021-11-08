package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Scanner;

public class TransferService {

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    private Scanner userInput = new Scanner(System.in);

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

    public Transfer createTransfer(String token) {
        Transfer newTransfer = new Transfer();
        try {
            ResponseEntity<Transfer> responseEntity = restTemplate.exchange(baseUrl + "createTransfer", HttpMethod.POST, getTransferEntity(newTransfer, token), Transfer.class);
        }
        catch (RestClientResponseException e) { //error message from server
            System.out.println("Error returned from server: " + e.getRawStatusCode() + ": " + e.getStatusText());
        }
        catch (ResourceAccessException e) { //couldn't reach server
            System.out.println("Error: Couldn't reach server.");
        }
        return newTransfer;
    }

    public Transfer makeTransferObject(int userId, BigDecimal amount, AuthenticatedUser currentUser){
        Transfer transfer = new Transfer();
        transfer.setAccountFrom(currentUser.getUser().getId());
        transfer.setAccountTo();
        return transfer;
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
