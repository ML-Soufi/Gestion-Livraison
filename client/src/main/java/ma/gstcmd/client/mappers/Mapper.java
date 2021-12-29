package ma.gstcmd.client.mappers;

import ma.gstcmd.client.dtos.ClientDto;
import ma.gstcmd.client.entities.ClientEntity;
import ma.gstcmd.client.requests.ClientRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {
    public ClientDto entityToDto(ClientEntity entity){
        ClientDto dto = new ClientDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public List<ClientDto> entityToDto(List<ClientEntity> entities){
        List<ClientDto> list = new ArrayList<>();
        entities.forEach(entity -> list.add(entityToDto(entity)));
        return list;
    }

    public ClientEntity requestToEntity(ClientRequest request){
        ClientEntity entity = new ClientEntity();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }
}
