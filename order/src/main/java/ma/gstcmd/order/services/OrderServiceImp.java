package ma.gstcmd.order.services;

import ma.gstcmd.order.dtos.OrderDto;
import ma.gstcmd.order.entities.OrderEntity;
import ma.gstcmd.order.exceptions.OrderException;
import ma.gstcmd.order.mappers.Mapper;
import ma.gstcmd.order.openfeign.ClientMsaClient;
import ma.gstcmd.order.openfeign.DeliverMsaClient;
import ma.gstcmd.order.repositories.OrderRepository;
import ma.gstcmd.order.requests.OrderRequest;
import ma.gstcmd.order.shareds.Toll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImp implements IOrderService {
    private OrderRepository orderRepository;
    private Toll toll;
    private Mapper mapper;
    private ClientMsaClient clientMsa;
    private DeliverMsaClient deliverMsa;
    @Autowired
    public OrderServiceImp(OrderRepository orderRepository, Toll toll, Mapper mapper, ClientMsaClient clientMsa, DeliverMsaClient deliverMsa) {
        this.orderRepository = orderRepository;
        this.toll = toll;
        this.mapper = mapper;
        this.clientMsa = clientMsa;
        this.deliverMsa = deliverMsa;
    }

    @Override
    public List<OrderDto> getOrders() {
        Iterable<OrderEntity> orderEntities = orderRepository.findAll();
        return mapper.entityToDto((List<OrderEntity>) orderEntities);
    }

    @Override
    public OrderDto getOrder(String orderId) {
        if(orderRepository.existsByOrderId(orderId)){
            OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
            return mapper.entityToDto(orderEntity);
        }else{
            throw new OrderException("cette commande n'éxiste pas.");
        }
    }

    @Override
    public Boolean existOrder(String orderId) {
        return orderRepository.existsByOrderId(orderId);
    }

    @Override
    public OrderDto addOrder(OrderRequest request) {
        if(!clientMsa.existClient(request.getClientId())) throw new OrderException("ce client n'existe pas");
        if(!deliverMsa.existDeliver(request.getDeliverId())) throw new OrderException("ce livreur n'existe pas");
        OrderEntity entity = mapper.requestToEntity(request);
        entity.setOrderId(toll.generateOrderId(20));
        OrderEntity saved = orderRepository.save(entity);
        return mapper.entityToDto(saved);
    }

    @Override
    public OrderDto updateOrder(String orderId, OrderRequest request) {
        if(orderRepository.existsByOrderId(orderId)){
            OrderEntity entity = orderRepository.findByOrderId(orderId);
            entity.setOrderAddress(request.getOrderAddress());
            entity.setOrderState(request.getOrderState());
            entity.setOrderPrice(request.getOrderPrice());
            //entity.setOrderDeliveryPrice(request.getOrderDeliveryPrice());
            entity.setUpdatedAt(new Date());
            OrderEntity saved = orderRepository.save(entity);
            return mapper.entityToDto(saved);
        }else{
            throw new OrderException("cette commande n'éxiste pas.");
        }
    }

    @Override
    public void deleteOrder(String orderId) {
        if(orderRepository.existsByOrderId(orderId)){
            orderRepository.deleteById(orderRepository.findByOrderId(orderId).getId());
        }else{
            throw new OrderException("cette commande n'éxiste pas.");
        }
    }
}
