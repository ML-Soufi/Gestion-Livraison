package ma.gstcmd.orderdetail.services;

import ma.gstcmd.orderdetail.dtos.OrderDetailDto;
import ma.gstcmd.orderdetail.dtos.OrderDetailDto1;
import ma.gstcmd.orderdetail.entities.OrderDetailEntity;
import ma.gstcmd.orderdetail.exceptions.OrderDetailException;
import ma.gstcmd.orderdetail.mappers.Mapper;
import ma.gstcmd.orderdetail.openfeign.OrderMsaClient;
import ma.gstcmd.orderdetail.openfeign.ProductMsaClient;
import ma.gstcmd.orderdetail.reposities.OrderDetailRepository;
import ma.gstcmd.orderdetail.requests.OrderDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService{
    private OrderDetailRepository orderDetailRepository;
    private Mapper mapper;
    private ProductMsaClient productMsa;
    private OrderMsaClient orderMsa;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository, Mapper mapper, ProductMsaClient productMsa, OrderMsaClient orderMsa) {
        this.orderDetailRepository = orderDetailRepository;
        this.mapper = mapper;
        this.productMsa = productMsa;
        this.orderMsa = orderMsa;
    }

    @Override
    public List<OrderDetailDto> getDetailOrders() {
        return null;
    }

    @Override
    public List<OrderDetailDto1> getOrderDetailProducts(String orderId) {
        Iterable<OrderDetailEntity> entities = orderDetailRepository.findByOrderId(orderId);
        return mapper.entityToDto1((List<OrderDetailEntity>) entities);
    }


    @Override
    public OrderDetailDto addOrderDetail(OrderDetailRequest request) {
        if(!orderMsa.existOrder(request.getOrderId())) throw new OrderDetailException("cette commande n'existe pas.");
        if(!productMsa.existProduct(request.getProductId())) throw new OrderDetailException("ce product n'existe pas.");
        if(!productMsa.existProductQuantity(request.getProductId(), request.getOrderedQuantity())) throw new OrderDetailException("la quantité commander hor stock.");
        OrderDetailEntity entity = mapper.requestToEntity(request);
        OrderDetailEntity saved = orderDetailRepository.save(entity);
        return mapper.entityToDto(saved);
    }

    @Override
    public OrderDetailDto updateOrderDetail(String orderId, OrderDetailRequest request) {
        return null;
    }
}
