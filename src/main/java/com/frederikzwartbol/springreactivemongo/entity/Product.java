package com.frederikzwartbol.springreactivemongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Product {
    private String id;
    private String name;
    private int quantity;
    private double price;
}
