package nazar.zhanabergenov.shop_monolith.order.mapper;

import nazar.zhanabergenov.shop_monolith.order.dto.OrderDto;
import nazar.zhanabergenov.shop_monolith.order.dto.OrderItemDto;
import nazar.zhanabergenov.shop_monolith.order.entity.Order;
import nazar.zhanabergenov.shop_monolith.order.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "items", ignore = true)
    Order toEntity(OrderDto orderDto);

    OrderDto toDto(Order order);

    List<OrderDto> toDtoList(List<Order> orders);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "subtotal", expression = "java(item.getSubtotal())")
    OrderItemDto toItemDto(OrderItem item);

    List<OrderItemDto> toItemDtoList(List<OrderItem> items);
}

