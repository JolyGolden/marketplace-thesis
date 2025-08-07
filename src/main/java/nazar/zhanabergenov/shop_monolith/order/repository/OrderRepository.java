package nazar.zhanabergenov.shop_monolith.order.repository;

import nazar.zhanabergenov.shop_monolith.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
