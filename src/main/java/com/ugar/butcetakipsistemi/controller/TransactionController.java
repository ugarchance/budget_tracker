package com.ugar.butcetakipsistemi.controller;

import com.ugar.butcetakipsistemi.entity.Transaction;
import com.ugar.butcetakipsistemi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    //İşlemler listesi
    @GetMapping("/islemler")
    public String getAllTransaction(Model model) {
        List<Transaction> transactions = transactionService.findAll();
        model.addAttribute("transactions", transactions);

        BigDecimal netIncome = transactionService.calculateNetIncome();
        model.addAttribute("netIncome", netIncome);

        String grafik = "grafik.png";
        model.addAttribute("grafik", grafik);

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

    //Main table content
    @GetMapping("/api/gelir-gider-verileri")
    public ResponseEntity<Map<String, BigDecimal>> getGelirGiderVerileri() {
        BigDecimal gelir = transactionService.findTotalIncome();
        BigDecimal gider = transactionService.findTotalExpense();

        Map<String, BigDecimal> veriler = new HashMap<>();
        veriler.put("gelir", gelir);
        veriler.put("gider", gider);

        return ResponseEntity.ok(veriler);
    }

    //Table content with date
    @GetMapping("/api/gelir-gider-verileri-tarih")
    public ResponseEntity<?> getGelirGiderVerileriTarih(
            @RequestParam("baslangicTarihi") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate baslangicTarihi,
            @RequestParam("bitisTarihi") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bitisTarihi) {

        BigDecimal gelir = transactionService.findTotalIncomeBetween(baslangicTarihi, bitisTarihi);
        BigDecimal gider = transactionService.findTotalExpenseBetween(baslangicTarihi, bitisTarihi);

        Map<String, Object> veriler = new HashMap<>();
        veriler.put("gelir", gelir);
        veriler.put("gider", gider);

        return ResponseEntity.ok(veriler);
    }

    //transaction added form
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
