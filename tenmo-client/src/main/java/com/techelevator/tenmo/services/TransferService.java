package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import com.techelevator.view.ConsoleService;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransferService {
    private String authToken = null;


private String API_Base = "http://localhost:8080";



private RestTemplate restTemplate = new RestTemplate();





    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public User[] listUsers() {
        User[] users = null;
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(API_Base + "/users",
                    HttpMethod.GET, makeAuthEntity(), User[].class);
            users = response.getBody();

        } catch (RuntimeException e) {
        }

        return users;
    }

    public BigDecimal viewBalance(int userId) {
        Accounts accounts = null;
        BigDecimal balance = null; // => so we can return the balance at the end
        try {
            ResponseEntity<BigDecimal> response = restTemplate.exchange(API_Base + "/accounts/" + userId,
                    HttpMethod.GET, makeAuthEntity(), BigDecimal.class); //responseEntity should be Bigdecimal and so should the .class
            balance = response.getBody();
            return balance;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Transfers[] listTransfers(Long userID){
        Transfers[] transfers = null;
        try {
            ResponseEntity<Transfers[]> response =
                    restTemplate.exchange(API_Base + "user/" + userID + "/transfers",
                            HttpMethod.GET, makeAuthEntity(), Transfers[].class);
            transfers = response.getBody();
        } catch (Exception e){
            e.printStackTrace();
        }
        return transfers;
    }

    public Transfers getTransfer(Long transferID){
        Transfers transfer = null;
        try {
            ResponseEntity<Transfers> response =
                    restTemplate.exchange(API_Base + "transfers/" + transferID,
                            HttpMethod.GET, makeAuthEntity(), Transfers.class);
            transfer = response.getBody();
        } catch (Exception e){
            e.printStackTrace();
        }
        return transfer;
    }

    private HttpEntity<User> makeUserEntity(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(user, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }






}