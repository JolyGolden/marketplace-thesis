package nazar.zhanabergenov.shop_monolith.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// order response
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private LocalDateTime createdAt;
    private BigDecimal total;
    private List<OrderItemDto> items = new ArrayList<>();
}

