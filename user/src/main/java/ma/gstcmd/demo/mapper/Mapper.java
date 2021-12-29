package ma.gstcmd.demo.mapper;

import ma.gstcmd.demo.dtos.UserDto;
import ma.gstcmd.demo.dtos.UserDto1;
import ma.gstcmd.demo.entities.UserEntity;
import ma.gstcmd.demo.requests.UserRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {
    public UserDto entityToDto(UserEntity entity){
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public UserDto1 entityToDto1(UserEntity entity){
        UserDto1 dto = new UserDto1();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public List<UserDto> entityToDto(List<UserEntity> entities){
        List<UserDto> dtos = new ArrayList<>();
        entities.forEach(entity -> dtos.add(entityToDto(entity)));
        return dtos;
    }

    public List<UserDto1> entityToDto1(List<UserEntity> entities){
        List<UserDto1> dtos = new ArrayList<>();
        entities.forEach(entity -> dtos.add(entityToDto1(entity)));
        return dtos;
    }

    public UserEntity requestToEntity(UserRequest request){
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }
}
