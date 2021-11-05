
package com.techelevator.tenmo.controller;

        import com.techelevator.tenmo.dao.TransferDao;
        import com.techelevator.tenmo.model.Transfers;
        import org.springframework.security.access.prepost.PreAuthorize;

        import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@PreAuthorize("iaAuthenticated()")
public class TransferController {
    private TransferDao transferDao;



    //don't know if I should do user or account id
    @RequestMapping(path = "account/{userID}/transfers", method = RequestMethod.GET)
    public List<Transfers>listAllTransfersForAccount(@PathVariable Long userID){
        return transferDao.allTransfers(userID);
    }

    @RequestMapping(path = "transfers/{transferID}", method = RequestMethod.GET)
    public Transfers allTransfersByTransferID(@PathVariable Long transferID){
        return transferDao.transferLookupWithTransferID(transferID);
    }

    @RequestMapping(path = "pay" , method = RequestMethod.POST)
    public Object makePayment(@RequestBody Transfers transfer){
        return transferDao.tenmoPay(transfer.getAccountFrom(),transfer.getAccountTo(),transfer.getAmount());
    }

    @RequestMapping(path = "users", method = RequestMethod.GET)
    public List<String> userList(){
        return transferDao.userList();
    }

}

