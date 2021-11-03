package com.techelevator.tenmo.controller;

<<<<<<< HEAD

=======
>>>>>>> 322f47a54208d3fe3ba9a91ca062cfb350edf6fe
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
<<<<<<< HEAD
@PreAuthorize("iaAuthenticated()")
public class TransferController {


=======
@PreAuthorize("isAuthenticated()")
public class TransferController {




>>>>>>> 322f47a54208d3fe3ba9a91ca062cfb350edf6fe
}
