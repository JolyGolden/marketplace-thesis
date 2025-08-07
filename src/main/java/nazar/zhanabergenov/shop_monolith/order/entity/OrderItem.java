package nazar.zhanabergenov.shop_monolith.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    private Integer quantity;
    private BigDecimal unitPrice;

    public OrderItem(Long id, Integer quantity, BigDecimal price) {
        this.productId = id;
        this.quantity = quantity;
        this.unitPrice = price;
    }
}
