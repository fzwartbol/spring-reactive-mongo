package com.frederikzwartbol.springreactivemongo.controller;

import com.frederikzwartbol.springreactivemongo.dto.ProductDTO;
import com.frederikzwartbol.springreactivemongo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Flux<ProductDTO> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Mono<ProductDTO> getProduct(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @GetMapping("/product-range")
    public Flux<ProductDTO> getProductBetweenRange(@RequestParam("min") int min, @RequestParam("max") int max) {
        return productService.getProductInRange(min, max);
    }

    @PostMapping
    public Mono<ProductDTO> saveProduct(@RequestBody Mono<ProductDTO> productDTO) {
        return productService.saveProduct(productDTO);
    }

    @PutMapping("/update/{id}")
    public Mono<ProductDTO> saveId(@RequestBody Mono<ProductDTO> productDTO, @PathVariable String id ) {
        return productService.updateProduct(id,productDTO);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }

}
