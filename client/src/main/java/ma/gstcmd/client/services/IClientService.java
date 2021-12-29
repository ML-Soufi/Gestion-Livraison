package ma.gstcmd.client.services;

import ma.gstcmd.client.dtos.ClientDto;
import ma.gstcmd.client.requests.ClientRequest;

import java.util.List;

public interface IClientService {
    List<ClientDto> getClients();
    ClientDto getClient(String clientId);
    Boolean existClient(String clientId);
    ClientDto addClient(ClientRequest request);
    ClientDto updateClient(String clientId, ClientRequest request);
    void deleteClient(String clientId);
}
