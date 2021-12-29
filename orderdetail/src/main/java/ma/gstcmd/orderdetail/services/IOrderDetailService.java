package ma.gstcmd.orderdetail.services;

import ma.gstcmd.orderdetail.dtos.OrderDetailDto;
import ma.gstcmd.orderdetail.dtos.OrderDetailDto1;
import ma.gstcmd.orderdetail.requests.OrderDetailRequest;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IOrderDetailService {
    List<OrderDetailDto> getDetailOrders();
    List<OrderDetailDto1> getOrderDetailProducts(String orderId);
    OrderDetailDto addOrderDetail(OrderDetailRequest request);
    OrderDetailDto updateOrderDetail(String orderId, OrderDetailRequest request);
}
