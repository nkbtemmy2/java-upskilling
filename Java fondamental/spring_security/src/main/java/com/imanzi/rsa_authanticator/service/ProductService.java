package com.imanzi.rsa_authanticator.service;


import com.imanzi.rsa_authanticator.model.Product;
import com.imanzi.rsa_authanticator.repository.ProductRepository;
import com.imanzi.rsa_authanticator.repository.UserRepository;
import com.imanzi.rsa_authanticator.utils.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Autowired
    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        existingProduct.setName(product.getName() != null ? product.getName() : existingProduct.getName());
        existingProduct.setDescription(product.getDescription() !=null ? product.getDescription() : existingProduct.getDescription());
        existingProduct.setPrice(product.getPrice() != null ? product.getPrice() : existingProduct.getPrice());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product markAsFeatured(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new UserException("PRODUCT_NOT_FOUND", "Product not found"));
        return productRepository.save(product);
    }

    public List<Product> searchProducts(String searchTerm) {
        return productRepository.searchByNameOrDescription(searchTerm);
    }


}