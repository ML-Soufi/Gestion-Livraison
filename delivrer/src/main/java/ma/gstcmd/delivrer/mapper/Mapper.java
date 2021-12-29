package ma.gstcmd.delivrer.mapper;

import ma.gstcmd.delivrer.dtos.DeliverDto;
import ma.gstcmd.delivrer.entities.DeliverEntity;
import ma.gstcmd.delivrer.requests.DeliverRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {
    public DeliverDto entityToDto(DeliverEntity entity){
        DeliverDto dto = new DeliverDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public List<DeliverDto> entityToDto(List<DeliverEntity> entities){
        List<DeliverDto> dtoList = new ArrayList<>();
        entities.forEach(deliverEntity -> dtoList.add(entityToDto(deliverEntity)));
        return dtoList;
    }

    public DeliverEntity requestToEntity(DeliverRequest request){
        DeliverEntity entity = new DeliverEntity();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }
}
