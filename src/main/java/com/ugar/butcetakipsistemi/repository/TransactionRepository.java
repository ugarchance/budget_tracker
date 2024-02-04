package com.ugar.butcetakipsistemi.repository;

import com.ugar.butcetakipsistemi.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    @Query("SELECT SUM(t.miktar) FROM Transaction t WHERE t.islemTipi = 'gelir' AND t.islemTarihi BETWEEN :start AND :end")
    Optional<BigDecimal> findTotalIncomeBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT SUM(t.miktar) FROM Transaction t WHERE t.islemTipi = 'gider' AND t.islemTarihi BETWEEN :start AND :end")
    Optional<BigDecimal> findTotalExpenseBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);


}
