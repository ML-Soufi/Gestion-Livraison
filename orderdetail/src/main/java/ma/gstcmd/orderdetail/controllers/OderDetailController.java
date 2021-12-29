package ma.gstcmd.orderdetail.controllers;

import ma.gstcmd.orderdetail.dtos.OrderDetailDto;
import ma.gstcmd.orderdetail.dtos.OrderDetailDto1;
import ma.gstcmd.orderdetail.requests.OrderDetailRequest;
import ma.gstcmd.orderdetail.services.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orderdetails/v1")
public class OderDetailController {
    private IOrderDetailService orderDetailService;

    @Autowired
    public OderDetailController(IOrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<OrderDetailDto1> getOrderDetailProducts(@PathVariable String orderId){
        return orderDetailService.getOrderDetailProducts(orderId);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetailDto addOrderDetail(@Valid @RequestBody OrderDetailRequest request){
        return orderDetailService.addOrderDetail(request);
    }
}
