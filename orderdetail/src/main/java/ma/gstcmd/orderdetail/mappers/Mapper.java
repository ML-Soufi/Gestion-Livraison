package ma.gstcmd.orderdetail.mappers;

import ma.gstcmd.orderdetail.dtos.OrderDetailDto;
import ma.gstcmd.orderdetail.dtos.OrderDetailDto1;
import ma.gstcmd.orderdetail.entities.OrderDetailEntity;
import ma.gstcmd.orderdetail.requests.OrderDetailRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {
    public OrderDetailDto1 entityToDto1(OrderDetailEntity entity){
        OrderDetailDto1 dto1 = new OrderDetailDto1();
        BeanUtils.copyProperties(entity, dto1);
        return dto1;
    }
    public List<OrderDetailDto1> entityToDto1(List<OrderDetailEntity> entities){
        List<OrderDetailDto1> dto1List = new ArrayList<>();
        entities.forEach(
                entity -> {
                    dto1List.add(entityToDto1(entity));
                }
        );
        return dto1List;
    }
    public OrderDetailDto entityToDto(OrderDetailEntity entity){
        OrderDetailDto dto = new OrderDetailDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public OrderDetailEntity requestToEntity(OrderDetailRequest request){
        OrderDetailEntity entity = new OrderDetailEntity();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }
}
