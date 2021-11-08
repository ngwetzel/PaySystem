package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import io.cucumber.java.sl.In;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransferService {
    private String authToken;

    private final String  API_Base = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser authenticatedUser;

    public TransferService(AuthenticatedUser authenticatedUser) {
        authToken = authenticatedUser.getToken();
        this.authenticatedUser = authenticatedUser;
    }


    public void setAuthToken(String authToken) {
        this.authToken = authToken;
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

    public Long getID(String user) {
//        String username = authenticatedUser.getUser().getUsername();

        Long accountID = null; // => so we can return the balance at the end
        try {

            ResponseEntity<Long> response = restTemplate.exchange(API_Base + "accounts?user=" + user,
                    HttpMethod.GET, makeAuthEntity(), Long.class);
            accountID = response.getBody();
            return accountID;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void send() {
        User[] users = null;
        Transfers tenmoTransfer = new Transfers();
        try{
            ResponseEntity<User[]> responseEntity = restTemplate.exchange(API_Base + "users",
                    HttpMethod.GET, makeAuthEntity(), User[].class);
            users = responseEntity.getBody();
            System.out.println
                    ("----------------------------------\n" +
                    "Users\n" +
                    "ID         Name\n" +
                    "-----------------------------------\n");
            for (User user : users) {
                System.out.println(
                        user.getId() + "     " + user.getUsername()
                );
            }
            System.out.println("------------------------------------\n" +
                    "Please enter User ID to view details (0 to cancel): ");
            Scanner scanner = new Scanner(System.in);
            Integer input = scanner.nextInt();
            boolean foundUser = false;
            for (User user : users){



        System.out.println("How much would you like to send?  ");
        BigDecimal amount = scanner.nextBigDecimal();
        Long accountFrom = getID(authenticatedUser.getUser().getUsername());
        Long accountTo = getID(userTo);

        Transfers forEntity = new Transfers();
        forEntity.setTransferTypeId(2L);
        forEntity.setTransferStatusId(2L);
       forEntity.setAccountFrom(accountFrom);
       forEntity.setAccountTo(accountTo);
        forEntity.setAmount(amount);
        makeTransferEntity(forEntity);



        }catch(NumberFormatException ex) {
            System.out.println("Error when entering a number please try again.");
        } catch (Exception e) {
            e.getStackTrace();
            e.printStackTrace();
        }

    }




    public Transfers[] listTransfers() {
        Transfers[] transfers = null;
        try {
            ResponseEntity<Transfers[]> response = restTemplate.exchange(API_Base + "transfers",
                    HttpMethod.GET, makeAuthEntity(), Transfers[].class);
            transfers = response.getBody();
            System.out.println
                    ("------------------------------------\n" +
                    " Transfers \n" +
                    " ID          From/To          Amount\n" +
                    "------------------------------------\n");
            //assert transfers != null;
            for (Transfers eachTransfer : transfers) {
                System.out.println(eachTransfer.getTransferId() + " " +
                        eachTransfer.getAccountTo() + "From: " +
                        eachTransfer.getAmount());
                System.out.println(eachTransfer.getTransferId() + " " +
                        eachTransfer.getAccountFrom() + "To: " +
                        eachTransfer.getAmount());

            }
            System.out.println("------------------------------------\n" +
                    "Please enter transferID to view details (0 to cancel): ");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            for (Transfers eachTransfer : transfers) {
                if (Integer.parseInt(input) == eachTransfer.getTransferId()) {
                    ResponseEntity<Transfers> responseEntity = restTemplate.exchange(API_Base + "transfers/"
                                    + eachTransfer.getTransferId(),
                            HttpMethod.GET, makeAuthEntity(), Transfers.class);
                    Transfers specificTransfer = responseEntity.getBody();
                    System.out.println
                            ("-------------------------------------\n" +
                             "Transfers Details\n" +
                             "-------------------------------------\n" +
                             "ID: " + specificTransfer.getTransferId() + "\n" +
                             "From: " + specificTransfer.getUserFrom() + "\n" +
                             "To: " + specificTransfer.getUserTo() + "\n" +
                             "Type: " + specificTransfer.getTransferType() + "\n" +
                             "Status: " + specificTransfer.getTransferStatus() + "\n" +
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

    private HttpEntity<User> makeUserEntity(User user) {
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







