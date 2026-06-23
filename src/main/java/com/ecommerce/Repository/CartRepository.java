package com.ecommerce.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.Entity.Cart;
import com.ecommerce.Entity.CartItem;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);
    
   

}