package com.imanzi.crud_app.repository;

import com.imanzi.crud_app.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
