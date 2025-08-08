package nazar.zhanabergenov.shop_monolith.order.service;

import lombok.AllArgsConstructor;
import nazar.zhanabergenov.shop_monolith.order.dto.CreateOrderRequest;
import nazar.zhanabergenov.shop_monolith.order.dto.OrderDto;
import nazar.zhanabergenov.shop_monolith.order.entity.Order;
import nazar.zhanabergenov.shop_monolith.order.entity.OrderItem;
import nazar.zhanabergenov.shop_monolith.order.exception.NotFoundException;
import nazar.zhanabergenov.shop_monolith.order.exception.OutOfStockException;
import nazar.zhanabergenov.shop_monolith.order.mapper.OrderMapper;
import nazar.zhanabergenov.shop_monolith.order.repository.OrderRepository;
import nazar.zhanabergenov.shop_monolith.product.entity.Product;
import nazar.zhanabergenov.shop_monolith.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    public final OrderRepository orderRepository;
    public final ProductRepository productRepo;
    public final OrderMapper orderMapper;

    @Transactional
    public OrderDto placeOrder(CreateOrderRequest req) {
        if (req.getItems() == null || req.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        Map<Long, Integer> qtyByProduct = req.getItems().stream()
                .collect(Collectors.toMap(
                        CreateOrderRequest.Item::getProductId,
                        CreateOrderRequest.Item::getQuantity,
                        Integer::sum));

        // ВАЖНО: блокируем в ОДНОМ порядке (например, по возрастанию id),
        // чтобы параллельные транзакции брали те же самые строки в одном порядке.
        List<Long> ids = qtyByProduct.keySet().stream().sorted().toList();

        // Это выполнит SELECT ... FOR UPDATE (на большинстве БД) и удержит lock до конца транзакции
        List<Product> lockedProducts = productRepo.findAllForUpdate(ids);
        Map<Long, Product> products = lockedProducts.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        // Проверки остатков уже под блокировкой — никто другой их не изменит до коммита
        for (Map.Entry<Long, Integer> e : qtyByProduct.entrySet()) {
            Long productId = e.getKey();
            int qty = e.getValue();

            Product p = products.get(productId);
            if (p == null) throw new NotFoundException("Product " + productId + " not found");
            if (qty <= 0) throw new IllegalArgumentException("Quantity must be > 0 for product " + productId);

            int stock = Optional.ofNullable(p.getStockQuantity()).orElse(0);
            if (stock < qty) throw new OutOfStockException("Not enough stock for product " + productId);
        }

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> e : qtyByProduct.entrySet()) {
            Product p = products.get(e.getKey());
            int qty = e.getValue();

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(p);
            item.setQuantity(qty);
            item.setUnitPrice(p.getPrice());

            order.getItems().add(item);

            total = total.add(p.getPrice().multiply(BigDecimal.valueOf(qty)));

            // Списание под тем же lock
            p.setStockQuantity(p.getStockQuantity() - qty);
        }

        order.setTotal(total);

        productRepo.saveAll(products.values());
        Order saved = orderRepository.save(order);

        return orderMapper.toDto(saved);
    }


    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order с ID " + id + " not found"));
        return orderMapper.toDto(order);
    }

    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderMapper.toDtoList(orders);
    }
}
