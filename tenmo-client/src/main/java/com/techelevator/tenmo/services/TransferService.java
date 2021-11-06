package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.AuthenticatedUser;
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
import java.util.Scanner;

public class TransferService {
    private String authToken;


private String API_Base = "http://localhost:8080";


private AuthenticatedUser authenticatedUser;
private RestTemplate restTemplate = new RestTemplate();


public  TransferService(AuthenticatedUser authenticatedUser) {
    this.authenticatedUser = authenticatedUser;
    authToken = authenticatedUser.getToken();
}


    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String[] listUsers() {
        String[] users = null;
        try {
            ResponseEntity<String[]> response = restTemplate.exchange(API_Base + "users",
                    HttpMethod.GET, makeAuthEntity(), String[].class);
            users = response.getBody();
            return users;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return null;
    }

    public BigDecimal viewBalance() {
        String username = authenticatedUser.getUser().getUsername();
        Accounts accounts = null;
        BigDecimal balance = null; // => so we can return the balance at the end
        try {
            ResponseEntity<BigDecimal> response = restTemplate.exchange(API_Base + "balance",
                    HttpMethod.GET, makeAuthEntity(), BigDecimal.class); //responseEntity should be Bigdecimal and so should the .class
            balance = response.getBody();
            return balance;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void send() {
    String username = authenticatedUser.getUser().getUsername();
    Transfers tenmoTransfer = null;
    Scanner scanner = new Scanner(System.in);
    String[] users = listUsers();
        System.out.println("Please choose a recipient: ");
        for(int i = 0; i < users.length; i++) {
            System.out.println(users[i]);
        }
      String userTo = scanner.nextLine();

        System.out.println("How much would you like to send?  ");
        BigDecimal amount = scanner.nextBigDecimal();
        Transfers forEntity = new Transfers();
        forEntity.setUserFrom(authenticatedUser.getUser().getUsername());
        forEntity.setUserTo(userTo);
        forEntity.setAmount(amount);


        try {
            ResponseEntity<Transfers> response = restTemplate.exchange(API_Base + "transfers",
                    HttpMethod.POST, makeTransferEntity(forEntity), Transfers.class);


        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    public Transfers[] listTransfers() {
    String username = authenticatedUser.getUser().getUsername();
        Transfers[] transfers = null;
        try {
            ResponseEntity<Transfers[]> response = restTemplate.exchange(API_Base + "transfers",
                    HttpMethod.GET, makeAuthEntity(), Transfers[].class);
            transfers = response.getBody();
            System.out.println("------------------------------------\n" +
                    " Transfers \n" +
                    " ID          From/To          Amount\n" +
                    "------------------------------------\n");
            assert transfers != null;
            for (Transfers eachTransfer : transfers) {
                System.out.println(eachTransfer.getTransferId() + " " +
                        eachTransfer.getAccountTo() + "From: " +
                        eachTransfer.getAmount());
                System.out.println(eachTransfer.getTransferId() + " " +
                        eachTransfer.getAccountFrom() + "To: " +
                        eachTransfer.getAmount());

            }
            System.out.println("------------------------------------\n" +
                    "Please enter transferID to view details (0 to cancel)");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            for (Transfers eachTransfer : transfers) {

                if (Integer.parseInt(input) == eachTransfer.getTransferId()) {

                }
            }
        } catch (Exception e) {
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

        private HttpEntity<Transfers> makeTransferEntity(Transfers transfer) {

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);

            headers.setBearerAuth(authToken);

            return new HttpEntity<> (transfer, headers);

        }
    }







