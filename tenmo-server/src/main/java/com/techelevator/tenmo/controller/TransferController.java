
package com.techelevator.tenmo.controller;


        import com.techelevator.tenmo.dao.AccountsDao;
        import com.techelevator.tenmo.dao.TransferDao;
<<<<<<< HEAD
        import com.techelevator.tenmo.dao.UserDao;
=======


>>>>>>> 363e45a6741629255c4d20f15297ef788b380520
        import com.techelevator.tenmo.model.Transfers;
        import com.techelevator.tenmo.model.User;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.access.prepost.PreAuthorize;

        import org.springframework.web.bind.annotation.*;

        import java.math.BigDecimal;
        import java.security.Principal;
        import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {
    @Autowired
    private TransferDao transferDao;
<<<<<<< HEAD
    @Autowired
    private UserDao userDao;
=======
@Autowired
private  AccountsDao accountsDao;


>>>>>>> 363e45a6741629255c4d20f15297ef788b380520

//public TransferController(TransferDao transferDao, AccountsDao accountsDao) {
//    this.accountsDao = accountsDao;
//    this.transferDao = transferDao;
//}

    //don't know if I should do user or account id
    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfers> listAllTransfersForAccount(Principal principal){
        return transferDao.allTransfers(principal.getName());
    }

    @RequestMapping(path = "/transfers/{transferID}", method = RequestMethod.GET)
    public Transfers allTransfersByTransferID(@PathVariable Long transferID){
        return transferDao.transferLookupWithTransferID(transferID);
    }

    @RequestMapping(path = "transfers" , method = RequestMethod.POST)
    public void makePayment(@RequestBody Transfers transfers) {
        transferDao.tenmoPay(transfers.getAccountFrom(), transfers.getAccountTo(), transfers.getAmount());
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> userList(){
        return userDao.findAll();
    }




}

