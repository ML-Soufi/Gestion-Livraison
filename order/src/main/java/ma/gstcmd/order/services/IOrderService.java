package ma.gstcmd.order.services;

import ma.gstcmd.order.dtos.OrderDto;
import ma.gstcmd.order.requests.OrderRequest;

import java.util.List;

public interface IOrderService {
    List<OrderDto> getOrders();
    OrderDto getOrder(String orderId);
    Boolean existOrder(String orderId);
    OrderDto addOrder(OrderRequest request);
    OrderDto updateOrder(String orderId, OrderRequest request);
    void deleteOrder(String orderId);
}
