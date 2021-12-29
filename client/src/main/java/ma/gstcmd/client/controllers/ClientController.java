package ma.gstcmd.client.controllers;

import ma.gstcmd.client.dtos.ClientDto;
import ma.gstcmd.client.requests.ClientRequest;
import ma.gstcmd.client.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clients/v1")
public class ClientController {
    private IClientService clientService;

    @Autowired
    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ClientDto> getClients(){
        return clientService.getClients();
    }


    @GetMapping("/{clientId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public  ClientDto getClient(@PathVariable String clientId){
        return clientService.getClient(clientId);
    }

    @GetMapping("/exist/{clientId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public  Boolean existClient(@PathVariable String clientId){
        return clientService.existClient(clientId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto addClient(@Valid @RequestBody ClientRequest request){
        return clientService.addClient(request);
    }

    @PutMapping("/{clientId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ClientDto updateClient(@PathVariable String clientId, @Valid @RequestBody ClientRequest request){
        return clientService.updateClient(clientId, request);
    }

    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteClient(@PathVariable String clientId){
        clientService.deleteClient(clientId);
    }
}
