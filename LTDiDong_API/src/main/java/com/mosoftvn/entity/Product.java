package com.mosoftvn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(length = 500, columnDefinition = "nvarchar(500) not null")
    private String productName;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private double unitPrice;
    @Column(length = 200)
    private String images;
    @Column(columnDefinition = "nvarchar(500) not null")
    private String description;
    @Column(nullable = false)
    private double discount;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YYYY-MM-DD hh:mi:ss")
    private Date createDate;
    @Column(nullable = false)
    private short status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="categoryId")
    private Category category;
}
