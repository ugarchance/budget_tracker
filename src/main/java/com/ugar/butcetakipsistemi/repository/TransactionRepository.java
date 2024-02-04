package com.ugar.butcetakipsistemi.repository;

import com.ugar.butcetakipsistemi.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


//Jpa ile Crud işlemleri gerçekleştiriliyor
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public List<Transaction> findAllByOrderByIdAsc();
    @Query("SELECT SUM(t.miktar) FROM Transaction t WHERE t.islemTipi = 'gelir'")
    BigDecimal findTotalIncome();

    @Query("SELECT SUM(t.miktar) FROM Transaction t WHERE t.islemTipi = 'gider'")
    BigDecimal findTotalExpense();

}
