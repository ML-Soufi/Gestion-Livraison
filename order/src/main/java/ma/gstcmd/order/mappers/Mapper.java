package ma.gstcmd.order.mappers;

import ma.gstcmd.order.dtos.OrderDto;
import ma.gstcmd.order.entities.OrderEntity;
import ma.gstcmd.order.requests.OrderRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {
    public OrderDto entityToDto(OrderEntity entity){
        OrderDto dto = new OrderDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public List<OrderDto> entityToDto(List<OrderEntity> entities){
        List<OrderDto> dtoList = new ArrayList<>();
        entities.forEach(entity -> dtoList.add(entityToDto(entity)));
        return dtoList;
    }

    public OrderEntity requestToEntity(OrderRequest request){
        OrderEntity entity = new OrderEntity();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }
}
