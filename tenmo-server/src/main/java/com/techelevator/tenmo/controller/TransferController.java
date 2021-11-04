package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("iaAuthenticated()")
public class TransferController {
    private TransferDAO transferDAO;

    //don't know if I should op
    @RequestMapping(path = "account/{id}/transfers", method = RequestMethod.GET)
    public List<Transfers>listAllTransfers

}
