package ma.gstcmd.delivrer.services;

import com.google.inject.internal.cglib.core.$ClassNameReader;
import ma.gstcmd.delivrer.dtos.DeliverDto;
import ma.gstcmd.delivrer.dtos.DeliverDto1;
import ma.gstcmd.delivrer.entities.DeliverEntity;
import ma.gstcmd.delivrer.exceptions.DeliverException;
import ma.gstcmd.delivrer.mapper.Mapper;
import ma.gstcmd.delivrer.repositories.DeliverRepository;
import ma.gstcmd.delivrer.requests.DeliverRequest;
import ma.gstcmd.delivrer.shareds.Toll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DeliverService implements IDeliverService{
    private DeliverRepository deliverRepository;
    private Mapper mapper;
    private Toll toll;

    @Autowired
    public DeliverService(DeliverRepository deliverRepository, Mapper mapper, Toll toll) {
        this.deliverRepository = deliverRepository;
        this.mapper = mapper;
        this.toll = toll;
    }

    @Override
    public DeliverDto1 getDelivers(int page) {
        page = (page > 0)? page-1 : page;
        Page<DeliverEntity> entities = deliverRepository.findAll(PageRequest.of(page, 5));
        DeliverDto1 dto = new DeliverDto1();
        dto.setDtos(mapper.entityToDto(entities.getContent()));
        dto.setPageSizes(entities.getTotalPages());
        return dto;
    }

    @Override
    public DeliverDto1 getDelivers(String firstName) {
        Page<DeliverEntity> entities = deliverRepository.findByFirstNameContains(firstName.toLowerCase(), PageRequest.of(0, 5));
        DeliverDto1 dto = new DeliverDto1();
        dto.setDtos(mapper.entityToDto(entities.getContent()));
        dto.setPageSizes(entities.getTotalPages());
        return dto;
    }

    @Override
    public DeliverDto getDeliver(String deliverId) {
        if(deliverRepository.existsByDeliverId(deliverId)){
            DeliverEntity entity = deliverRepository.findByDeliverId(deliverId);
            return mapper.entityToDto(entity);
        }else{
            throw new DeliverException("ce livreur n'éxiste pas.");
        }
    }

    @Override
    public Boolean existDeliver(String deliverId) {
        return deliverRepository.existsByDeliverId(deliverId);
    }

    @Override
    public DeliverDto addDeliver(DeliverRequest request) {
        DeliverEntity entity = mapper.requestToEntity(request);
        entity.setDeliverId(toll.generateDeliverId(20));
        DeliverEntity saved = deliverRepository.save(entity);
        return mapper.entityToDto(saved);
    }

    @Override
    public DeliverDto updateDeliver(String deliverId, DeliverRequest request) {
        if(deliverRepository.existsByDeliverId(deliverId)){
            DeliverEntity entity = deliverRepository.findByDeliverId(deliverId);
            entity.setDeliverCni(request.getDeliverCni());
            entity.setFirstName(request.getFirstName());
            entity.setLastName(request.getLastName());
            entity.setDeliverSex(request.getDeliverSex());
            entity.setDeliverPhone(request.getDeliverPhone());
            entity.setDeliverEmail(request.getDeliverEmail());
            entity.setModifiedAt(new Date());
            DeliverEntity saved = deliverRepository.save(entity);
            return mapper.entityToDto(saved);
        }else{
            throw new DeliverException("ce livreur n'éxiste pas.");
        }
    }

    @Override
    public void deleteDeliver(String deliverId) {
        if(deliverRepository.existsByDeliverId(deliverId)){
            deliverRepository.deleteById(deliverRepository.findByDeliverId(deliverId).getId());
        }else{
            throw new DeliverException("ce livreur n'éxiste pas.");
        }
    }
}
