package nazar.zhanabergenov.shop_monolith.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import nazar.zhanabergenov.shop_monolith.product.entity.Product;

import java.math.BigDecimal;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product product;
    private Integer quantity;
    private BigDecimal unitPrice;

    public OrderItem(Long id, Integer quantity, BigDecimal price) {
        this.id = id;
        this.quantity = quantity;
        this.unitPrice = price;
    }

    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
