package com.ugar.butcetakipsistemi.service;

import com.ugar.butcetakipsistemi.entity.Transaction;
import com.ugar.butcetakipsistemi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    //Repository instance'i oluşturuyorum
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findAllTransaction() {
        return transactionRepository.findAll();
    }
    public Transaction saveTransaction(Transaction theTransaction){
        return transactionRepository.save(theTransaction);
    }
    public Optional<Transaction> findById(long theId){
        return transactionRepository.findById(theId);
    }
    public List<Transaction> findAll(){
       return transactionRepository.findAllByOrderByIdAsc();
    }
    public void deleteById (long theId){
        transactionRepository.deleteById(theId);
    }

    public BigDecimal calculateNetIncome() {
        BigDecimal totalIncome = transactionRepository.findTotalIncome();
        BigDecimal totalExpense = transactionRepository.findTotalExpense();

        // Eğer bir değer null ise, bunu BigDecimal.ZERO olarak kabul et
        totalIncome = totalIncome == null ? BigDecimal.ZERO : totalIncome;
        totalExpense = totalExpense == null ? BigDecimal.ZERO : totalExpense;

        return totalIncome.subtract(totalExpense);
    }

    public BigDecimal findTotalIncome() {
        return transactionRepository.findTotalIncome();
    }

    public BigDecimal findTotalExpense() {
        return transactionRepository.findTotalExpense();
    }
    public BigDecimal findTotalIncomeBetween(LocalDate start, LocalDate end) {
        return transactionRepository.findTotalIncomeBetween(start, end).orElse(BigDecimal.ZERO);
    }

    public BigDecimal findTotalExpenseBetween(LocalDate start, LocalDate end) {
        return transactionRepository.findTotalExpenseBetween(start, end).orElse(BigDecimal.ZERO);
    }

}
