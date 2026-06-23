package com.ecommerce.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

}