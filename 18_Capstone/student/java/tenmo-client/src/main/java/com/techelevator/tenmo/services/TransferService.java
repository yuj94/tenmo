package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
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

    public void createTransfer(TransferDTO transferDto, String token) {
        try {
            ResponseEntity<TransferDTO> response = restTemplate.exchange(baseUrl + "createTransfer", HttpMethod.POST, getTransferEntity(transferDto, token), TransferDTO.class);
            response.getBody();
        }
        catch (RestClientResponseException e) { //error message from server
            System.out.println("Error returned from server: " + e.getRawStatusCode());
        }
        catch (ResourceAccessException e) { //couldn't reach server
            System.out.println("Error: Couldn't reach server.");
        }
    }

    public TransferDTO makeTransferObject(int userId, BigDecimal amount){ //set transfer id, transfer type, transfer status
        TransferDTO transferDto = new TransferDTO();
        transferDto.setUserIdTo(userId);
        transferDto.setAmount(amount);
        return transferDto;
    }

    private HttpEntity<?> getHttpEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity(headers);
        return entity;
    }

    private HttpEntity<TransferDTO> getTransferEntity(TransferDTO transferDto, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<TransferDTO> entity = new HttpEntity(transferDto, headers);
        return entity;
    }

}
