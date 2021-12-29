package ma.gstcmd.client.services;

import ma.gstcmd.client.SendMail;
import ma.gstcmd.client.dtos.ClientDto;
import ma.gstcmd.client.entities.ClientEntity;
import ma.gstcmd.client.exceptions.ClientException;
import ma.gstcmd.client.mappers.Mapper;
import ma.gstcmd.client.repositories.ClientRepository;
import ma.gstcmd.client.requests.ClientRequest;
import ma.gstcmd.client.shareds.Toll;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Service
public class ClientServiceImp implements IClientService {
    private ClientRepository clientRepository;
    private Mapper mapper;
    private Toll toll;
    private EntityManager entityManager;
    private SendMail sendMail;

    @Autowired
    public ClientServiceImp(ClientRepository clientRepository, Mapper mapper, Toll toll, EntityManager entityManager, SendMail sendMail) {
        this.clientRepository = clientRepository;
        this.mapper = mapper;
        this.toll = toll;
        this.entityManager = entityManager;
        this.sendMail = sendMail;
    }

    @Override
    public List<ClientDto> getClients() {
        Iterable<ClientEntity> clientEntities = clientRepository.findAll();
        return mapper.entityToDto((List<ClientEntity>) clientEntities);
    }

    @Override
    public ClientDto getClient(String clientId) {
        if(clientRepository.existsByClientId(clientId)){
            ClientEntity entity = clientRepository.findByClientId(clientId);
            return mapper.entityToDto(entity);
        }else{
            throw new ClientException("ce client n'existe pas.");
        }
    }

    @Override
    public Boolean existClient(String clientId) {
        return clientRepository.existsByClientId(clientId);
    }

    @Override
    public ClientDto addClient(ClientRequest request) {
        ClientEntity entity = mapper.requestToEntity(request);
        entity.setClientId(toll.generateClientId(20));
        ClientEntity saved = clientRepository.save(entity);
        sendMail.sendMail(entity.getClientEmail());
        return mapper.entityToDto(saved);
    }

    @Override
    public ClientDto updateClient(String clientId, ClientRequest request) {
        if(clientRepository.existsByClientId(clientId)){
            ClientEntity entity = clientRepository.findByClientId(clientId);
            entity.setClientFirstName(request.getClientFirstName());
            entity.setClientLastName(request.getClientLastName());
            entity.setClientPhone(request.getClientPhone());
            entity.setClientEmail(request.getClientEmail());
            entity.setClientAddress(request.getClientAddress());
            entity.setUpdatedAt(new Date());
            ClientEntity saved = clientRepository.save(entity);
            return mapper.entityToDto(saved);
        }else{
            throw new ClientException("ce client n'existe pas.");
        }
    }

    @Override
    public void deleteClient(String clientId) {
        if(clientRepository.existsByClientId(clientId)){
            clientRepository.deleteById(clientRepository.findByClientId(clientId).getId());
        }else{
            throw new ClientException("ce client n'existe pas.");
        }
    }
}
