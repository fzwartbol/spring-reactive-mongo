package com.frederikzwartbol.springreactivemongo.service;

import com.frederikzwartbol.springreactivemongo.dto.ProductDTO;
import com.frederikzwartbol.springreactivemongo.repository.ProductRepository;
import com.frederikzwartbol.springreactivemongo.utils.Modelmapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * @author Frederik
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * This method returns a Flux of ProductDTOs
     * @return
     */
    public Flux<ProductDTO> getProducts() {
        return productRepository.findAll().map(Modelmapper::entityToDto);
    }

    /**
     * This method
     * @param id
     * @return
     */

    public Mono<ProductDTO> getProductById(String id) {
        return productRepository.findById(id).map(Modelmapper::entityToDto);
    }

    public Flux<ProductDTO> getProductInRange(int min, int max) {
        return productRepository.findByPriceBetween(Range.closed(min,max)).map(Modelmapper::entityToDto);
    }

    /** Reactive method for saving a product,
     * if mono -> map to entity -> save -> map to dto
     * @param productDTO
     * @return
     */
    public Mono<ProductDTO> saveProduct(Mono<ProductDTO> productDTO) {
        return productDTO.map(Modelmapper::dtoToEntity)
                .flatMap(productRepository::save)
                .map(Modelmapper::entityToDto);
    }

    /** Reactive method for updating a product
     * @param productDTO
     * @return
     */
    public Mono<ProductDTO> updateProduct(String id, Mono<ProductDTO> productDTO) {
        return productRepository.findById(id)
                .zipWith(productDTO, (dbProduct, requestProduct) -> {
                    dbProduct.setName(requestProduct.getName());
                    dbProduct.setPrice(requestProduct.getPrice());
                    dbProduct.setQuantity(requestProduct.getQty());
                    return dbProduct;
                })
                .flatMap(productRepository::save)
                .map(Modelmapper::entityToDto);

        /** other option
        productRepository.findById(id)
                .flatMap(p -> productDTO.map(Modelmapper::dtoToEntity)
                        .doOnNext(update -> {
                            p.setName(update.getName());
                            p.setPrice(update.getPrice());
                            p.setQty(update.getQty());
                        }))
                .flatMap(productRepository::save)
                .map(Modelmapper::entityToDto);
         */
    }

    /** Reactive method for deleting a product
     * @param id
     * @return
     */
    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }

}
