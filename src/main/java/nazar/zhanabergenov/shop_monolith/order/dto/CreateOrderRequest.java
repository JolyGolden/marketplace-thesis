package nazar.zhanabergenov.shop_monolith.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private String customerName;
    private List<Item> items;

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class Item {
        private Long productId;
        private int quantity;
    }
}
