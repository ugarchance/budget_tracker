package com.ugar.butcetakipsistemi.controller;

import com.ugar.butcetakipsistemi.entity.Transaction;
import com.ugar.butcetakipsistemi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class TransactionController {
    private final TransactionService transactionService;
    @Value("${islemTuru}")
    private List<String> islemTuru;


    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/islemler")
    public String getAllTransaction(Model model) {
        List<Transaction> theTransaction = transactionService.findAll();
        model.addAttribute("transactions", theTransaction);
        return "islemler-listesi";
    }
    @GetMapping("/listeyi-guncelle")
    public String updateList(@RequestParam("transactionId") long theId, Model theModel){
        Optional<Transaction> theTransaction = transactionService.findById(theId);
        if (theTransaction.isPresent()) {
            theModel.addAttribute("transaction", theTransaction.get());
        }
        theModel.addAttribute("islemTuru", islemTuru);
        return "islem-ekleme-formu";
    }
    @GetMapping("/islem-ekleme-formu")
    public String showFormForAdd(Model theModel){
        Transaction theTransaction = new Transaction();

        theModel.addAttribute("transaction",theTransaction);
        theModel.addAttribute("islemTuru",islemTuru);

        return"islem-ekleme-formu";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute("transaction")Transaction theTransaction){
        transactionService.saveTransaction(theTransaction);
        return "redirect:/islemler";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("transactionId") int theId){

        transactionService.deleteById(theId);

        return "redirect:/islemler";
    }
}
