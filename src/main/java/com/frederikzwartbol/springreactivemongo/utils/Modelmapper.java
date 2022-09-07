package com.frederikzwartbol.springreactivemongo.utils;

import com.frederikzwartbol.springreactivemongo.dto.ProductDTO;
import com.frederikzwartbol.springreactivemongo.entity.Product;

public class Modelmapper {

    public static ProductDTO entityToDto(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getQuantity(), product.getPrice());
    }

    public static Product dtoToEntity(ProductDTO productDTO) {
        return new Product(productDTO.getId(), productDTO.getName(), productDTO.getQuantity(), productDTO.getPrice());
    }
}
