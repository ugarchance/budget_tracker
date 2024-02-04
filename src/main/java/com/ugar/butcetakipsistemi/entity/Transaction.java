package com.ugar.butcetakipsistemi.entity;

import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Transaction {

    //Hibernate ile postegreSql üzerinden tablo oluşturuyorum.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "islem_tipi")
    private String islemTipi; // "income" veya "expense"

    @Column(name = "miktar")
    private BigDecimal miktar;

    @Column(name = "acıklama")
    private String acıklama;

    @Column(name = "islem_tarihi")
    private String islemTarihi;

    public String islemTuru;


}
