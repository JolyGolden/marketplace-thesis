package nazar.zhanabergenov.shop_monolith.product.repository;

import nazar.zhanabergenov.shop_monolith.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
