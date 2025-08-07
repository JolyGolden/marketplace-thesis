package nazar.zhanabergenov.shop_monolith.order.service;

import lombok.AllArgsConstructor;
import nazar.zhanabergenov.shop_monolith.order.dto.OrderDto;
import nazar.zhanabergenov.shop_monolith.order.entity.Order;
import nazar.zhanabergenov.shop_monolith.order.entity.OrderItem;
import nazar.zhanabergenov.shop_monolith.order.exception.NotFoundException;
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

@Service
@AllArgsConstructor
public class OrderService {
    public final OrderRepository orderRepository;
    public final ProductRepository productRepo;
    public final OrderMapper orderMapper;

    @Transactional
    public Order placeOrder(OrderDto dto) {

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
