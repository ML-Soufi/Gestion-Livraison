package ma.gstcmd.order.controllers;

import ma.gstcmd.order.dtos.OrderDto;
import ma.gstcmd.order.requests.OrderRequest;
import ma.gstcmd.order.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders/v1")
public class OrderController {
    private IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<OrderDto> getOrders(){
        return orderService.getOrders();
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderDto getOrder(@PathVariable String orderId){
        return orderService.getOrder(orderId);
    }

    @GetMapping("/exist/{orderId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Boolean existOrder(@PathVariable String orderId){
        return orderService.existOrder(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto addOrder(@Valid @RequestBody OrderRequest request){
        return orderService.addOrder(request);
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderDto getOrder(@PathVariable String orderId,@Valid @RequestBody OrderRequest request){
        return orderService.updateOrder(orderId, request);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteOrder(@PathVariable String orderId){
        orderService.deleteOrder(orderId);
    }
}
