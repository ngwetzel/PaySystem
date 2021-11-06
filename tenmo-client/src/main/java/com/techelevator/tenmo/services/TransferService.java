package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import io.cucumber.java.sl.In;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import java.util.Scanner;

public class TransferService {
    private String authToken;

    private final String  API_Base = "http://localhost:8080";
    private final RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser authenticatedUser;

    public TransferService(AuthenticatedUser authenticatedUser) {
        authToken = authenticatedUser.getToken();
    }


    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String[] listUsers() {
        String[] users = null;
        try {
            ResponseEntity<String[]> response = restTemplate.exchange(API_Base + "/users",
                    HttpMethod.GET, makeAuthEntity(), String[].class);
            users = response.getBody();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return users;
    }

    public BigDecimal viewBalance() {
        BigDecimal balance = null;
        try {
            ResponseEntity<BigDecimal> response = restTemplate.exchange(API_Base + "balance",
                    HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
            balance = response.getBody();
            return balance;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Transfers[] listTransfers() {
        Transfers[] transfers = null;
        try {
            ResponseEntity<Transfers[]> response = restTemplate.exchange(API_Base + "/transfers",
                    HttpMethod.GET, makeAuthEntity(), Transfers[].class);
            transfers = response.getBody();
            System.out.println
                    ("------------------------------------\n" +
                     " Transfers \n" +
                     " ID          From/To          Amount\n" +
                     "------------------------------------\n");


            for (Transfers eachTransfer : transfers) {
                if (eachTransfer.getTransferTypeId() == 2) {
                    System.out.println(eachTransfer.getTransferId() + " " +
                            "To: " +eachTransfer.getUserTo()+
                            eachTransfer.getAmount());

                } else {
                    System.out.println(eachTransfer.getTransferId() + " " +
                            "From: " +eachTransfer.getUserFrom() +
                            eachTransfer.getAmount());
                }
            }

            System.out.println("------------------------------------\n" +
                    "Please enter transferID to view details (0 to cancel)");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            for (Transfers eachTransfer : transfers) {
                if(Integer.parseInt(input) == 0){
                    System.exit(0);
                }

                else if (Integer.parseInt(input) == eachTransfer.getTransferId()) {
                    ResponseEntity<Transfers> responseEntity = restTemplate.exchange(API_Base + "/transfers/"
                                    + eachTransfer.getTransferId(),
                            HttpMethod.GET, makeAuthEntity(), Transfers.class);
                    Transfers specificTransfer = responseEntity.getBody();
                    System.out.println
                            ("-------------------------------------\n" +
                             "Transfers Details\n" +
                             "-------------------------------------\n" +
                             "ID: " + specificTransfer.getTransferId() +
                             "From: " + specificTransfer.getUserFrom() +
                             "To: " + specificTransfer.getUserTo() +
                             "Type: " + specificTransfer.getTransferTypeId() +
                             "Status: " + specificTransfer.getTransferStatusId() +
                             "Amount: $" + specificTransfer.getAmount()
                            );
                }
                else{
                    System.out.println("Transfer ID not found please re-enter");
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return transfers;
    }


    private HttpEntity<User> makeUserEntity (User user){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(user, headers);
    }

    private HttpEntity<Void> makeAuthEntity () {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Transfers> makeTransferEntity (Transfers transfer){

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBearerAuth(authToken);

        return new HttpEntity<>(transfer, headers);

    }
}







