package ma.gstcmd.delivrer.controllers;

import ma.gstcmd.delivrer.dtos.DeliverDto;
import ma.gstcmd.delivrer.dtos.DeliverDto1;
import ma.gstcmd.delivrer.requests.DeliverRequest;
import ma.gstcmd.delivrer.services.IDeliverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/delivers/v1")
public class DeliverController {
    private IDeliverService deliverService;

    @Autowired
    public DeliverController(IDeliverService deliverService) {
        this.deliverService = deliverService;
    }

    @GetMapping("/page/{page}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DeliverDto1 getDelivers(@PathVariable int page){
        return deliverService.getDelivers(page);
    }

    @GetMapping("/contains/{firstName}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DeliverDto1 getDelivers(@PathVariable String firstName){
        return deliverService.getDelivers(firstName);
    }

    @GetMapping("/{deliverId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DeliverDto getDeliver(@PathVariable String deliverId){
        return deliverService.getDeliver(deliverId);
    }

    @GetMapping("/exist/{deliverId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Boolean existDeliver(@PathVariable String deliverId){
        return deliverService.existDeliver(deliverId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeliverDto addDeliver(@Valid @RequestBody DeliverRequest request){
        return deliverService.addDeliver(request);
    }

    @PutMapping("/{deliverId}")
    @ResponseStatus(HttpStatus.CREATED)
    public DeliverDto updateDeliver(@PathVariable String deliverId, @Valid @RequestBody DeliverRequest request){
        return deliverService.updateDeliver(deliverId, request);
    }

    @DeleteMapping("/{deliverId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteDeliver(@PathVariable String deliverId){
        deliverService.deleteDeliver(deliverId);
    }
}
